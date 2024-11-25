package org.frc5010.common.sensors.absolute_encoder;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import java.util.function.Supplier;
import org.frc5010.common.motors.hardware.GenericRevBrushlessMotor;
import swervelib.telemetry.Alert;

/**
 * SparkMax absolute encoder, attached through the data port. Credit: YAGSL for the original code
 */
public class RevAbsoluteEncoder extends GenericAbsoluteEncoder {
  /** The {@link AbsoluteEncoder} representing the duty cycle encoder attached to the SparkMax. */
  public AbsoluteEncoder encoder;
  /** An {@link Alert} for if there is a failure configuring the encoder. */
  private Alert failureConfiguring;
  /** An {@link Alert} for if there is a failure configuring the encoder offset. */
  private Alert offsetFailure;

  /**
   * Create the {@link RevAbsoluteEncoder} object as a duty cycle from the {@link CANSparkMax}
   * motor.
   *
   * @param motor Motor to create the encoder from.
   * @param conversionFactor The conversion factor to set if the output is not from 0 to 360.
   */
  public RevAbsoluteEncoder(GenericRevBrushlessMotor motor, int conversionFactor) {
    failureConfiguring =
        new Alert(
            "Encoders",
            "Failure configuring SparkMax Analog Encoder",
            Alert.AlertType.WARNING_TRACE);
    offsetFailure =
        new Alert(
            "Encoders", "Failure to set Absolute Encoder Offset", Alert.AlertType.WARNING_TRACE);
    if (motor.getMotor() instanceof CANSparkMax) {
      encoder = ((CANSparkMax) motor.getMotor()).getAbsoluteEncoder(Type.kDutyCycle);
      configureSparkMax(() -> encoder.setVelocityConversionFactor(conversionFactor));
      configureSparkMax(() -> encoder.setPositionConversionFactor(conversionFactor));
    } else {
      throw new RuntimeException("Motor given to instantiate SparkMaxEncoder is not a CANSparkMax");
    }
  }

  /**
   * Run the configuration until it succeeds or times out.
   *
   * @param config Lambda supplier returning the error state.
   */
  private void configureSparkMax(Supplier<REVLibError> config) {
    for (int i = 0; i < maximumRetries; i++) {
      if (config.get() == REVLibError.kOk) {
        return;
      }
    }
    failureConfiguring.set(true);
  }

  /** Reset the encoder to factory defaults. */
  @Override
  public void factoryDefault() {
    // Do nothing
  }

  /** Clear sticky faults on the encoder. */
  @Override
  public void clearStickyFaults() {
    // Do nothing
  }

  /**
   * Configure the absolute encoder to read from [0, 360) per second.
   *
   * @param inverted Whether the encoder is inverted.
   */
  @Override
  public void configure(boolean inverted) {
    encoder.setInverted(inverted);
  }

  /**
   * Get the absolute position of the encoder.
   *
   * @return Absolute position in degrees from [0, 360).
   */
  @Override
  public double getPosition() {
    return encoder.getPosition();
  }

  /**
   * Get the instantiated absolute encoder Object.
   *
   * @return Absolute encoder object.
   */
  @Override
  public Object getEncoder() {
    return encoder;
  }

  /**
   * Sets the Absolute Encoder Offset inside of the SparkMax's Memory.
   *
   * @param offset the offset the Absolute Encoder uses as the zero point.
   * @return if setting Absolute Encoder Offset was successful or not.
   */
  @Override
  public boolean setEncoderOffset(double offset) {
    REVLibError error = null;
    for (int i = 0; i < maximumRetries; i++) {
      error = encoder.setZeroOffset(offset);
      if (error == REVLibError.kOk) {
        return true;
      }
    }
    offsetFailure.setText("Failure to set Absolute Encoder Offset Error: " + error);
    offsetFailure.set(true);
    return false;
  }

  /**
   * Get the velocity in degrees/sec.
   *
   * @return velocity in degrees/sec.
   */
  @Override
  public double getVelocity() {
    return encoder.getVelocity();
  }

  @Override
  public void reset() {
    encoder.setZeroOffset(0);
  }

  @Override
  public void setPositionConversion(double conversion) {
    encoder.setPositionConversionFactor(conversion);
  }

  @Override
  public void setVelocityConversion(double conversion) {
    encoder.setVelocityConversionFactor(conversion);
  }

  @Override
  public void setPosition(double position) {
    encoder.setZeroOffset(position - encoder.getPosition() + encoder.getZeroOffset());
  }

  @Override
  public void setRate(double rate) {}

  @Override
  public void setInverted(boolean inverted) {
    encoder.setInverted(inverted);
  }
}
