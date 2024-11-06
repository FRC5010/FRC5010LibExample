// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.config.json.DrivetrainPropertiesJson;
import org.frc5010.common.constants.SwerveConstants;
import org.frc5010.common.drive.GenericDrivetrain;
import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.sensors.Controller;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.Command;

/** This is an example robot class. */
public class ExampleRobot extends GenericRobot {
  SwerveConstants swerveConstants;
  GenericDrivetrain drivetrain;
  PercentControlMotor percentControlMotor;
  DisplayValueSubsystem displayValueSubsystem = new DisplayValueSubsystem();
  ExampleSubsystem exampleSubsystem;

  public ExampleRobot(String directory) {
    super(directory);
    percentControlMotor = new PercentControlMotor(MotorFactory.NEO(1))
      .setVisualizer(mechVisual, new Pose3d(0.1, 0.0, 0.1, new Rotation3d()), "example")
      .setupSimulatedMotor(1, 1);
    drivetrain = (GenericDrivetrain) getSubsystem(DrivetrainPropertiesJson.DRIVE_TRAIN);

    exampleSubsystem = new ExampleSubsystem(percentControlMotor);
  }

  @Override
  public void configureButtonBindings(Controller driver, Controller operator) {
    exampleSubsystem.setDefaultCommand(
      exampleSubsystem.getDefaultCommand(() -> operator.getLeftYAxis()));
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
