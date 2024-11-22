package org.frc5010.common.config.json;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import org.frc5010.common.config.DeviceConfiguration;
import org.frc5010.common.constants.GenericPID;
import org.frc5010.common.constants.MotorFeedFwdConstants;
import org.frc5010.common.motors.function.VelocityControlMotor;

public class VelocityMotorConfigurationJson implements DeviceConfiguration {
  public String name;
  public String type;
  public int id;
  public double gearing;
  public double momentOfInertiaKgMSq;
  public double x;
  public double y = 0.0;
  public double z;
  public double kS = 0.0;
  public double kV = 0.0;
  public double kA = 0.0;
  public double kP = 0.0;
  public double kI = 0.0;
  public double kD = 0.0;
  public double iZone = 0.0;

  @Override
  public Object configure(Mechanism2d mechanismSimulation) {
    VelocityControlMotor motor =
        new VelocityControlMotor(DeviceConfigReader.getMotor(type, id), name)
            .setupSimulatedMotor(gearing, momentOfInertiaKgMSq)
            .setVisualizer(mechanismSimulation, new Pose3d(x, y, z, new Rotation3d()));
    if (kP != 0.0 || kI != 0.0 || kD != 0.0) {
      motor.setValues(new GenericPID(kP, kI, kD));
    }
    if (kS != 0.0 || kV != 0.0 || kA != 0.0) {
      motor.setMotorFeedFwd(new MotorFeedFwdConstants(kS, kV, kA));
    }
    if (iZone != 0.0) {
      motor.setIZone(iZone);
    }
    return motor;
  }
}
