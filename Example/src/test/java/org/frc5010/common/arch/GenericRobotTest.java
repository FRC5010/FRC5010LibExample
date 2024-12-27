// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.arch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.frc5010.common.subsystems.Color;
import org.frc5010.robot.TestRobot;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

/** Add your docs here. */
public class GenericRobotTest {

  @Test
  public void testCreateDefaultGenericRobot() {
    TestRobot robot = new TestRobot();
    assertNotNull(robot);
    assertNotNull(robot.getMechVisual());
    assertEquals(Alliance.Blue, TestRobot.getAlliance());
    assertEquals(GenericRobot.LogLevel.COMPETITION, TestRobot.getLoggingLevel());
    assertNotNull(robot.getController("driver"));
    assertNotNull(robot.getController("operator"));
  }

  @Test
  public void testDetermineAllianceColor() {
    TestRobot robot = new TestRobot();
    assertEquals(Alliance.Blue, robot.determineAllianceColor());
  }

  @Test
  public void testChooseAllianceColor() {
    assertEquals(Color.ORANGE, GenericRobot.chooseAllianceDisplayColor());
  }
}
