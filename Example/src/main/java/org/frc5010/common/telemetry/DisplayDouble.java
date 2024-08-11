package org.frc5010.common.telemetry;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.EnumSet;

/** Add a double to the dashboard */
public class DisplayDouble {
  // Variables
  protected double value_;
  protected final String name_;
  protected final String table_;
  protected final DoubleTopic topic_;
  protected final DoublePublisher publisher_;
  protected final DoubleSubscriber subscriber_;
  protected int listenerHandle_;

  // Constructor
  /**
   * Add a double to the dashboard
   *
   * @param defaultValue the default value
   * @param name the name
   * @param table the table
   */
  public DisplayDouble(final double defaultValue, final String name, final String table) {
    value_ = defaultValue;
    name_ = name;
    table_ = table;
    topic_ = NetworkTableInstance.getDefault().getTable(table_).getDoubleTopic(name_);
    publisher_ = topic_.publish();
    subscriber_ = topic_.subscribe(value_);
    listenerHandle_ =
        NetworkTableInstance.getDefault()
            .addListener(
                subscriber_,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                  setValue(event.valueData.value.getDouble(), false);
                });

    publisher_.setDefault(value_);
  }

  // Getters
  /**
   * Get the value
   *
   * @return the value
   */
  public synchronized double getValue() {
    return value_;
  }

  // Setters
  /**
   * Set the value
   *
   * @param value the value
   */
  public synchronized void setValue(final double value) {
    setValue(value, true);
  }

  /**
   * Set the value
   *
   * @param value the value to set
   * @param publish whether or not to publish the value
   */
  public synchronized void setValue(final double value, final boolean publish) {
    value_ = value;
    if (publish) {
      publisher_.set(value_);
    }
  }
}
