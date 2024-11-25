package org.frc5010.common.config;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import java.io.File;
import java.io.IOException;
import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.config.json.SubsystemJson;

public class SubsystemParser {
  protected Mechanism2d mechanismSimulation;
  protected String robotDirectory;
  protected GenericRobot robot;

  /**
   * Creates a new RobotParser.
   *
   * @param robotDirectory the directory to read from
   * @param robot the robot being configured
   * @throws IOException
   */
  public SubsystemParser(String robotDirectory, GenericRobot robot) throws IOException {
    mechanismSimulation = robot.getMechVisual();
    this.robotDirectory = robotDirectory;
    this.robot = robot;
  }

  private void checkDirectory(File directory, String configFile) {
    assert new File(directory, configFile).exists();
  }

  public void parseSubsystem(GenericSubsystem genericSubsystem, String configFile)
      throws StreamReadException, DatabindException, IOException {
    File directory = new File(Filesystem.getDeployDirectory(), robotDirectory + "/subsystems/");
    checkDirectory(directory, configFile);

    genericSubsystem.setMechSimulation(mechanismSimulation);

    // Read in the subsystem configuration
    // Read in the robot configuration
    SubsystemJson subsystemJson =
        new ObjectMapper().readValue(new File(directory, configFile), SubsystemJson.class);
    subsystemJson.configureSubsystem(genericSubsystem, directory);
  }
}
