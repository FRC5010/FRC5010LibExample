// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.config.ConfigConstants;
import org.frc5010.common.constants.SwerveConstants;
import org.frc5010.common.drive.GenericDrivetrain;
import org.frc5010.common.drive.swerve.YAGSLSwerveDrivetrain;
import org.frc5010.common.motors.function.PercentControlMotor;
import org.frc5010.common.sensors.Controller;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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
    drivetrain = (GenericDrivetrain) subsystems.get(ConfigConstants.DRIVETRAIN);
    exampleSubsystem = new ExampleSubsystem();
  }

  @Override
  public void configureButtonBindings(Controller driver, Controller operator) {
    driver.createYButton().onTrue(exampleSubsystem.setVelocityControlMotorReference(() -> 3500))
        .onFalse(exampleSubsystem.setVelocityControlMotorReference(() -> 0));
    driver.createXButton().onTrue(exampleSubsystem.setVelocityControlMotorReference(() -> 2000))
        .onFalse(exampleSubsystem.setVelocityControlMotorReference(() -> 0));
    driver.createAButton().whileTrue(exampleSubsystem.setAngularMotorReference(() -> 90))
        .whileFalse(exampleSubsystem.setAngularMotorReference(() -> 0));
    driver.createBButton().whileTrue(((YAGSLSwerveDrivetrain)drivetrain).driveToPose(new Pose2d(8, 4, new Rotation2d())));
  }

  @Override
  public void setupDefaultCommands(Controller driver, Controller operator) {
    driver.setRightTrigger(driver.createRightTrigger());
    exampleSubsystem.setDefaultCommand(exampleSubsystem.getDefaultCommand(() -> driver.getRightTrigger()));
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
