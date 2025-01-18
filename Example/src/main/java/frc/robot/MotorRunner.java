// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.motors.hardware.NEO;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorRunner extends SubsystemBase {
  NEO motor;


  /** Creates a new MotorRunner. */
  public MotorRunner() {
    motor = new NEO(4);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public void runNeo(double speed) {
    motor.set(speed);
  }
  
}
