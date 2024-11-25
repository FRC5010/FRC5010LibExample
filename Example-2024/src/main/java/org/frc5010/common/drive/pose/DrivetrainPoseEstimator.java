// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.drive.pose;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.frc5010.common.vision.AprilTags;
import org.frc5010.common.vision.VisionSystem;

/** Add your docs here. */
public class DrivetrainPoseEstimator {
  /** The vision system */
  private VisionSystem vision;

  /** The 2d field */
  private final Field2d field2d;
  /** The pose tracker */
  private final GenericPose poseTracker;
  /** Whether or not to disable the vision update */
  private boolean disableVisionUpdate = false;

  /** The closest tag to the robot */
  private int closestTagToRobot;
  /** The closest target to the robot */
  private Pose3d closestTargetToRobot;
  /** The tag poses */
  private List<Pose2d> tagPoses = new ArrayList<>();

  /**
   * Constructor for the DrivetrainPoseEstimator
   *
   * @param poseTracker the pose tracker to use
   * @param vision the vision system
   */
  public DrivetrainPoseEstimator(GenericPose poseTracker, VisionSystem vision) {
    this.poseTracker = poseTracker;
    this.vision = vision;
    field2d = poseTracker.getField();

    ShuffleboardTab tab = Shuffleboard.getTab("Pose");
    tab.addString("Pose (X,Y)", this::getFormattedPose).withPosition(11, 0);
    tab.addDoubleArray("Robot Pose3d", () -> getCurrentPose3dArray()).withPosition(11, 1);

    tab.addNumber("Pose Degrees", () -> (getCurrentPose().getRotation().getDegrees()))
        .withPosition(11, 2);
    tab.add(field2d).withPosition(0, 0).withSize(11, 5);

    for (AprilTag at : vision.getFieldLayout().getTags()) {
      if (at.pose.getX() != 0 && at.pose.getY() != 0 && at.pose.getZ() != 0) {
        field2d.getObject("Field Tag " + at.ID).setPose(at.pose.toPose2d());
        AprilTags.poseToID.put(at.pose.toPose2d(), at.ID);
        tagPoses.add(at.pose.toPose2d());
      }
    }
  }

  /**
   * Returns a formatted string representation of the current pose.
   *
   * @return a formatted string in the format "(x, y)" where x and y are the rounded coordinates of
   *     the current pose.
   */
  private String getFormattedPose() {
    var pose = getCurrentPose();
    return String.format("(%.2f, %.2f)", pose.getX(), pose.getY());
  }

  /**
   * Returns the current pose of the pose tracker.
   *
   * @return the current pose of the pose tracker as a Pose2d object
   */
  public Pose2d getCurrentPose() {
    return poseTracker.getCurrentPose();
  }

  /**
   * Returns the current pose of the pose tracker as a Pose3d object.
   *
   * @return the current pose of the pose tracker as a Pose3d object
   */
  public Pose3d getCurrentPose3d() {
    return new Pose3d(poseTracker.getCurrentPose());
  }

  /**
   * Sets the value of the `disableVisionUpdate` flag.
   *
   * @param disable the new value for the `disableVisionUpdate` flag
   */
  public void setDisableVisionUpdate(boolean disable) {
    disableVisionUpdate = disable;
  }

  /**
   * Calculates the projected pose of the robot in the field after a given time and robot field
   * speed.
   *
   * @param time the time in seconds
   * @param robotFieldSpeed the robot's speed in the field
   * @return the projected pose of the robot in the field
   */
  public Pose3d getProjectedPose3d(double time, ChassisSpeeds robotFieldSpeed) {
    Transform3d movement =
        new Transform3d(
            robotFieldSpeed.vxMetersPerSecond * time,
            robotFieldSpeed.vyMetersPerSecond * time,
            0,
            new Rotation3d());
    return getCurrentPose3d().plus(movement);
  }

  /**
   * Retrieves the current pose of the robot in 3D space as an array of doubles.
   *
   * @return An array of doubles containing the x, y, z coordinates of the pose, followed by the
   *     quaternion components (w, x, y, z) of the rotation.
   */
  public double[] getCurrentPose3dArray() {
    Pose3d pose = getCurrentPose3d();
    Quaternion rotation = pose.getRotation().getQuaternion();
    return new double[] {
      pose.getX(),
      pose.getY(),
      pose.getZ(),
      rotation.getW(),
      rotation.getX(),
      rotation.getY(),
      rotation.getZ()
    };
  }

  /**
   * Returns the current rotation of the gyroscope as a Rotation2d object.
   *
   * @return the current rotation of the gyroscope as a Rotation2d object
   */
  public Rotation2d getGyroRotation2d() {
    // System.out.println(poseTracker.getGyroRotation2d());
    return poseTracker.getGyroRotation2d();
  }

