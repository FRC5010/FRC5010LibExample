// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.motors;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import org.frc5010.common.sensors.encoder.GenericEncoder;
import org.frc5010.common.sensors.encoder.GenericEncoder.EncoderMeasurementType;

/** Common interface fdr motors in the library */
public interface MotorController5010 extends MotorController {
  /**
   * Sets up the same motor hardware and current limit
   *
   * @param port The port number of the motor
   * @return a reference to the motor
   */
  MotorController5010 duplicate(int port);

  /**
   * Sets the motor's slew rate
   *
   * @param rate The slew rate
   * @return a reference to the motor
   */
  MotorController5010 setSlewRate(double rate);

  /**
   * Sets the motor as a follower of another motor
   *
   * @param motor The motor to follow
   * @return a reference to the motor
   */
  MotorController5010 setFollow(MotorController5010 motor);

  /**
   * Sets the motor as an inverted follower of another motor
   *
   * @param motor The motor to follow
   * @param inverted Whether the motor should be inverted
   * @return a reference to the motor
   */
  MotorController5010 setFollow(MotorController5010 motor, boolean inverted);

  /**
   * Inverts the motor
   *
   * @param inverted Whether the motor should be inverted
   * @return a reference to the motor
   */
  MotorController5010 invert(boolean inverted);

  /**
   * Sets the current limit
   *
   * @param limit The current limit
   * @return a reference to the motor
   */
  MotorController5010 setCurrentLimit(int limit);

  /**
   * Gets the motor encoder
   *
   * @return The motor encoder
   */
  GenericEncoder getMotorEncoder();

  /**
   * Gets the motor encoder
   *
   * @param sensorType The sensor type
   * @param countsPerRev The number of counts per revolution
   * @return The motor encoder
   */
  GenericEncoder getMotorEncoder(EncoderMeasurementType sensorType, int countsPerRev);

  /**
   * Gets the PID controller
   *
   * @return The PID controller
   */
  PIDController5010 getPIDController5010();

  /**
   * Gets the motor
   *
   * @return The motor
   */
  MotorController getMotor();

  /** Sets the motor to factory default */
  void factoryDefault();

  /**
   * Returns the default SysIdRoutine
   *
   * @param subsystemBase The subsystem
   * @return The default SysIdRoutine
   */
  SysIdRoutine getDefaultSysId(SubsystemBase subsystemBase);

  /**
   * Returns the simulated instance of the motor
   *
   * @return The simulated instance of the motor
   */
  DCMotor getMotorSimulationType();
}
