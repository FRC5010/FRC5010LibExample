package org.frc5010.common.config.json;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.frc5010.common.arch.GenericDeviceHandler;

public class SubsystemJson {
  public Map<String, String> devices;

  public void configureSubsystem(GenericDeviceHandler system, File directory)
      throws StreamReadException, DatabindException, IOException {
    for (String key : devices.keySet()) {
      File deviceFile = new File(directory, devices.get(key));
      assert deviceFile.exists();
      DeviceConfigReader.readDeviceConfig(system, deviceFile, key);
    }
  }
}
