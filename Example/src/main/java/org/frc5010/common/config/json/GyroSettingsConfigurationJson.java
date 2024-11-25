package org.frc5010.common.config.json;

import org.frc5010.common.config.DeviceConfiguration;
import org.frc5010.common.sensors.gyro.NavXGyro;
import org.frc5010.common.sensors.gyro.PigeonGyro;

import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;

/** Instatiates a Gyro sensor based on the specified type */
public class GyroSettingsConfigurationJson implements DeviceConfiguration {
  /** The type of gyro sensor */
  public String type;
  /** The ID of the gyro sensor, if needed */
  public int id;

/**
 * Configures and returns an appropriate gyro sensor based on the specified type.
 *
 * @param mechanismSimulation The Mechanism2d instance for visualization, if needed.
 * @return An instance of a gyro sensor (NavXGyro or PigeonGyro) based on the type, or null if the type is unrecognized.
 */
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
