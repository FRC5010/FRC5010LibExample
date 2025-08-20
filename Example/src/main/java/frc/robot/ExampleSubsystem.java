package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.constants.GenericPID;
import org.frc5010.common.constants.MotorFeedFwdConstants;
import org.frc5010.common.drive.swerve.YAGSLSwerveDrivetrain;
import org.frc5010.common.motors.MotorConstants.Motor;
import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.motors.function.AngularControlMotor;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.motors.function.VelocityControlMotor;
import org.frc5010.common.motors.function.VerticalPositionControlMotor;
import org.frc5010.common.sensors.absolute_encoder.RevAbsoluteEncoder;
import org.ironmaple.simulation.IntakeSimulation;
import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.crescendo2024.NoteOnFly;
import yams.mechanisms.positional.Arm;
import yams.mechanisms.positional.Elevator;
import yams.mechanisms.positional.Pivot;

public class ExampleSubsystem extends GenericSubsystem {
  protected PercentControlMotor motor;
  protected VelocityControlMotor controlledMotor;
  protected AngularControlMotor angularMotor;
  protected VerticalPositionControlMotor verticalMotor;
  protected IntakeSimulation intakeSimulation;
  protected NoteOnFly noteOnFly;
  protected int scoredNotes = 0;
  protected Rotation2d rotation = new Rotation2d(Degrees.of(180));
  protected Elevator elevator;
  protected Arm arm;
  protected Pivot pivot;

  public ExampleSubsystem() {
    super("example.json");
    this.motor = (PercentControlMotor) devices.get("percent_motor");
    this.controlledMotor = (VelocityControlMotor) devices.get("velocity_motor");
    this.elevator = (Elevator) devices.get("Elevator");
    this.arm = (Arm) devices.get("Arm");
    this.pivot = (Pivot) devices.get("Turret");

    this.angularMotor = angularControlledMotor();
    // verticalMotor = verticalControlledMotor();
    // intakeSimulation =
    //     IntakeSimulation.InTheFrameIntake(
    //         "Coral",
    //         YAGSLSwerveDrivetrain.getSwerveDrive().getMapleSimDrive().get(),
    //         Inches.of(24.25),
    //         IntakeSide.FRONT,
    //         1);
  }

  public AngularControlMotor angularControlledMotor() {
    AngularControlMotor angularMotor =
        new AngularControlMotor(
                MotorFactory.Spark(13, Motor.Neo), "angular", getDisplayValuesHelper())
            .setupSimulatedMotor(
                (5.0 * 68.0 / 24.0) * (80.0 / 24.0),
                Units.lbsToKilograms(22),
                Inches.of(19),
                Degrees.of(0),
                Degrees.of(360),
                false,
                0,
                Degrees.of(0),
                false,
                0.1)
            .setVisualizer(mechanismSimulation, new Pose3d(0.75, 0, 0.25, new Rotation3d()));
    angularMotor.setEncoder(new RevAbsoluteEncoder((SparkMax) angularMotor.getMotor(), 360));
    angularMotor.setValues(new GenericPID(0.01, 0.000025, 0.003));
    angularMotor.setMotorFeedFwd(new MotorFeedFwdConstants(0.0, 0.01, 0.0, false));
    angularMotor.setIZone(3);
    angularMotor.setOutputRange(-12, 12);
    return angularMotor;
  }

  public Command getDefaultCommand(DoubleSupplier speed) {
    return Commands.run(() -> motor.set(speed.getAsDouble()), this);
  }

  public Command setPercentControlMotorReference(DoubleSupplier reference) {
    return Commands.runOnce(
        () -> {
          double speed = reference.getAsDouble();
          if (speed > 0.0 && !noteIsInsideIntake().getAsBoolean()) {
            intakeSimulation.startIntake();
          } else {
            intakeSimulation.stopIntake();
          }
          motor.set(speed);
        },
        this);
  }

