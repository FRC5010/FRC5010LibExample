// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.motors.hardware;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import org.frc5010.common.motors.MotorController5010;
import org.frc5010.common.motors.PIDController5010;
import org.frc5010.common.motors.SystemIdentification;
import org.frc5010.common.motors.control.RevPID;
import org.frc5010.common.sensors.encoder.GenericEncoder;
import org.frc5010.common.sensors.encoder.GenericEncoder.EncoderMeasurementType;
import org.frc5010.common.sensors.encoder.RevEncoder;

/** A class for a generic REV brushless motor */
public class GenericRevBrushlessMotor extends CANSparkMax implements MotorController5010 {
  /** The current limit */
  protected int currentLimit;

  /**
   * Constructor for a generic REV brushless motor
   *
   * @param port the port number
   * @param currentLimit the current limit
   */
  public GenericRevBrushlessMotor(int port, int currentLimit) {
    super(port, MotorType.kBrushless);
    factoryDefault();
    setCurrentLimit(currentLimit);
  }

  @Override
  /**
   * Sets up the same motor hardware and current limit
   *
   * @param port The port number of the motor
   */
  public MotorController5010 duplicate(int port) {
    MotorController5010 duplicate = new GenericRevBrushlessMotor(port, currentLimit);
    return duplicate;
  }

  /**
   * Sets the current limit for the motor controller.
   *
   * @param limit the current limit to be set
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setCurrentLimit(int limit) {
    this.currentLimit = limit;
    super.setSmartCurrentLimit(limit);
    return this;
  }

  /**
   * Sets the slew rate for the motor controller.
   *
   * @param rate the slew rate to be set
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setSlewRate(double rate) {
    super.setOpenLoopRampRate(rate);
    return this;
  }

  /**
   * Sets the motor as a follower of another motor.
   *
   * @param motor the motor to follow
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setFollow(MotorController5010 motor) {
    super.follow((CANSparkMax) motor.getMotor());
    return this;
  }

  /**
   * Sets the motor as a follower of another motor, with the option to invert the output.
   *
   * @param motor the motor to follow
   * @param inverted whether the output should be inverted
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setFollow(MotorController5010 motor, boolean inverted) {
    super.follow((CANSparkMax) motor.getMotor(), inverted);
    return this;
  }

  /**
   * Inverts the motor.
   *
   * @param inverted a boolean indicating whether the motor should be inverted
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 invert(boolean inverted) {
    super.setInverted(inverted);
    return this;
  }

  /**
   * Retrieves the generic encoder for the motor.
   *
   * @return a new instance of GenericEncoder, which wraps the encoder returned by the superclass's
   *     getEncoder() method
   */
  @Override
  public GenericEncoder getMotorEncoder() {
    return new RevEncoder(super.getEncoder());
  }

  /**
   * Retrieves the generic encoder for the motor with the specified sensor type and counts per
   * revolution.
   *
   * @param sensorType the type of sensor to use for the encoder
   * @param countsPerRev the number of counts per revolution of the motor
   * @return a new instance of GenericEncoder, which wraps the encoder returned by the superclass's
   *     getEncoder() method
   */
  @Override
  public GenericEncoder getMotorEncoder(EncoderMeasurementType sensorType, int countsPerRev) {
    return new RevEncoder(super.getEncoder());
  }

  /**
   * Returns a new instance of PIDController5010, which is a wrapper around the RevPID class.
   *
   * @return a new instance of PIDController5010
   */
  @Override
  public PIDController5010 getPIDController5010() {
    return new RevPID(this);
  }

  /**
   * Returns the MotorController instance that this object represents.
   *
   * @return the MotorController instance that this object represents
   */
  @Override
  public MotorController getMotor() {
    return this;
  }

  /**
   * Returns a SysIdRoutine instance that represents the default system identification routine for
   * this object.
   *
   * @param subsystemBase the subsystem that this routine is associated with
   * @return a SysIdRoutine instance that represents the default system identification routine
   */
  @Override
  public SysIdRoutine getDefaultSysId(SubsystemBase subsystemBase) {
    return SystemIdentification.rpmSysIdRoutine(
        this, getMotorEncoder(), "Motor " + super.getDeviceId(), subsystemBase);
  }

  /**
   * Overrides the factoryDefault method to restore the default settings of the superclass. This
   * method is called to restore the default settings of the motor controller.
   */
  @Override
  public void factoryDefault() {
    super.restoreFactoryDefaults();
  }

  @Override
  public DCMotor getMotorSimulationType() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getMotorSimulationType'");
  }
}
