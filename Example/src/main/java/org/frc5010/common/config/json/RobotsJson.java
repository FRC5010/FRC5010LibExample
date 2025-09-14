// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.config.json;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.utils.RobotIdentity;

/** RobotsJson is used to parse JSON configuration files to build a robot. */
public class RobotsJson {
  /** A map of robot IDs to robot classes */
  public Map<String, RobotIdentity> robots = new HashMap<>();

  /**
   * Creates a robot instance based on the current robot ID.
   *
   * <p>The current robot ID is determined by the MAC address of the robot. The robot ID is matched
   * against the robots in the robots.json file. If a match is found, the robot instance is created
   * using the class specified in the robots.json file, and the robot directory is passed to the
   * constructor of the robot instance.
   *
   * <p>If no match is found, an error message is printed and a RuntimeException is thrown.
   *
   * @return the created robot instance
   */
  public GenericRobot createRobot() {
    String whichRobot = RobotIdentity.whereAmI();
    String robotDirectory = "default";
    for (String robotName : robots.keySet()) {
      RobotIdentity robotIdentity = robots.get(robotName);
      if ((RobotBase.isSimulation() && robotIdentity.simulate)
          || (DriverStation.isFMSAttached() && robotIdentity.competition)
          || robotIdentity.id.equals(whichRobot)) {
        robotDirectory = robotName;
        break;
      }
    }
    if (robots.containsKey(robotDirectory)) {
      RobotIdentity robotIdentity = robots.get(robotDirectory);
      try {
        GenericRobot robot =
            Class.forName(robotIdentity.robotClass)
                .asSubclass(GenericRobot.class)
                .getDeclaredConstructor(String.class)
                .newInstance(robotDirectory);
        return robot;
      } catch (ClassNotFoundException
          | NoSuchMethodException
          | InstantiationException
          | IllegalAccessException
          | InvocationTargetException e) {
        System.err.println("Error creating robot instance: " + e.getMessage());
        throw new RuntimeException(e);
      }
    } else {
      System.out.println(
          "Robot not found in robots.json ID: "
              + whichRobot
              + ".\nReplace the key of your robot with the ID displayed.");
      throw new RuntimeException("Robot not found in robots.json ID: " + whichRobot);
    }
  }
}
