package org.frc5010.common.telemetry;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.EnumSet;

/** Add a boolean to the dashboard */
public class DisplayBoolean {
  // Variables
  /** The value being displayed */
  protected boolean value_;
  /** The name of the variable */
  protected final String name_;
  /** The table being stored in */
  protected final String table_;
  /** The topic */
  protected final BooleanTopic topic_;
  /** The publisher */
  protected final BooleanPublisher publisher_;
  /** The subscriber */
  protected final BooleanSubscriber subscriber_;
  /** The listener handle */
  protected int listenerHandle_;

  // Constructor
  /**
   * Create a new display boolean
   *
   * @param defaultValue the default value
   * @param name the name of the variable being stored
   * @param table the table being stored in
   */
  public DisplayBoolean(final boolean defaultValue, final String name, final String table) {
    value_ = defaultValue;
    name_ = name;
    table_ = table;
    topic_ = NetworkTableInstance.getDefault().getTable(table_).getBooleanTopic(name_);
    publisher_ = topic_.publish();
    subscriber_ = topic_.subscribe(value_);
    listenerHandle_ =
        NetworkTableInstance.getDefault()
            .addListener(
                subscriber_,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                  setValue(event.valueData.value.getBoolean(), false);
                });

    publisher_.setDefault(value_);
  }

  // Getters
  /**
   * Get the value
   *
   * @return the value
   */
  public synchronized boolean getValue() {
    return value_;
  }

  // Setters
  /**
   * Set the value
   *
   * @param value the value to set
   */
  public synchronized void setValue(final boolean value) {
    setValue(value, true);
  }

  /**
   * Set the value
   *
   * @param value the value to set
   * @param publish whether or not to publish the value
   */
  public synchronized void setValue(final boolean value, final boolean publish) {
    value_ = value;
    if (publish) {
      publisher_.set(value_);
    }
  }
}
