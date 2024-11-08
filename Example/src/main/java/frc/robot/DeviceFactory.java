package frc.robot;

import org.frc5010.common.constants.GenericPID;
import org.frc5010.common.constants.MotorFeedFwdConstants;
import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.motors.function.VelocityControlMotor;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;

public class DeviceFactory {
    public static Mechanism2d mechVisual;

    public DeviceFactory(Mechanism2d mechVisual) {
        DeviceFactory.mechVisual = mechVisual;
    }

    public PercentControlMotor percentControlledMotor() {
        return new PercentControlMotor(MotorFactory.NEO(11))
                .setupSimulatedMotor(1, 1)
                .setVisualizer(mechVisual, new Pose3d(0.5, 0, 0.5, new Rotation3d()), "percent");
    }

    public VelocityControlMotor velocityControlledMotor() {
        VelocityControlMotor velocityMotor = new VelocityControlMotor(MotorFactory.NEO(12))
                .setupSimulatedMotor(1, 1)
                .setVisualizer(mechVisual, new Pose3d(0.1, 0, 0.5, new Rotation3d()), "velocity");
        velocityMotor.setValues(new GenericPID(0.4, 0, 0.1));
        velocityMotor.setMotorFeedFwd(new MotorFeedFwdConstants(0.1, 0.1, 0, false));
        return velocityMotor;
    }
}
