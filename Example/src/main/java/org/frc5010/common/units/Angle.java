package org.frc5010.common.units;

/** Angle unit class */
public class Angle {
  /** Angle units */
  public enum AngleUnit {
    /** Degrees */
    DEGREE("deg", 1.0),
    /** Radians */
    RADIAN("rad", 180.0 / Math.PI),
    /** Turns */
    TURN("turn", 360.0);

    private final String shorthand_;
    private final double conversionRate_;

    private AngleUnit(final String shorthand, final double conversionRate) {
      shorthand_ = shorthand;
      conversionRate_ = conversionRate;
    }

    /**
     * Get the shorthand of the angle unit
     *
     * @return The shorthand
     */
    public String getShorthand() {
      return shorthand_;
    }

    /**
     * Get the conversion rate of the angle unit
     *
     * @return The conversion rate
     */
    public double getConversionRate() {
      return conversionRate_;
    }

    /**
     * Convert the unit angle to degrees
     *
     * @param unitAngle The unit angle
     * @return The degrees
     */
    public double toDegrees(final double unitAngle) {
      return unitAngle * conversionRate_;
    }

    /**
     * Convert the degrees to the unit angle
     *
     * @param degrees The degrees
     * @return The unit angle
     */
    public double fromDegrees(final double degrees) {
      return degrees / conversionRate_;
    }
  }

  // Variables
  /** The angle unit */
  protected final AngleUnit unit_;
  /** The angle */
  protected double degrees_;

  // Constructor
  /**
   * Constructor for degrees
   *
   * @param degrees The angle in degrees
   * @return The angle
   */
  public static Angle Degree(final double degrees) {
    return new Angle(AngleUnit.DEGREE, degrees);
  }

  /**
   * Constructor for radians
   *
   * @param radians The angle in radians
   * @return The angle
   */
  public static Angle Radian(final double radians) {
    return new Angle(AngleUnit.RADIAN, radians);
  }

  /**
   * Constructor for turns
   *
   * @param turns The angle in turns
   * @return The angle
   */
  public static Angle Turn(final double turns) {
    return new Angle(AngleUnit.TURN, turns);
  }

  /**
   * General Constructor
   *
   * @param unit The angle unit
   * @param unitAngle The angle in the unit
   */
  public Angle(final AngleUnit unit, final double unitAngle) {
    unit_ = unit;
    degrees_ = unit_.toDegrees(unitAngle);
  }

  // Setters
  /**
   * Set the angle in degrees
   *
   * @param degrees The angle in degrees
   */
  public void setDegrees(final double degrees) {
    setAngle(AngleUnit.DEGREE, degrees);
  }

  /**
   * Set the angle in radians
   *
   * @param radians The angle in radians
   */
  public void setRadians(final double radians) {
    setAngle(AngleUnit.RADIAN, radians);
  }

  /**
   * Set the angle in turns
   *
   * @param turns The angle in turns
   */
  public void setTurns(final double turns) {
    setAngle(AngleUnit.TURN, turns);
  }

  /**
   * Set the angle in the unit
   *
   * @param time The angle
   */
  public void setAngle(final Angle time) {
    setAngle(AngleUnit.DEGREE, time.getDegrees());
  }

  /**
   * Set the angle in the unit
   *
   * @param unit The angle unit
   * @param unitAngle The angle in the unit
   */
  public void setAngle(final AngleUnit unit, final double unitAngle) {
    degrees_ = unit.toDegrees(unitAngle);
  }

  // Getters
  /**
   * Get the angle in degrees
   *
   * @return The angle in degrees
   */
  public double getDegrees() {
    return getAngle(AngleUnit.DEGREE);
  }

  /**
   * Get the angle in radians
   *
   * @return The angle in radians
   */
  public double getRadians() {
    return getAngle(AngleUnit.RADIAN);
  }

  /**
   * Get the angle in radians
   *
   * @return The angle in radians
   */
  public double getSeconds() {
    return getAngle(AngleUnit.RADIAN);
  }

  /**
   * Get the angle in turns
   *
   * @return The angle in turns
   */
  public double getMinutes() {
    return getAngle(AngleUnit.TURN);
  }

  /**
   * Get the angle in the specified unit
   *
   * @param unit The angle unit
   * @return The angle in the unit
   */
  public double getAngle(final AngleUnit unit) {
    return unit.fromDegrees(degrees_);
  }

  // Operations
  /**
   * Add two angles
   *
   * @param angle The angle to add
   * @return The angle
   */
  public Angle add(final Angle angle) {
    return Angle.Degree(degrees_ + angle.getDegrees());
  }

  /**
   * Subtract two angles
   *
   * @param angle The angle to subtract
   * @return The angle
   */
  public Angle subtract(final Angle angle) {
    return Angle.Degree(degrees_ - angle.getDegrees());
  }

  /**
   * Multiply an angle by a scalar
   *
   * @param scalar The scalar
   * @return The angle
   */
  public Angle multiply(final Angle scalar) {
    return Angle.Degree(degrees_ * scalar.getDegrees());
  }

  /**
   * Divide an angle by a scalar
   *
   * @param scalar The scalar
   * @return The angle
   */
  public Angle multiply(final double scalar) {
    return Angle.Degree(degrees_ * scalar);
  }

  /**
   * Divide an angle by a scalar
   *
   * @param scalar The scalar
   * @return The angle
   */
  public Angle divide(final double scalar) {
    return Angle.Degree(degrees_ / scalar);
  }
}
