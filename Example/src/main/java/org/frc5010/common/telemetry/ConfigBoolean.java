package org.frc5010.common.telemetry;

import java.util.EnumSet;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.GenericRobot.LogLevel;

import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;

/** Add a boolean to the dashboard */
public class ConfigBoolean extends DisplayBoolean {
  // Variables
  /** The subscriber */
  protected BooleanSubscriber subscriber_;
  /** The listener handle */
  protected int listenerHandle_;

  // Constructor
  /**
   * Create a new display boolean
   *
   * @param defaultValue the default value
   * @param name         the name of the variable being stored
   * @param table        the table being stored in
   */
  public ConfigBoolean(final boolean defaultValue, final String name, final String table) {
    super(defaultValue, name, table, false);
    if (GenericRobot.logLevel.ordinal() <= LogLevel.CONFIG.ordinal()) {
      subscriber_ = topic_.subscribe(value_);
      configInit();
    }
  }

  public void configInit() {
    subscriber_ = topic_.subscribe(value_);
    listenerHandle_ = NetworkTableInstance.getDefault()
        .addListener(
            subscriber_,
            EnumSet.of(NetworkTableEvent.Kind.kValueAll),
            event -> {
              setValue(event.valueData.value.getBoolean(), false);
            });
  }
}
