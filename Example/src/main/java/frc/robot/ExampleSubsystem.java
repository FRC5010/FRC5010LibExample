package frc.robot;

import java.util.function.DoubleSupplier;

import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.constants.GenericPID;
import org.frc5010.common.constants.MotorFeedFwdConstants;
import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.motors.function.AngularControlMotor;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.motors.function.VelocityControlMotor;
import org.frc5010.common.sensors.absolute_encoder.RevAbsoluteEncoder;
import org.frc5010.common.units.Angle;
import org.frc5010.common.units.Length;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ExampleSubsystem extends GenericSubsystem {
    protected PercentControlMotor motor;
    protected VelocityControlMotor controlledMotor;
    protected AngularControlMotor angularMotor;

    public ExampleSubsystem() {
        super("example.json");
        this.motor = (PercentControlMotor)devices.get("percent_motor");
        this.controlledMotor = (VelocityControlMotor)devices.get("velocity_motor");
        this.angularMotor = angularControlledMotor();
    }

    public PercentControlMotor percentControlledMotor() {
        return new PercentControlMotor(MotorFactory.NEO(11), "percent")
                .setupSimulatedMotor(1, 1)
                .setVisualizer(mechanismSimulation, new Pose3d(0.5, 0, 0.25, new Rotation3d()));
    }

    public VelocityControlMotor velocityControlledMotor() {
        VelocityControlMotor velocityMotor = new VelocityControlMotor(MotorFactory.NEO(12), "velocity")
                .setupSimulatedMotor(1, 1)
                .setVisualizer(mechanismSimulation, new Pose3d(0.1, 0, 0.25, new Rotation3d()));
        velocityMotor.setValues(new GenericPID(0.4, 0, 0.1));
        velocityMotor.setMotorFeedFwd(new MotorFeedFwdConstants(0.1, 0.1, 0, false));
        return velocityMotor;
    }

    public AngularControlMotor angularControlledMotor() {
        AngularControlMotor angularMotor = new AngularControlMotor(MotorFactory.NEO(13), "angular")
                .setupSimulatedMotor((5.0 * 68.0 / 24.0) *  (80.0 / 24.0), Units.lbsToKilograms(22),
                        Length.Inch(19), Angle.Degree(0), Angle.Degree(360),
                        false, 0, Angle.Degree(0), false, 0.1)
                .setVisualizer(mechanismSimulation, new Pose3d(0.75, 0, 0.25, new Rotation3d()));
        angularMotor.setEncoder(new RevAbsoluteEncoder((SparkMax)angularMotor.getMotor(), 360));
        angularMotor.setValues(new GenericPID(0.01, 0.000025, 0.003));
        angularMotor.setMotorFeedFwd(new MotorFeedFwdConstants(0.0, 0.01, 0.0, false));
        angularMotor.setIZone(3);
        angularMotor.setOutputRange(-12, 12);
        return angularMotor;
    }

    public Command getDefaultCommand(DoubleSupplier speed) {
        return Commands.run(() -> motor.set(speed.getAsDouble()), this);
    }

    public Command setControlMotorReference(DoubleSupplier reference) {
        return Commands.runOnce(() -> controlledMotor.setReference(reference.getAsDouble()), this);
    }

    public Command setAngularMotorReference(DoubleSupplier reference) {
        return Commands.runOnce(() -> angularMotor.setReference(reference.getAsDouble()), this);
    }

    @Override
    public void periodic() {
        super.periodic();
        angularMotor.draw();
    }

    @Override
    public void simulationPeriodic() {
        super.simulationPeriodic();
        angularMotor.simulationUpdate();
    }
}