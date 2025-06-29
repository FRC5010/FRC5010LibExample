package org.frc5010.common.config;

import org.frc5010.common.arch.GenericSubsystem;

public interface DeviceConfiguration {
  public Object configure(GenericSubsystem deviceHandler);
}
