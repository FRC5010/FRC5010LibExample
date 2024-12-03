package org.frc5010.common.telemetry;

import java.util.EnumSet;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.GenericRobot.LogLevel;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.measure.Angle;

/** Add an angle to the dashboard */
public class ConfigAngle extends DisplayAngle {
  /** Subscriber for the angle */
  protected DoubleSubscriber subscriber_;
  /** Listener handle */
  protected int listenerHandle_;

  /**
   * Constructor for an angle display
   *
   * @param angle - angle in that unit
   * @param unit  - angle unit
   * @param name  - name of the angle
   * @param table - name of the table
   */
  public ConfigAngle(
      final double angle, AngleUnit unit, final String name, final String table) {
    super(angle, unit, name, table);
    if (GenericRobot.logLevel.ordinal() <= LogLevel.CONFIG.ordinal()) {
      subscriber_ = topic_.subscribe(angle_.in(unit_));
      configInit();
    }
  }

  /**
   * Constructor for an angle display
   *
   * @param angle - angle with units
   * @param name  - name of the angle
   * @param table - name of the table
   */
  public ConfigAngle(
      final Angle angle, final String name, final String table) {
    super(angle, name, table);
    if (GenericRobot.logLevel.ordinal() <= LogLevel.CONFIG.ordinal()) {
      subscriber_ = topic_.subscribe(angle_.in(unit_));
      configInit();
    }
  }

  /**
   * Initializes the display by adding a listener to the subscriber and
   * setting the default value of the publisher
   */
  protected void configInit() {
    if (GenericRobot.logLevel.ordinal() <= LogLevel.CONFIG.ordinal()) {
      listenerHandle_ = NetworkTableInstance.getDefault()
          .addListener(
              subscriber_,
              EnumSet.of(NetworkTableEvent.Kind.kValueAll),
              event -> {
                setAngle(event.valueData.value.getDouble(), unit_, false);
              });
    }
  }
}
