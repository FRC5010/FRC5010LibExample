package frc.robot;

import java.util.function.DoubleSupplier;

import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.motors.function.GenericFunctionalMotor;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ExampleSubsystem extends GenericSubsystem {
    protected GenericFunctionalMotor motor;

    public ExampleSubsystem(GenericFunctionalMotor motor) {
        this.motor = motor;
    }

    public Command getDefaultCommand(DoubleSupplier speed) {
        return Commands.run(() -> motor.set(speed.getAsDouble()), this);
    }

    @Override
    public void periodic() {
        motor.draw();
    }

    @Override
    public void simulationPeriodic() {
        motor.simulationUpdate();
    }
}