  public Command setVelocityControlMotorReference(DoubleSupplier reference) {
    return Commands.runOnce(
        () -> {
          double speed = reference.getAsDouble();
          if (speed <= 0.0 && !noteIsInsideIntake().getAsBoolean()) {
            controlledMotor.setReference(speed);
          } else if (speed > 3000
              && noteIsInsideIntake().getAsBoolean()
              && obtainedGamePieceToScore().getAsBoolean()) {
            controlledMotor.setReference(speed);
            if (RobotBase.isSimulation()) {
              Pose2d worldPose = YAGSLSwerveDrivetrain.getSwerveDrive().getPose();

              // noteOnFly = new NoteOnFly(
              //         worldPose.getTranslation(),
              //         controlledMotor.getRobotToMotor().getTranslation().toTranslation2d(),
              //         YAGSLSwerveDrivetrain.getSwerveDrive().getFieldVelocity(),
              //         worldPose.getRotation().rotateBy(rotation),
              //         0.45,
              //         speed / 6000 * 20,
              //         Math.toRadians(55));
              // noteOnFly.enableBecomeNoteOnFieldAfterTouchGround();
              // noteOnFly.asSpeakerShotNote(() -> {
              //     if (noteOnFly.hasHitTarget()) {
              //         SmartDashboard.putNumber("Notes Speaker", ++scoredNotes);
              //     }
              // });
              SimulatedArena.getInstance().addGamePieceProjectile(noteOnFly);
            }
          } else if (speed < 3000
              && speed > 1000
              && noteIsInsideIntake().getAsBoolean()
              && obtainedGamePieceToScore().getAsBoolean()) {
            controlledMotor.setReference(speed);
            if (RobotBase.isSimulation()) {
              Pose2d worldPose = YAGSLSwerveDrivetrain.getSwerveDrive().getPose();
              // noteOnFly = new NoteOnFly(
              //         worldPose.getTranslation(),
              //         controlledMotor.getRobotToMotor().getTranslation().toTranslation2d(),
              //         YAGSLSwerveDrivetrain.getSwerveDrive().getFieldVelocity(),
              //         worldPose.getRotation().rotateBy(rotation),
              //         Meters.of(0.45),
              //         speed / 6000 * 20,
              //         Math.toRadians(55));
              // noteOnFly.enableBecomeNoteOnFieldAfterTouchGround();
              // noteOnFly.asAmpShotNote(() -> {
              //     if (noteOnFly.hasHitTarget()) {
              //         SmartDashboard.putNumber("Notes Amp", ++scoredNotes);
              //     }
              // });
              // SimulatedArena.getInstance().addGamePieceProjectile(noteOnFly);
            }
          } else {
            controlledMotor.setReference(speed);
          }
        },
        this);
  }

  public Command setAngularMotorReference(DoubleSupplier reference) {
    return Commands.runOnce(() -> angularMotor.setReference(reference.getAsDouble()), this);
  }

  public Trigger obtainedGamePieceToScore() {
    return new Trigger(
        () -> {
          return RobotBase.isSimulation()
              ? intakeSimulation.getGamePiecesAmount() == 1
                  && intakeSimulation.obtainGamePieceFromIntake()
              : false;
        });
  }

  public Trigger noteIsInsideIntake() {
    return new Trigger(
        () -> {
          return RobotBase.isSimulation() ? intakeSimulation.getGamePiecesAmount() > 0 : false;
        });
  }

  @Override
  public void periodic() {
    super.periodic();
    angularMotor.periodicUpdate();
    elevator.updateTelemetry();
    arm.updateTelemetry();
    pivot.updateTelemetry();
    //        verticalMotor.draw();
  }

  @Override
  public void simulationPeriodic() {
    super.simulationPeriodic();
    angularMotor.simulationUpdate();
    elevator.simIterate();
    arm.simIterate();
    pivot.simIterate();
    //      verticalMotor.simulationUpdate();
  }

  public Command setElevatorHeight(Supplier<Distance> height) {
    return elevator.setHeight(height.get());
  }

  public Command setArmAngle(Supplier<Angle> angle) {
    return arm.setAngle(angle.get());
  }

  public Command setPivotAngle(Supplier<Angle> angle) {
    return pivot.setAngle(angle.get());
  }

  public Command driveElevator(DoubleSupplier speed) {
    return elevator.set(speed.getAsDouble());
  }

  public Command getElevatorSysId() {
    return elevator.sysId(Volts.of(12), Volts.of(12).per(Second), Second.of(30));
  }
}
