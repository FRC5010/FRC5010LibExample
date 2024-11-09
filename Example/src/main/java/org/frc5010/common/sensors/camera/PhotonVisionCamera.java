// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.sensors.camera;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Timer;
import java.util.Optional;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

/** A camera using the PhotonVision library. */
public class PhotonVisionCamera extends GenericCamera {
  /** The camera */
  protected PhotonCamera camera;
  /** The field layout */
  protected AprilTagFieldLayout fieldLayout;
  /** The target, if any */
  protected Optional<PhotonTrackedTarget> target = Optional.empty();
  /** The latest camera result */
  protected PhotonPipelineResult camResult;

  /**
   * Constructor
   *
   * @param name - the name of the camera
   * @param colIndex - the column index for the dashboard
   * @param cameraToRobot - the camera-to-robot transform
   */
  public PhotonVisionCamera(String name, int colIndex, Transform3d cameraToRobot) {
    super(name, colIndex, cameraToRobot);
    this.robotToCamera = cameraToRobot;
    camera = new PhotonCamera(name);
  }

  /** Update the camera and target with the latest result */
  @Override
  public void updateCameraInfo() {
    camResult = camera.getLatestResult();
  }

  /**
   * Does the camera have a valid target?
   *
   * @return true if the camera has a valid target
   */
  @Override
  public boolean hasValidTarget() {
    return camResult.hasTargets();
  }

  /**
   * Get the target yaw
   *
   * @return the target yaw
   */
  @Override
  public double getTargetYaw() {
    return target.map(t -> t.getYaw()).orElse(Double.MAX_VALUE);
  }

  /**
   * Get the target pitch
   *
   * @return the target pitch
   */
  @Override
  public double getTargetPitch() {
    return target.map(t -> t.getPitch()).orElse(Double.MAX_VALUE);
  }

  /**
   * Get the target area
   *
   * @return the target area
   */
  @Override
  public double getTargetArea() {
    return target.map(t -> t.getArea()).orElse(Double.MAX_VALUE);
  }

  /**
   * Get the latency in seconds
   *
   * @return the latency in seconds
   */
  @Override
  public double getLatency() {
    return Timer.getFPGATimestamp() - (camResult.getLatencyMillis() / 1000.0);
  }

  /**
   * Get the target pose estimate relative to the robot.
   *
   * @return the target pose estimate relative to the robot
   */
  @Override
  public Optional<Pose3d> getRobotPose() {
    return Optional.empty();
  }

  /**
   * Get the target pose estimate relative to the robot.
   *
   * @return the target pose estimate relative to the robot
   */
  @Override
  public Optional<Pose3d> getRobotToTargetPose() {
    return Optional.empty();
  }
}
