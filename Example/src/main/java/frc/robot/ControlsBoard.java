// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.sensors.Controller;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/** This is an example robot class. */
public class ControlsBoard extends GenericRobot {
    MotorRunner motorSubsystem;

  public ControlsBoard(String directory) {
    
    super(directory);
    motorSubsystem = new MotorRunner();
  }

  @Override
  public void configureButtonBindings(Controller driver, Controller operator) {
  }

  @Override
  public void setupDefaultCommands(Controller driver, Controller operator) {
   motorSubsystem.setDefaultCommand(
    Commands.run(
        () -> motorSubsystem.runNeo(driver.getLeftYAxis()), motorSubsystem)
   );
  }

  @Override
  public void initAutoCommands() {
    
  }

  @Override
  public Command generateAutoCommand(Command autoCommand) {
    return Commands.idle();
  }

}
