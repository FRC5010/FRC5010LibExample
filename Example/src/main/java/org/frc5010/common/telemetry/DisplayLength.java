package org.frc5010.common.telemetry;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.EnumSet;
import org.frc5010.common.units.Length;

/** Add a length to the dashboard */
public class DisplayLength extends Length {
  protected final String name_;
  protected final String table_;
  protected final DoubleTopic topic_;
  protected final DoublePublisher publisher_;
  protected final DoubleSubscriber subscriber_;
  protected int listenerHandle_;

  // Constructor
  /**
   * Add a length to the dashboard
   *
   * @param unit - length unit
   * @param unitLength - length in that unit
   * @param name - name of the variable
   * @param table - name of the table
   */
  public DisplayLength(
      final LengthUnit unit, final double unitLength, final String name, final String table) {
    super(unit, unitLength);
    name_ = String.format("%s (%s)", name, unit_.getShorthand());
    table_ = table;
    topic_ = NetworkTableInstance.getDefault().getTable(table_).getDoubleTopic(name_);
    publisher_ = topic_.publish();
    subscriber_ = topic_.subscribe(unit_.fromMillimeters(millimeters_));
    listenerHandle_ =
        NetworkTableInstance.getDefault()
            .addListener(
                subscriber_,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                event -> {
                  setLength(unit_, event.valueData.value.getDouble(), false);
                });

    publisher_.setDefault(unit_.fromMillimeters(millimeters_));
  }

  // Setters
  /**
   * Sets the length
   *
   * @param unit - length unit
   * @param unitLength - length in that unit
   */
  @Override
  public void setLength(final LengthUnit unit, final double unitLength) {
    setLength(unit, unitLength, true);
  }
  /**
   * Sets the length
   *
   * @param unit - length unit
   * @param unitLength - length in that unit
   * @param publish - publish the value
   */
  public void setLength(final LengthUnit unit, final double unitLength, final boolean publish) {
    super.setLength(unit, unitLength);
    if (publish) {
      publisher_.set(unit_.fromMillimeters(millimeters_));
    }
  }
}
