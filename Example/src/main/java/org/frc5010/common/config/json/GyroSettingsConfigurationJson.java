package org.frc5010.common.config.json;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import org.frc5010.common.config.DeviceConfiguration;
import org.frc5010.common.sensors.gyro.NavXGyro;
import org.frc5010.common.sensors.gyro.PigeonGyro;

public class GyroSettingsConfigurationJson implements DeviceConfiguration {
  public String type;
  public int id;
  public double x;
  public double y;
  public double z;

  @Override
  public Object configure(Mechanism2d mechanismSimulation) {
    switch (type) {
      case "navx":
        return new NavXGyro(SPI.Port.kMXP);
      case "pigeon2":
        return new PigeonGyro(id);
      default:
        return null;
    }
  }
}