  /**
   * Returns the ID of the closest tag to the robot.
   *
   * @return the ID of the closest tag to the robot
   */
  public int getClosestTagToRobot() {
    closestTagToRobot = AprilTags.poseToID.get(getCurrentPose().nearest(tagPoses));
    return closestTagToRobot;
  }

  /**
   * Returns the pose of the closest tag to the robot.
   *
   * @return the pose of the closest tag to the robot as a Pose3d object
   */
  public Pose3d getPoseFromClosestTag() {
    Pose3d targetPose =
        vision.getFieldLayout().getTagPose(getClosestTagToRobot()).orElse(getCurrentPose3d());
    field2d.getObject("Closest Tag").setPose(targetPose.toPose2d());
    return targetPose;
  }

  /**
   * Retrieves the pose of the closest vision target to the robot.
   *
   * @return the pose of the closest vision target as a Pose3d object
   */
  public Pose3d getPoseFromClosestVisionTarget() {
    closestTargetToRobot = updatePoseFromClosestVisionTarget();
    return closestTargetToRobot;
  }

  /**
   * Updates the pose of the closest vision target to the robot based on the current vision data.
   *
   * @return the updated pose of the closest vision target as a Pose3d object, or null if the camera
   *     transform is null
   */
  public Pose3d updatePoseFromClosestVisionTarget() {
    double angleYaw = vision.getAngleX();
    double anglePitch = vision.getAngleY();
    double distance = -vision.getDistance();
    Transform3d cameraTransform = vision.getCameraToRobot();

    Pose3d targetPose = null;
    if (null != cameraTransform) {
      Pose2d currentPose2d = poseTracker.getCurrentPose();
      Pose3d currentPose3d =
          new Pose3d(
              currentPose2d.getX(),
              currentPose2d.getY(),
              0.0,
              new Rotation3d(0.0, 0.0, currentPose2d.getRotation().getRadians()));

      Pose3d cameraPose = currentPose3d.transformBy(cameraTransform);
      if (vision.isValidTarget()) {
        targetPose =
            cameraPose.transformBy(
                new Transform3d(
                    Math.cos(Units.degreesToRadians(-angleYaw)) * distance,
                    Math.sin(Units.degreesToRadians(-angleYaw)) * distance,
                    Math.sin(Units.degreesToRadians(-anglePitch)) * distance,
                    new Rotation3d(0, 0, 0)));
        field2d.getObject("Closest Target").setPose(targetPose.toPose2d());
      }

      field2d.getObject("Target Camera").setPose(cameraPose.toPose2d());
    }
    return targetPose;
  }

  /**
   * Sets the pose of a target object on the field.
   *
   * @param targetPose the pose to set for the target object
   * @param targetName the name of the target object
   */
  public void setTargetPoseOnField(Pose2d targetPose, String targetName) {
    field2d.getObject(targetName).setPose(targetPose);
  }

  /**
   * Perform all periodic pose estimation tasks.
   *
   * <p>This method updates the local measurements of the pose tracker and checks if the vision
   * update is disabled. If the vision update is not disabled, it retrieves the robot poses and pose
   * distances from the vision's raw values. It then iterates over each camera and checks if the
   * robot pose is not null. If the robot pose is not null, it retrieves the image capture time and
   * checks if the fiducial ID for the camera is greater than 0. If the condition is true, it sets
   * the visionUpdated flag to true, updates the dashboard with the camera name, and calls the
   * updateVisionMeasurements method of the pose tracker with the robot pose, image capture time,
   * and the standardized vector of the pose distance. Finally, it updates the dashboard with the
   * vision updating status and sets the robot pose on the field2d.
   */
  public void update() {

    poseTracker.updateLocalMeasurements();
    if (!disableVisionUpdate) {
      Map<String, Pose2d> poses = vision.getRawValues().getRobotPoses();
      Map<String, Double> poseDistances = vision.getRawValues().getPoseDistances();
      boolean visionUpdated = false;
      for (String camera : poses.keySet()) {
        SmartDashboard.putBoolean(camera, false);
        Pose2d robotPose = poses.get(camera);
        Double poseDistance = poseDistances.get(camera);
        if (null != robotPose) {
          double imageCaptureTime = vision.getRawValues().getLatency(camera);

          if (vision.getRawValues().getFiducialIds().get(camera) > 0) {
            visionUpdated = true;
            SmartDashboard.putBoolean(camera, true);
            poseTracker.updateVisionMeasurements(
                robotPose, imageCaptureTime, vision.getStdVector(poseDistance));
          }
        }
      }
      SmartDashboard.putBoolean("April Tag Pose Updating", visionUpdated);
    }
    field2d.setRobotPose(getCurrentPose());
  }

  /**
   * Force the pose estimator to a particular pose. This is useful for indicating to the software
   * when you have manually moved your robot in a particular position on the field (EX: when you
   * place it on the field at the start of the match).
   *
   * @param pose the pose to reset to
   */
  public void resetToPose(Pose2d pose) {
    poseTracker.resetToPose(pose);
  }
}
