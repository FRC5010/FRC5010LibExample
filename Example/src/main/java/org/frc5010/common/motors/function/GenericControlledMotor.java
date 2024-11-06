// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.motors.function;

import org.frc5010.common.constants.GenericPID;
import org.frc5010.common.motors.MotorController5010;
import org.frc5010.common.motors.PIDController5010;

/** Add your docs here. */
public class GenericControlledMotor extends GenericFunctionalMotor implements PIDController5010 {
  PIDController5010 pid;

  public GenericControlledMotor(MotorController5010 motor) {
    super(motor);
    pid = motor.getPIDController5010();
  }

  @Override
  public PIDController5010 getPIDController5010() {
    return pid;
  }

  @Override
  public void setTolerance(double tolerance) {
    pid.setTolerance(tolerance);
  }

  @Override
  public double getTolerance() {
    return pid.getTolerance();
  }

  @Override
  public void setValues(GenericPID pidValues) {
    pid.setValues(pidValues);
  }

  @Override
  public void setP(double p) {
    pid.setP(p);
  }

  @Override
  public void setI(double i) {
    pid.setI(i);
  }

  @Override
  public void setD(double d) {
    pid.setD(d);
  }

  @Override
  public void setF(double f) {
    pid.setF(f);
  }

  @Override
  public void setIZone(double iZone) {
    pid.setIZone(iZone);
  }

  @Override
  public void setOutputRange(double min, double max) {
    pid.setOutputRange(min, max);
  }

  @Override
  public void setReference(double reference) {
    pid.setReference(reference);
  }

  @Override
  public void setReference(double reference, PIDControlType controlType, double feedforward) {
    pid.setReference(reference, controlType, feedforward);
  }

  @Override
  public void setControlType(PIDControlType controlType) {
    pid.setControlType(controlType);
  }

  @Override
  public GenericPID getValues() {
    return pid.getValues();
  }

  @Override
  public double getP() {
    return pid.getP();
  }

  @Override
  public double getI() {
    return pid.getI();
  }

  @Override
  public double getD() {
    return pid.getD();
  }

  @Override
  public double getF() {
    return pid.getF();
  }

  @Override
  public double getIZone() {
    return pid.getIZone();
  }

  @Override
  public double getReference() {
    return pid.getReference();
  }

  @Override
  public PIDControlType getControlType() {
    return pid.getControlType();
  }

  @Override
  public boolean isAtTarget() {
    return pid.isAtTarget();
  }
}
