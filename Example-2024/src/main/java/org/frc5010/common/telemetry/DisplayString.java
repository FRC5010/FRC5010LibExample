package org.frc5010.common.telemetry;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import java.util.EnumSet;

/** Add a string to the dashboard */
public class DisplayString {
  // Variables
  /** The value */
  protected String value_;
  /** The name */
  protected final String name_;
  /** The name of the table */
  protected final String table_;
  /** The topic */
  protected final StringTopic topic_;
  /** The publisher */
  protected final StringPublisher publisher_;
  /** The subscriber */
  protected final StringSubscriber subscriber_;
  /** The listener handle */
  protected int listenerHandle_;

  // Constructor
  /**
   * Add a string to the dashboard
   *
   * @param defaultValue the default value
   * @param name the name of the variable
   * @param table the name of the table
   */
  public DisplayString(final String defaultValue, final String name, final String table) {
    value_ = defaultValue;
    name_ = name;
    table_ = table;
    topic_ = NetworkTableInstance.getDefault().getTable(table_).getStringTopic(name_);
    publisher_ = topic_.publish();
    subscriber_ = topic_.subscribe(value_);
    listenerHandle_ =
        NetworkTableInstance.getDefault()
            .addListener(
                subscriber_,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                  setValue(event.valueData.value.getString(), false);
                });

    publisher_.setDefault(value_);
  }

  // Getters
  /**
   * Get the value
   *
   * @return the value
   */
  public synchronized String getValue() {
    return value_;
  }

  // Setters
  /**
   * Set the value
   *
   * @param value the value to set
   */
  public synchronized void setValue(final String value) {
    setValue(value, true);
  }

  /**
   * Set the value
   *
   * @param value the value to set
   * @param publish whether or not to publish the value
   */
  public synchronized void setValue(final String value, final boolean publish) {
    value_ = value;
    if (publish) {
      publisher_.set(value_);
    }
  }
}
