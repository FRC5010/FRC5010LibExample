package org.frc5010.common.config.json;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import org.frc5010.common.config.DeviceConfiguration;
import org.frc5010.common.motors.function.PercentControlMotor;

public class PercentMotorConfigurationJson implements DeviceConfiguration {
  public String name;
  public String type;
  public int id;
  public double gearing;
  public double momentOfInertiaKgMSq;
  public double x;
  public double y;
  public double z;

  @Override
  public Object configure(Mechanism2d mechanismSimulation) {
    return new PercentControlMotor(DeviceConfigReader.getMotor(type, id), name)
        .setupSimulatedMotor(gearing, momentOfInertiaKgMSq)
        .setVisualizer(mechanismSimulation, new Pose3d(x, y, z, new Rotation3d()));
  }
}
