// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.motors.function;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import org.frc5010.common.constants.RobotConstantsDef;
import org.frc5010.common.motors.MotorController5010;
import org.frc5010.common.motors.PIDController5010;
import org.frc5010.common.sensors.encoder.GenericEncoder;
import org.frc5010.common.sensors.encoder.GenericEncoder.EncoderMeasurementType;
import org.frc5010.common.units.Length;

/** A class that wraps a motor controller with functionality */
public class GenericFunctionalMotor implements MotorController5010 {
  /** The motor */
  protected MotorController5010 _motor;

  protected Mechanism2d _visualizer;
  protected Pose3d _robotToMotor;
  protected String _visualName;

  /**
   * Constructor for a motor
   *
   * @param motor The motor
   */
  public GenericFunctionalMotor(MotorController5010 motor) {
    this._motor = motor;
  }

  /**
   * Constructor for a motor
   *
   * @param motor The motor
   * @param slewRate The slew rate
   */
  public GenericFunctionalMotor(MotorController5010 motor, double slewRate) {
    this._motor = motor;
    _motor.setSlewRate(slewRate);
  }

  /**
   * Duplicates the motor controller and returns a new instance with the specified port.
   *
   * @param port the port number for the new motor controller
   * @return a new instance of MotorController5010 with the specified port
   */
  @Override
  public MotorController5010 duplicate(int port) {
    return _motor.duplicate(port);
  }

  /**
   * Sets the current limit for the motor controller.
   *
   * @param limit the current limit to be set
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setCurrentLimit(int limit) {
    _motor.setCurrentLimit(limit);
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
    _motor.setSlewRate(rate);
    return this;
  }

  /**
   * Sets the speed of the motor.
   *
   * @param speed the speed to set the motor to, between -1.0 and 1.0
   */
  @Override
  public void set(double speed) {
    _motor.set(speed);
  }

  /**
   * Retrieves the current value of the motor.
   *
   * @return the current value of the motor, between -1.0 and 1.0
   */
  @Override
  public double get() {
    return _motor.get();
  }

  /**
   * Sets the inversion of the motor.
   *
   * @param isInverted a boolean indicating whether the motor should be inverted or not
   */
  @Override
  public void setInverted(boolean isInverted) {
    _motor.setInverted(isInverted);
  }

  /**
   * Returns the inversion status of the motor.
   *
   * @return true if the motor is inverted, false otherwise
   */
  @Override
  public boolean getInverted() {
    return _motor.getInverted();
  }

  /**
   * Disables the motor by calling the disable method on the underlying motor object.
   *
   * @see MotorController5010#disable()
   */
  @Override
  public void disable() {
    _motor.disable();
  }

  /**
   * Stops the motor by calling the stopMotor method on the underlying motor object.
   *
   * @see MotorController5010#stopMotor()
   */
  @Override
  public void stopMotor() {
    _motor.stopMotor();
  }

  /**
   * Sets the motor as a follower of another motor.
   *
   * @param motor the motor to follow
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setFollow(MotorController5010 motor) {
    _motor.setFollow(motor);
    return this;
  }

  /**
   * Sets the motor as a follower of another motor, with the option to invert the follower.
   *
   * @param motor the motor to follow
   * @param inverted whether the follower should be inverted or not
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 setFollow(MotorController5010 motor, boolean inverted) {
    _motor.setFollow(motor, inverted);
    return this;
  }

  /**
   * Inverts the motor.
   *
   * @param inverted whether the motor should be inverted or not
   * @return a reference to the current MotorController5010 instance
   */
  @Override
  public MotorController5010 invert(boolean inverted) {
    _motor.invert(inverted);
    return this;
  }

  /**
   * Retrieves the generic encoder for the motor.
   *
   * @return the generic encoder for the motor
   */
  @Override
  public GenericEncoder getMotorEncoder() {
    return _motor.getMotorEncoder();
  }

  /**
   * Retrieves the generic encoder for the motor.
   *
   * @param sensorType the type of sensor to use for the encoder
   * @param countsPerRev the number of counts per revolution of the motor
   * @return the generic encoder for the motor
   */
  @Override
  public GenericEncoder getMotorEncoder(EncoderMeasurementType sensorType, int countsPerRev) {
    return _motor.getMotorEncoder();
  }

  /**
   * Retrieves the PIDController5010 for this motor.
   *
   * @return the PIDController5010 for this motor
   * @throws UnsupportedOperationException if the motor does not support PIDController5010
   */
  @Override
  public PIDController5010 getPIDController5010() {
    throw new UnsupportedOperationException("Not supported for this motor");
  }

  /**
   * Returns the default SysIdRoutine using the given subsystem.
   *
   * @param subsystemBase the subsystem for which to create the default SysIdRoutine
   * @return the default SysIdRoutine for the given subsystem
   */
  @Override
  public SysIdRoutine getDefaultSysId(SubsystemBase subsystemBase) {
    return _motor.getDefaultSysId(subsystemBase);
  }

  /**
   * Retrieves the motor controller associated with this instance.
   *
   * @return the motor controller associated with this instance
   */
  @Override
  public MotorController getMotor() {
    return _motor.getMotor();
  }

  /** Calls the factoryDefault method of the _motor object. This method does not return anything. */
  @Override
  public void factoryDefault() {
    // Not sure if it needs to return anything
    _motor.factoryDefault();
  }

  public GenericFunctionalMotor setVisualizer(
      Mechanism2d visualizer, Pose3d robotToMotor, String visualName) {
    _visualizer = visualizer;
    _robotToMotor = robotToMotor;
    _visualName = visualName;
    return this;
  }

  public Mechanism2d getVisualizer() {
    return _visualizer;
  }

  /** Needs to be overridden by subclasses to draw the motor behavior on the visualizer */
  public void draw() {}

  public Pose3d getRobotToMotor() {
    return _robotToMotor;
  }

  /** Needs to be overridden by subclasses to update the motor behavior during simulation */
  public void simulationUpdate() {}

  @Override
  public DCMotor getMotorSimulationType() {
    throw new UnsupportedOperationException("Unimplemented method 'getMotorSimulationType'");
  }

  public double getSimX(Length x) {
    return x.getMeters() * RobotConstantsDef.robotVisualH / 2.0
        + RobotConstantsDef.robotVisualH / 2.0;
  }

  public double getSimY(Length y) {
    return y.getMeters();
  }

  @Override
  public double getMaxRPM() {
    throw new UnsupportedOperationException("Unimplemented method 'getMaxRPM'");
  }
}
