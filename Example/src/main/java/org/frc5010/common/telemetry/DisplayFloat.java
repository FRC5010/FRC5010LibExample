package org.frc5010.common.telemetry;

import edu.wpi.first.networktables.FloatPublisher;
import edu.wpi.first.networktables.FloatSubscriber;
import edu.wpi.first.networktables.FloatTopic;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.EnumSet;

/** Add a float to the dashboard */
public class DisplayFloat {
  // Variables
  protected float value_;
  protected final String name_;
  protected final String table_;
  protected final FloatTopic topic_;
  protected final FloatPublisher publisher_;
  protected final FloatSubscriber subscriber_;
  protected int listenerHandle_;

  // Constructor
  /**
   * Add a float to the dashboard
   *
   * @param defaultValue
   * @param name
   * @param table
   */
  public DisplayFloat(final float defaultValue, final String name, final String table) {
    value_ = defaultValue;
    name_ = name;
    table_ = table;
    topic_ = NetworkTableInstance.getDefault().getTable(table_).getFloatTopic(name_);
    publisher_ = topic_.publish();
    subscriber_ = topic_.subscribe(value_);
    listenerHandle_ =
        NetworkTableInstance.getDefault()
            .addListener(
                subscriber_,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                  setValue(event.valueData.value.getFloat(), false);
                });

    publisher_.setDefault(value_);
  }

  // Getters
  /**
   * Get the value
   *
   * @return the value
   */
  public synchronized float getValue() {
    return value_;
  }

  // Setters
  /**
   * Set the value
   *
   * @param value the value to set
   */
  public synchronized void setValue(final float value) {
    setValue(value, true);
  }
  /**
   * Set the value
   *
   * @param value the value
   * @param publish - whether or not to publish
   */
  public synchronized void setValue(final float value, final boolean publish) {
    value_ = value;
    if (publish) {
      publisher_.set(value_);
    }
  }
}
