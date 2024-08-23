package org.frc5010.common.units;

/** Length unit class */
public class Length {
  /** Length units */
  public enum LengthUnit {
    /** Millimeters */
    MILLIMETER("mm", 1.0),
    /** Centimeters */
    CENTIMETER("cm", 10.0),
    /** Meters */
    METER("m", 1000.0),
    /** Feet */
    INCH("in", 25.4),
    /** Feet */
    FOOT("ft", 304.8),
    /** Yards */
    YARD("yd", 914.4);

    private final String shorthand_;
    private final double conversionRate_;

    private LengthUnit(final String shorthand, final double conversionRate) {
      shorthand_ = shorthand;
      conversionRate_ = conversionRate;
    }

    /**
     * Get the shorthand of the length unit
     *
     * @return The shorthand
     */
    public String getShorthand() {
      return shorthand_;
    }

    /**
     * Get the conversion rate of the length unit
     *
     * @return The conversion rate
     */
    public double getConversionRate() {
      return conversionRate_;
    }

    /**
     * Convert the unit length to millimeters
     *
     * @param unitLength The unit length
     * @return The millimeters
     */
    public double toMillimeters(final double unitLength) {
      return unitLength * conversionRate_;
    }

    /**
     * Convert the millimeters to the unit length
     *
     * @param millimeters The millimeters
     * @return The unit length
     */
    public double fromMillimeters(final double millimeters) {
      return millimeters / conversionRate_;
    }
  }

  // Variables
  /** The length unit */
  protected final LengthUnit unit_;
  /** The length in millimeters */
  protected double millimeters_;

  // Constructor
  /**
   * Create a new length in millimeters
   *
   * @param millimeters The length in millimeters
   * @return The length
   */
  public static Length Millimeter(final double millimeters) {
    return new Length(LengthUnit.MILLIMETER, millimeters);
  }

  /**
   * Create a new length in centimeters
   *
   * @param centimeters The length in centimeters
   * @return The length
   */
  public static Length Centimeter(final double centimeters) {
    return new Length(LengthUnit.CENTIMETER, centimeters);
  }

  /**
   * Create a new length in meters
   *
   * @param meters The length in meters
   * @return The length
   */
  public static Length Meter(final double meters) {
    return new Length(LengthUnit.METER, meters);
  }

  /**
   * Create a new length in inches
   *
   * @param inches The length in inches
   * @return The length
   */
  public static Length Inch(final double inches) {
    return new Length(LengthUnit.INCH, inches);
  }

  /**
   * Create a new length in feet
   *
   * @param feet The length in feet
   * @return The length
   */
  public static Length Foot(final double feet) {
    return new Length(LengthUnit.FOOT, feet);
  }

  /**
   * Create a new length in yards
   *
   * @param yards The length in yards
   * @return The length
   */
  public static Length Yard(final double yards) {
    return new Length(LengthUnit.YARD, yards);
  }

  /**
   * Create a new length specified by the unit and length
   *
   * @param unit - length unit
   * @param unitLength - length in the specified unit
   */
  public Length(final LengthUnit unit, final double unitLength) {
    unit_ = unit;
    millimeters_ = unit_.toMillimeters(unitLength);
  }

  // Setters
  /**
   * Set the length in millimeters
   *
   * @param millimeters The length in millimeters
   */
  public void setMillimeters(final double millimeters) {
    setLength(LengthUnit.MILLIMETER, millimeters);
  }

  /**
   * Set the length in centimeters
   *
   * @param centimeters The length in centimeters
   */
  public void setCentimeters(final double centimeters) {
    setLength(LengthUnit.CENTIMETER, centimeters);
  }

  /**
   * Set the length in meters
   *
   * @param meters The length in meters
   */
  public void setMeters(final double meters) {
    setLength(LengthUnit.METER, meters);
  }

  /**
   * Set the length in inches
   *
   * @param inches The length in inches
   */
  public void setInches(final double inches) {
    setLength(LengthUnit.INCH, inches);
  }

  /**
   * Set the length in feet
   *
   * @param feet The length in feet
   */
  public void setFeet(final double feet) {
    setLength(LengthUnit.FOOT, feet);
  }

  /**
   * Set the length in yards
   *
   * @param yards The length in yards
   */
  public void setYards(final double yards) {
    setLength(LengthUnit.YARD, yards);
  }

  /**
   * Set the length using another length object
   *
   * @param length The length
   */
  public void setLength(final Length length) {
    setLength(LengthUnit.MILLIMETER, length.getMillimeters());
  }

  /**
   * Set the length using the unit and length
   *
   * @param unit - length unit
   * @param unitLength - length in the specified unit
   */
  public void setLength(final LengthUnit unit, final double unitLength) {
    millimeters_ = unit.toMillimeters(unitLength);
  }

  // Getters
  /**
   * Get the length in millimeters
   *
   * @return The length in millimeters
   */
  public double getMillimeters() {
    return getLength(LengthUnit.MILLIMETER);
  }

  /**
   * Get the length in centimeters
   *
   * @return The length in centimeters
   */
  public double getCentimeters() {
    return getLength(LengthUnit.CENTIMETER);
  }

  /**
   * Get the length in meters
   *
   * @return The length in meters
   */
  public double getMeters() {
    return getLength(LengthUnit.METER);
  }

  /**
   * Get the length in inches
   *
   * @return The length in inches
   */
  public double getInches() {
    return getLength(LengthUnit.INCH);
  }

  /**
   * Get the length in feet
   *
   * @return The length in feet
   */
  public double getFeet() {
    return getLength(LengthUnit.FOOT);
  }

  /**
   * Get the length in yards
   *
   * @return The length in yards
   */
  public double getYards() {
    return getLength(LengthUnit.YARD);
  }

  /**
   * Get the length in the specified unit
   *
   * @param unit - length unit
   * @return The length in the specified unit
   */
  public double getLength(final LengthUnit unit) {
    return unit.fromMillimeters(millimeters_);
  }

  // Operations
  /**
   * Add two lengths
   *
   * @param length - length to add
   * @return - sum of the lengths
   */
  public Length add(final Length length) {
    return Length.Millimeter(millimeters_ + length.getMillimeters());
  }

  /**
   * Subtract two lengths
   *
   * @param length - length to subtract
   * @return - difference of the lengths
   */
  public Length subtract(final Length length) {
    return Length.Millimeter(millimeters_ - length.getMillimeters());
  }

  /**
   * Multiply a length by a scalar
   *
   * @param scalar - scalar
   * @return - new length
   */
  public Length multiply(final double scalar) {
    return Length.Millimeter(millimeters_ * scalar);
  }

  /**
   * Divide a length by a scalar
   *
   * @param scalar - scalar
   * @return - new length
   */
  public Length divide(final double scalar) {
    return Length.Millimeter(millimeters_ / scalar);
  }
}
