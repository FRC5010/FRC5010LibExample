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
  protected final LengthUnit unit_;
  protected double millimeters_;

  // Constructor
  public static Length Millimeter(final double millimeters) {
    return new Length(LengthUnit.MILLIMETER, millimeters);
  }

  public static Length Centimeter(final double centimeters) {
    return new Length(LengthUnit.CENTIMETER, centimeters);
  }

  public static Length Meter(final double meters) {
    return new Length(LengthUnit.METER, meters);
  }

  public static Length Inch(final double inches) {
    return new Length(LengthUnit.INCH, inches);
  }

  public static Length Foot(final double feet) {
    return new Length(LengthUnit.FOOT, feet);
  }

  public static Length Yard(final double yards) {
    return new Length(LengthUnit.YARD, yards);
  }

  public Length(final LengthUnit unit, final double unitLength) {
    unit_ = unit;
    millimeters_ = unit_.toMillimeters(unitLength);
  }

  // Setters
  public void setMillimeters(final double millimeters) {
    setLength(LengthUnit.MILLIMETER, millimeters);
  }

  public void setCentimeters(final double centimeters) {
    setLength(LengthUnit.CENTIMETER, centimeters);
  }

  public void setMeters(final double meters) {
    setLength(LengthUnit.METER, meters);
  }

  public void setInches(final double inches) {
    setLength(LengthUnit.INCH, inches);
  }

  public void setFeet(final double feet) {
    setLength(LengthUnit.FOOT, feet);
  }

  public void setYards(final double yards) {
    setLength(LengthUnit.YARD, yards);
  }

  public void setLength(final Length length) {
    setLength(LengthUnit.MILLIMETER, length.getMillimeters());
  }

  public void setLength(final LengthUnit unit, final double unitLength) {
    millimeters_ = unit.toMillimeters(unitLength);
  }

  // Getters
  public double getMillimeters() {
    return getLength(LengthUnit.MILLIMETER);
  }

  public double getCentimeters() {
    return getLength(LengthUnit.CENTIMETER);
  }

  public double getMeters() {
    return getLength(LengthUnit.METER);
  }

  public double getInches() {
    return getLength(LengthUnit.INCH);
  }

  public double getFeet() {
    return getLength(LengthUnit.FOOT);
  }

  public double getYards() {
    return getLength(LengthUnit.YARD);
  }

  public double getLength(final LengthUnit unit) {
    return unit.fromMillimeters(millimeters_);
  }

  // Operations
  public Length add(final Length length) {
    return Length.Millimeter(millimeters_ + length.getMillimeters());
  }

  public Length subtract(final Length length) {
    return Length.Millimeter(millimeters_ - length.getMillimeters());
  }

  public Length multiply(final double scalar) {
    return Length.Millimeter(millimeters_ * scalar);
  }

  public Length divide(final double scalar) {
    return Length.Millimeter(millimeters_ / scalar);
  }
}
