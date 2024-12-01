// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.auto;

import org.frc5010.common.arch.GenericCommandSequence;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

/** Add your docs here. */
public class AutoSequence extends GenericCommandSequence {

    /**
     * Returns a command that resets the odometry to the specified pose.
     * 
     * @param pose The pose to which to set the odometry.
     * @return The command that will reset the odometry.
     */
    public Command resetOdometry(Pose2d pose) {
        return AutoBuilder.resetOdom(pose);
    }

    public AutoSequence() {}
}