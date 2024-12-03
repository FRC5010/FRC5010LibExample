package org.frc5010.common.telemetry;

import java.util.EnumSet;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.GenericRobot.LogLevel;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;

/** Add a double to the dashboard */
public class ConfigDouble extends DisplayDouble {
  // Variables
  /** The subscriber */
  protected DoubleSubscriber subscriber_;
  /** The listener handle */
  protected int listenerHandle_;

  // Constructor
  /**
   * Add a double to the dashboard
   *
   * @param defaultValue the default value
   * @param name         the name
   * @param table        the table
   */
  public ConfigDouble(final double defaultValue, final String name, final String table) {
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
              setValue(event.valueData.value.getDouble(), false);
            });
  }
}
