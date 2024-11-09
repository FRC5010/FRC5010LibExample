package frc.robot;

import java.util.function.DoubleSupplier;

import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.constants.GenericPID;
import org.frc5010.common.constants.MotorFeedFwdConstants;
import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.motors.function.AngularControlMotor;
import org.frc5010.common.motors.function.GenericControlledMotor;
import org.frc5010.common.motors.function.GenericFunctionalMotor;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.motors.function.VelocityControlMotor;
import org.frc5010.common.units.Angle;
import org.frc5010.common.units.Length;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ExampleSubsystem extends GenericSubsystem {
    protected GenericFunctionalMotor motor;
    protected GenericControlledMotor controlledMotor;
    protected GenericControlledMotor angularMotor;

    public ExampleSubsystem() {
        this.motor = percentControlledMotor();
        this.controlledMotor = velocityControlledMotor();
        this.angularMotor = angularControlledMotor();
    }

    public PercentControlMotor percentControlledMotor() {
        return new PercentControlMotor(MotorFactory.NEO(11))
                .setupSimulatedMotor(1, 1)
                .setVisualizer(mechanismSimulation, new Pose3d(0.5, 0, 0.25, new Rotation3d()), "percent");
    }

    public VelocityControlMotor velocityControlledMotor() {
        VelocityControlMotor velocityMotor = new VelocityControlMotor(MotorFactory.NEO(12))
                .setupSimulatedMotor(1, 1)
                .setVisualizer(mechanismSimulation, new Pose3d(0.1, 0, 0.25, new Rotation3d()), "velocity");
        velocityMotor.setValues(new GenericPID(0.4, 0, 0.1));
        velocityMotor.setMotorFeedFwd(new MotorFeedFwdConstants(0.1, 0.1, 0, false));
        return velocityMotor;
    }

    public AngularControlMotor angularControlledMotor() {
        AngularControlMotor angularMotor = new AngularControlMotor(MotorFactory.NEO(13))
                .setupSimulatedMotor(20, 0.1,
                        Length.Meter(0.2), Angle.Degree(0), Angle.Degree(360),
                        false, Angle.Degree(0))
                .setVisualizer(mechanismSimulation, new Pose3d(0.75, 0, 0.25, new Rotation3d()), "angular");
        angularMotor.setValues(new GenericPID(1, 0, 0.1));
        angularMotor.setMotorFeedFwd(new MotorFeedFwdConstants(0.1, 10, 0, false));
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
        motor.draw();
        controlledMotor.draw();
        angularMotor.draw();
    }

    @Override
    public void simulationPeriodic() {
        motor.simulationUpdate();
        controlledMotor.simulationUpdate();
        angularMotor.simulationUpdate();
    }
}