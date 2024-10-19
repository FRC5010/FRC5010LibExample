// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.subsystems;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.sensors.camera.GenericCamera;

/**
 * This class is an abstract class that needs to be implemented by any subclass of CameraSystem. It
 * is responsible for updating the camera information.
 */
public abstract class CameraSystem extends GenericSubsystem {
  /** The camera object. */
  protected GenericCamera camera;

  /**
   * Creates a new CameraSystem.
   *
   * @param camera the camera object
   */
  public CameraSystem(GenericCamera camera) {
    super();
    this.camera = camera;
  }

  /**
   * This method is called once per scheduler run and it calls the updateCameraInfo() method.
   *
   * @see #updateCameraInfo()
   */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateCameraInfo();
  }

  /**
   * This is an abstract method that needs to be implemented by any subclass of CameraSystem. It is
   * responsible for updating the camera information.
   */
  public void updateCameraInfo() {
    camera.update();
  }

  /**
   * Get the distance to the target - need to be implemented by any subclass of CameraSystem
   *
   * @return the distance to the target
   */
  public abstract double getDistanceToTarget();

  /**
   * Does the camera have a valid target?
   *
   * @return true if the camera has a valid target
   */
  protected abstract boolean hasValidTarget();

  public Trigger hasAValidTarget() {
    return new Trigger(this::hasValidTarget);
  }
}
