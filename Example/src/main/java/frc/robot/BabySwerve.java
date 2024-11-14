// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.config.json.DrivetrainPropertiesJson;
import org.frc5010.common.constants.SwerveConstants;
import org.frc5010.common.drive.GenericDrivetrain;
import org.frc5010.common.sensors.Controller;

import edu.wpi.first.wpilibj2.command.Command;

/** This is an example robot class. */
public class BabySwerve extends GenericRobot {
  SwerveConstants swerveConstants;
  GenericDrivetrain drivetrain;
  DisplayValueSubsystem displayValueSubsystem = new DisplayValueSubsystem();

  public BabySwerve(String directory) {
    super(directory);
    drivetrain = (GenericDrivetrain) getSubsystem(DrivetrainPropertiesJson.DRIVE_TRAIN);
  }

  @Override
  public void configureButtonBindings(Controller driver, Controller operator) {
  }

  @Override
  public void setupDefaultCommands(Controller driver, Controller operator) {
    drivetrain.setDefaultCommand(drivetrain.createDefaultCommand(driver));
  }

  @Override
  public void initAutoCommands() {
    drivetrain.setAutoBuilder();
  }

  @Override
  public Command generateAutoCommand(Command autoCommand) {
    return drivetrain.generateAutoCommand(autoCommand);
  }

}
