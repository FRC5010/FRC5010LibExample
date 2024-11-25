package org.frc5010.common.config.json;

import org.frc5010.common.config.DeviceConfiguration;
import org.frc5010.common.sensors.gyro.NavXGyro;
import org.frc5010.common.sensors.gyro.PigeonGyro;

import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;

public class GyroSettingsConfigurationJson implements DeviceConfiguration {
  public String type;
  public int id;

  @Override
  public Object configure(Mechanism2d mechanismSimulation) {
    switch (type) {
      case "navx":
        return new NavXGyro(NavXComType.kMXP_SPI);
      case "pigeon2":
        return new PigeonGyro(id);
      default:
        return null;
    }
  }
}
