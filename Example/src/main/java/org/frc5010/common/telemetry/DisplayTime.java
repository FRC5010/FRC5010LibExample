package org.frc5010.common.telemetry;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.EnumSet;
import org.frc5010.common.units.Time;

/** Add a time to the dashboard */
public class DisplayTime extends Time {
  /** The name of the variable */
  protected final String name_;
  /** The table */
  protected final String table_;
  /** The topic */
  protected final DoubleTopic topic_;
  /** The publisher */
  protected final DoublePublisher publisher_;
  /** The subscriber */
  protected final DoubleSubscriber subscriber_;
  /** The listener handle */
  protected int listenerHandle_;

  // Constructor
  /**
   * Add a time to the dashboard
   *
   * @param unit - time unit
   * @param unitTime - time in that unit
   * @param name - name of the time
   * @param table - name of the table
   */
  public DisplayTime(
      final TimeUnit unit, final double unitTime, final String name, final String table) {
    super(unit, unitTime);
    name_ = String.format("%s (%s)", name, unit_.getShorthand());
    table_ = table;
    topic_ = NetworkTableInstance.getDefault().getTable(table_).getDoubleTopic(name_);
    publisher_ = topic_.publish();
    subscriber_ = topic_.subscribe(unit_.fromMilliseconds(milliseconds_));
    listenerHandle_ =
        NetworkTableInstance.getDefault()
            .addListener(
                subscriber_,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                  setTime(unit_, event.valueData.value.getDouble(), false);
                });

    publisher_.setDefault(unit_.fromMilliseconds(milliseconds_));
  }

  // Setters

  /**
   * Sets the time
   *
   * @param unit - time unit
   * @param unitTime - time in that unit
   */
  @Override
  public void setTime(final TimeUnit unit, final double unitTime) {
    setTime(unit, unitTime, true);
  }

  /**
   * Sets the time
   *
   * @param unit - time unit
   * @param unitTime - time in that unit
   * @param publish - whether or not to publish
   */
  public void setTime(final TimeUnit unit, final double unitTime, final boolean publish) {
    super.setTime(unit, unitTime);
    if (publish) {
      publisher_.set(unit_.fromMilliseconds(milliseconds_));
    }
  }
}
