package org.frc5010.common.config.json;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.frc5010.common.arch.GenericDeviceHandler;
import org.frc5010.common.config.ConfigConstants;
import org.frc5010.common.motors.MotorController5010;
import org.frc5010.common.motors.MotorFactory;

public class DeviceConfigReader {
  public static MotorController5010 getMotor(String type, int id) {
    MotorController5010 motor;
    switch (type) {
      case "neo":
        motor = MotorFactory.NEO(id);
        break;
      case "kraken":
        motor = MotorFactory.KrakenX60(id);
      default:
        return null;
    }
    return motor;
  }

  public static void readDeviceConfig(GenericDeviceHandler system, File deviceFile, String key)
      throws StreamReadException, DatabindException, IOException {
    switch (key) {
      case "gyro":
        GyroSettingsConfigurationJson gyroConfig =
            new ObjectMapper().readValue(deviceFile, GyroSettingsConfigurationJson.class);
        system.addDevice(ConfigConstants.GYRO, gyroConfig.configure(system.getMechVisual()));
        break;
      case "percent_motor":
        PercentMotorConfigurationJson percentMotorConfig =
            new ObjectMapper().readValue(deviceFile, PercentMotorConfigurationJson.class);
        system.addDevice(
            percentMotorConfig.name, percentMotorConfig.configure(system.getMechVisual()));
        break;
      default:
        break;
    }
  }
}
