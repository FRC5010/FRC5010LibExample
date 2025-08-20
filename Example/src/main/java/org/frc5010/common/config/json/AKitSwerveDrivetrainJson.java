// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.config.json;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.generated.TunerConstants;
import java.io.File;
import java.io.IOException;
import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.config.ConfigConstants;
import org.frc5010.common.constants.RobotConstantsDef;
import org.frc5010.common.drive.swerve.GenericSwerveDrivetrain;
import org.frc5010.common.drive.swerve.akit.AkitSwerveDrive;
import org.frc5010.common.drive.swerve.akit.GyroIO;
import org.frc5010.common.drive.swerve.akit.GyroIOPigeon2;
import org.frc5010.common.drive.swerve.akit.ModuleIOSim;
import org.frc5010.common.drive.swerve.akit.ModuleIOSpark;
import org.frc5010.common.drive.swerve.akit.ModuleIOSparkTalon;
import org.frc5010.common.drive.swerve.akit.ModuleIOTalonFX;
import org.littletonrobotics.junction.mechanism.LoggedMechanism2d;

/** Add your docs here. */
public class AKitSwerveDrivetrainJson implements DrivetrainPropertiesJson {
  public String type = "SparkTalon";

  @Override
  public void readDrivetrainConfiguration(GenericRobot robot, File directory) throws IOException {}

  @Override
  public void createDriveTrain(GenericRobot robot) {
    AkitSwerveDrive drive;
    if (RobotBase.isSimulation()) {
      drive =
          new AkitSwerveDrive(
              new GyroIO() {},
              new ModuleIOSim(),
              new ModuleIOSim(),
              new ModuleIOSim(),
              new ModuleIOSim());
    } else {
      if ("SparkTalon".equals(type)) {
        drive =
            new AkitSwerveDrive(
                new GyroIOPigeon2(),
                new ModuleIOSparkTalon(TunerConstants.FrontLeft),
                new ModuleIOSparkTalon(TunerConstants.FrontRight),
                new ModuleIOSparkTalon(TunerConstants.BackLeft),
                new ModuleIOSparkTalon(TunerConstants.BackRight));
      } else if ("Spark".equals(type)) {
        drive =
            new AkitSwerveDrive(
                new GyroIOPigeon2(),
                new ModuleIOSpark(0),
                new ModuleIOSpark(1),
                new ModuleIOSpark(2),
                new ModuleIOSpark(3));
      } else if ("Talon".equals(type)) {
        drive =
            new AkitSwerveDrive(
                new GyroIOPigeon2(),
                new ModuleIOTalonFX(TunerConstants.FrontLeft),
                new ModuleIOTalonFX(TunerConstants.FrontRight),
                new ModuleIOTalonFX(TunerConstants.BackLeft),
                new ModuleIOTalonFX(TunerConstants.BackRight));
      } else {
        throw new IllegalArgumentException("Unknown AkitSwerveDrive type: " + type);
      }
    }
    GenericSwerveDrivetrain drivetrain =
        new GenericSwerveDrivetrain(
            new LoggedMechanism2d(RobotConstantsDef.robotVisualH, RobotConstantsDef.robotVisualV),
            robot.getDrivetrainConstants(),
            drive);
    robot.addSubsystem(ConfigConstants.DRIVETRAIN, drivetrain);
    robot.setPoseSupplier(() -> drivetrain.getPoseEstimator().getCurrentPose());
  }
}
