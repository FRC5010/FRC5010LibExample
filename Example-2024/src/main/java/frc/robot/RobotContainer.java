// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.WpiHelperInterface;
import org.frc5010.common.arch.WpiNetworkTableValuesHelper;
import org.frc5010.common.constants.Constants;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer implements WpiHelperInterface {
	public static Constants constants;
	private GenericRobot robot;

  public RobotContainer() {
    constants = new Constants();

    robot = new ExampleRobot("basic_robot");
    configureButtonBindings();
 		initAutoCommands();
		WpiNetworkTableValuesHelper.loadRegisteredToNetworkTables();

  }

  private void configureButtonBindings() {
		robot.configureButtonBindings();
	}

	// Just sets up defalt commands (setUpDeftCom)
	public void setupDefaults() {
		robot.setupDefaultCommands();
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return robot.getAutonomousCommand();
	}

	public void initAutoCommands() {
		robot.buildAutoCommands();
	}

	public void disabledBehavior() {
		robot.disabledBehavior();
	}
}