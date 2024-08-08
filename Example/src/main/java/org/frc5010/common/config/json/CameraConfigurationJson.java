// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.config.json;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.sensors.camera.GenericCamera;
import org.frc5010.common.sensors.camera.LimeLightCamera;
import org.frc5010.common.sensors.camera.PhotonVisionCamera;
import org.frc5010.common.subsystems.AprilTagPoseSystem;
import org.frc5010.common.subsystems.VisibleTargetSystem;
import org.frc5010.common.vision.AprilTags;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

/** Add your docs here. */
public class CameraConfigurationJson {
  /** Limelight constant */
  public static String LIMELIGHT = "limelight";
  /** PhotonVision constant */
  public static String PHOTON_VISION = "photonvision";
  /** AprilTag constant */
  public static String APRIL_TAG = "apriltag";
  /** Target camera constant */
  public static String TARGET = "target";

  /** Name of the camera */
  public String name;
  /** Use of the camera */
  public String use;
  /** Type of the camera */
  public String type;
  /** Optional strategy of the camera pose */
  public String strategy = "none";
  /** Column in SmartDashboard */
  public int column = 0;
  /** Camera X position from center in meters, in the robot's reference frame */
  public double x;
  /** Camera Y position from center in meters, in the robot's reference frame */
  public double y = 0;
  /** Camera Z position from center in meters, in the robot's reference frame */
  public double z;
  /** Camera roll angle in degrees, in the robot's reference frame */
  public double roll = 0;
  /** Camera pitch angle in degrees, in the robot's reference frame */
  public double pitch = 0;
  /** Camera yaw angle in degrees, in the robot's reference frame */
  public double yaw = 0;
  /** optional target height in meters */
  public double targetHeight = 0;

  /**
   * Configures the camera system based on the provided robot.
   *
   * @param robot the GenericRobot instance to configure the camera for
   */
  public void configureCamera(GenericRobot robot) {
    GenericCamera camera = null;
    Transform3d robotToCamera =
        new Transform3d(new Translation3d(x, y, z), new Rotation3d(roll, pitch, yaw));
    AprilTagPoseSystem atSystem = (AprilTagPoseSystem) robot.getSubsystem("apriltag");
    if (atSystem == null) {
      atSystem = new AprilTagPoseSystem(AprilTags.aprilTagFieldLayout);
      robot.addSubsystem("apriltag", atSystem);
    }

    switch (type) {
      case "limelight":
        {
          camera = new LimeLightCamera(name, column, robotToCamera);
          break;
        }
      case "photonvision":
        {
          camera =
              new PhotonVisionCamera(
                  name,
                  column,
                  AprilTags.aprilTagFieldLayout,
                  PoseStrategy.valueOf(strategy),
                  robotToCamera,
                  robot.getPoseSupplier());
          break;
        }
    }
    switch (use) {
      case "target":
        robot.addSubsystem(name, new VisibleTargetSystem(camera, targetHeight));
        break;
      case "apriltag":
        {
          atSystem.addCamera(camera);
          break;
        }
    }
  }
}