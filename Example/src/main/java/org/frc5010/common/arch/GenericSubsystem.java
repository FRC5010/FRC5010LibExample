// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.arch;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Base class for subsystems that provides default logging and network table support */
public class GenericSubsystem extends SubsystemBase implements WpiHelperInterface {
  /** The network table values */
  protected final WpiNetworkTableValuesHelper values = new WpiNetworkTableValuesHelper();
  /** The log prefix */
  protected String logPrefix = getClass().getSimpleName();
  /** The mechanism simulation */
  protected Mechanism2d mechanismSimulation;

  /** Creates a new LoggedSubsystem. */
  public GenericSubsystem() {
    WpiNetworkTableValuesHelper.register(this);
  }

  /**
   * Get the mechanism simulation visual
   *
   * @return the mechanism visual
   */
  public Mechanism2d getMechSimulation() {
    return mechanismSimulation;
  }

  /**
   * Set the mechanism simulation visual
   *
   * @param mechSim the mechanism visual
   */
  public void setMechSimulation(Mechanism2d mechSim) {
    mechanismSimulation = mechSim;
  }

  /**
   * Called when the command is created and registers it with the network tables
   *
   * @param builder - The sendable builder
   */
  @Override
  public void initSendable(SendableBuilder builder) {
    log(logPrefix + ": Initializing sendables.");
    values.initSendables(builder, this.getClass().getSimpleName());
  }
}
