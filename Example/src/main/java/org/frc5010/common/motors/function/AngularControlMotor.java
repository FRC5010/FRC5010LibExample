// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.motors.function;

import org.frc5010.common.motors.MotorController5010;

/** Provides PID control for angular motors */
public class AngularControlMotor extends GenericControlledMotor {
  public AngularControlMotor(MotorController5010 motor) {
    super(motor);
  }
}
