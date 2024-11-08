package frc.robot;

import java.util.function.DoubleSupplier;

import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.motors.function.GenericControlledMotor;
import org.frc5010.common.motors.function.GenericFunctionalMotor;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class ExampleSubsystem extends GenericSubsystem {
    protected GenericFunctionalMotor motor;
    protected GenericControlledMotor controlledMotor;

    public ExampleSubsystem(GenericFunctionalMotor motor, GenericControlledMotor controlledMotor) {
        this.motor = motor;
        this.controlledMotor = controlledMotor;
    }

    public Command getDefaultCommand(DoubleSupplier speed) {
        return Commands.run(() -> motor.set(speed.getAsDouble()), this);
    }

    public Command setControlMotorReference(DoubleSupplier reference) {
        return Commands.runOnce(() -> controlledMotor.setReference(reference.getAsDouble()), this);
    }
    
    @Override
    public void periodic() {
        motor.draw();
        controlledMotor.draw();
    }

    @Override
    public void simulationPeriodic() {
        motor.simulationUpdate();
        controlledMotor.simulationUpdate();
    }
}