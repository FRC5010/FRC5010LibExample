// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.GenericRobot.LogLevel;
import org.frc5010.common.constants.GenericDrivetrainConstants;

/** The base JSON class for robot configurations */
public class RobotJson {
  /* User configuration file for the power mode */
  public String userConfig = "competitionMode.json";

  /** The logging level for the robot */
  public String logLevel = "COMPETITION";

  /** Drivetrain configuration */
  public String driveType = "YAGSL_SWERVE_DRIVE";

  /** Drivetrain width between wheel centers */
  public double trackWidth = 0.0;

  /** Drivetrain length between wheel centers */
  public double wheelBase = 0.0;

  /** Drivetrain wheel diameter */
  public double wheelDiameter = 0.0;

  /** Drivetrain physical max speed */
  public double physicalMaxSpeed = 0.0;

  /** Drivetrain gear ratio between drive motor and wheels */
  public double driveMotorGearRatio = 1.0;

  /** Mechanism definition files */
  public String[] mechanismDefinitions;

  /**
   * Reads the robot configuration from the given directory
   *
   * @param robot the robot being configured
   * @param directory the directory to read from
   * @throws IOException
   */
  public void configureRobot(GenericRobot robot, File directory) throws IOException {
    GenericRobot.setLoggingLevel(LogLevel.valueOf(logLevel));
    GenericDrivetrainConstants drivetrainConstants = robot.getDrivetrainConstants();
    drivetrainConstants.setTrackWidth(trackWidth);
    drivetrainConstants.setWheelBase(wheelBase);
    drivetrainConstants.setWheelDiameter(wheelDiameter);
    drivetrainConstants.setkPhysicalMaxSpeedMetersPerSecond(physicalMaxSpeed);

    UserModeJson userModeJson =
        new ObjectMapper().readValue(new File(directory, userConfig), UserModeJson.class);
    drivetrainConstants.setkTeleDriveMaxSpeedMetersPerSecond(userModeJson.maxSpeed);
    drivetrainConstants.setkTeleDriveMaxAngularSpeedRadiansPerSecond(userModeJson.maxAngularSpeed);
    drivetrainConstants.setkTeleDriveMaxAccelerationUnitsPerSecond(userModeJson.maxAccelleration);
    drivetrainConstants.setkTeleDriveMaxAngularAccelerationUnitsPerSecond(
        userModeJson.maxAngularAccelleration);
    drivetrainConstants.setkDriveMotorGearRatio(driveMotorGearRatio);
  }

  /**
   * Reads the mechanism definition files
   *
   * @param robot the robot being configured
   */
  public void readMechanismDefinitionss(GenericRobot robot) {}
}
