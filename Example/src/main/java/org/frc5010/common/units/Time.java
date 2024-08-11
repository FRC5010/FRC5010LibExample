// Package
package org.frc5010.common.units;

/** Time units */
public class Time {
  /** Time units */
  public enum TimeUnit {
    /** Milliseconds */
    MILLISECOND("msec", 1.0),
    /** Seconds */
    SECOND("sec", 1000.0),
    /** Minutes */
    MINUTE("min", 60000.0),
    /** Hours */
    HOUR("hr", 3600000.0);

    private final String shorthand_;
    private final double conversionRate_;

    private TimeUnit(final String shorthand, final double conversionRate) {
      shorthand_ = shorthand;
      conversionRate_ = conversionRate;
    }

    /**
     * Get the shorthand of the time unit
     *
     * @return The shorthand
     */
    public String getShorthand() {
      return shorthand_;
    }

    /**
     * Get the conversion rate of the time unit
     *
     * @return The conversion rate
     */
    public double getConversionRate() {
      return conversionRate_;
    }

    /**
     * Convert a time unit to milliseconds
     *
     * @param unitTime The time in the unit
     * @return The time in milliseconds
     */
    public double toMilliseconds(final double unitTime) {
      return unitTime * conversionRate_;
    }

    /**
     * Convert milliseconds to a time unit
     *
     * @param milliseconds The time in milliseconds
     * @return The time in the unit
     */
    public double fromMilliseconds(final double milliseconds) {
      return milliseconds / conversionRate_;
    }
  }

  // Variables
  protected final TimeUnit unit_;
  protected double milliseconds_;

  // Constructor
  /**
   * Constructor for milliseconds
   *
   * @param milliseconds The time in milliseconds
   * @return The time
   */
  public static Time Millisecond(final double milliseconds) {
    return new Time(TimeUnit.MILLISECOND, milliseconds);
  }

  /**
   * Constructor for seconds
   *
   * @param seconds The time in seconds
   * @return The time
   */
  public static Time Second(final double seconds) {
    return new Time(TimeUnit.SECOND, seconds);
  }

  /**
   * Constructor for minutes
   *
   * @param minutes The time in minutes
   * @return The time
   */
  public static Time Minute(final double minutes) {
    return new Time(TimeUnit.MINUTE, minutes);
  }

  /**
   * Constructor for hours
   *
   * @param hours The time in hours
   * @return The time
   */
  public static Time Hour(final double hours) {
    return new Time(TimeUnit.HOUR, hours);
  }

  /**
   * Generic Constructor
   *
   * @param unit The time unit
   * @param unitTime The time in the unit
   */
  public Time(final TimeUnit unit, final double unitTime) {
    unit_ = unit;
    milliseconds_ = unit_.toMilliseconds(unitTime);
  }

  // Setters
  /**
   * Set the time in milliseconds
   *
   * @param milliseconds The time in milliseconds
   */
  public void setMilliseconds(final double milliseconds) {
    setTime(TimeUnit.MILLISECOND, milliseconds);
  }

  /**
   * Set the time in seconds
   *
   * @param seconds The time in seconds
   */
  public void setSeconds(final double seconds) {
    setTime(TimeUnit.SECOND, seconds);
  }

  /**
   * Set the time in minutes
   *
   * @param minutes The time in minutes
   */
  public void setMinutes(final double minutes) {
    setTime(TimeUnit.MINUTE, minutes);
  }

  /**
   * Set the time in hours
   *
   * @param hours The time in hours
   */
  public void setHours(final double hours) {
    setTime(TimeUnit.HOUR, hours);
  }

  /**
   * Set the time in the unit
   *
   * @param time The time unit
   */
  public void setTime(final Time time) {
    setTime(TimeUnit.MILLISECOND, time.getMilliseconds());
  }

  /**
   * Set the time in the unit
   *
   * @param unit The time unit
   * @param unitTime The time in the unit
   */
  public void setTime(final TimeUnit unit, final double unitTime) {
    milliseconds_ = unit.toMilliseconds(unitTime);
  }

  // Getters
  /**
   * Get the time in milliseconds
   *
   * @return The time in milliseconds
   */
  public double getMilliseconds() {
    return getTime(TimeUnit.MILLISECOND);
  }

  /**
   * Get the time in seconds
   *
   * @return The time in seconds
   */
  public double getSeconds() {
    return getTime(TimeUnit.SECOND);
  }

  /**
   * Get the time in minutes
   *
   * @return The time in minutes
   */
  public double getMinutes() {
    return getTime(TimeUnit.MINUTE);
  }

  /**
   * Get the time in hours
   *
   * @return The time in hours
   */
  public double getHours() {
    return getTime(TimeUnit.HOUR);
  }

  /**
   * Get the time in the unit
   *
   * @param unit The time unit
   * @return The time in the unit
   */
  public double getTime(final TimeUnit unit) {
    return unit.fromMilliseconds(milliseconds_);
  }

  // Operations
  /**
   * Add two times
   *
   * @param time The time to add
   * @return The time
   */
  public Time add(final Time time) {
    return Time.Millisecond(milliseconds_ + time.getMilliseconds());
  }

  /**
   * Subtract two times
   *
   * @param time The time to subtract
   * @return The time
   */
  public Time subtract(final Time time) {
    return Time.Millisecond(milliseconds_ - time.getMilliseconds());
  }

  /**
   * Multiply a time by a scalar
   *
   * @param scalar The scalar
   * @return The time
   */
  public Time multiply(final double scalar) {
    return Time.Millisecond(milliseconds_ * scalar);
  }

  /**
   * Divide a time by a scalar
   *
   * @param scalar The scalar
   * @return The time
   */
  public Time divide(final double scalar) {
    return Time.Millisecond(milliseconds_ / scalar);
  }
}
