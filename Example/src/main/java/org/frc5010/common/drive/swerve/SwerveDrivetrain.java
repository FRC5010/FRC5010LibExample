// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.drive.swerve;

import org.frc5010.common.arch.Persisted;
import org.frc5010.common.constants.GenericDrivetrainConstants;
import org.frc5010.common.constants.SwerveConstants;
import org.frc5010.common.drive.pose.DrivePoseEstimator;
import org.frc5010.common.sensors.gyro.GenericGyro;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;

/** Add your docs here. */
public abstract class SwerveDrivetrain extends SwerveDriveFunctions {

  private GenericGyro gyro;

  private GenericDrivetrainConstants swerveConstants;

  private boolean ready = false;
  private Persisted<Double> maxChassisVelocity;
  private DrivePoseEstimator poseEstimator;

  public SwerveDrivetrain(
      LoggedMechanism2d mechVisual, GenericGyro genericGyro, SwerveConstants swerveConstants) {
    this.gyro = genericGyro;
    this.swerveConstants = swerveConstants;
    gyro.reset();
  }

  public SwerveDrivetrain(
      LoggedMechanism2d mechVisual, GenericDrivetrainConstants swerveConstants) {
    this.swerveConstants = swerveConstants;
  }

  public DrivePoseEstimator getPoseEstimator() {
    return poseEstimator;
  }

  public SwerveConstants getSwerveConstants() {
    return ((SwerveConstants) swerveConstants);
  }

  public double getGyroRate() {
    return gyro.getRate();
  }
}
