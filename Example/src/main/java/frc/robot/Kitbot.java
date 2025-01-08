// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.config.ConfigConstants;
import org.frc5010.common.drive.GenericDrivetrain;
import org.frc5010.common.sensors.Controller;
import edu.wpi.first.wpilibj2.command.Command;

public class Kitbot extends GenericRobot {

  GenericDrivetrain drivetrain;

  public Kitbot(String directory) {
    super(directory);
    drivetrain = (GenericDrivetrain) getSubsystem(ConfigConstants.DRIVETRAIN);
  }

  public void configureButtonBindings(Controller driver, Controller operator) {
    
  }

  public void setupDefaultCommands(Controller driver, Controller operator) {
    drivetrain.setDefaultCommand(drivetrain.createDefaultCommand(driver));
  }

  public void initAutoCommands() {
    drivetrain.setAutoBuilder();
  }

  public Command generateAutoCommand(Command autoCommand) {
    return drivetrain.generateAutoCommand(autoCommand);
  }
}
