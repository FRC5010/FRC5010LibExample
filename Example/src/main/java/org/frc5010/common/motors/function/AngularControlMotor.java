// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.motors.function;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color8Bit;
import org.frc5010.common.motors.MotorController5010;
import org.frc5010.common.motors.MotorFactory;
import org.frc5010.common.sensors.encoder.GenericEncoder;
import org.frc5010.common.sensors.encoder.SimulatedEncoder;
import org.frc5010.common.telemetry.DisplayDouble;
import org.frc5010.common.units.Angle;
import org.frc5010.common.units.Length;

/** Provides PID control for angular motors */
public class AngularControlMotor extends GenericControlledMotor {
  protected MechanismLigament2d simulatedArm;
  protected MechanismLigament2d setpoint;
  protected MechanismRoot2d root;
  protected SingleJointedArmSim simMotor;
  protected SimulatedEncoder simEncoder;
  protected GenericEncoder encoder;
  protected Length armLength = Length.Meter(0);
  protected Angle minAngle = Angle.Degree(0.0);
  protected Angle maxAngle = Angle.Degree(0.0);
  protected Angle startingAngle = Angle.Degree(0.0);
  protected DisplayDouble reference;
  protected DisplayDouble position;
  protected DisplayDouble control;
  protected double kG = 0.0;

  public AngularControlMotor(MotorController5010 motor) {
    super(motor);
    encoder = _motor.getMotorEncoder();
  }

  public AngularControlMotor setupSimulatedMotor(
      double gearing,
      double mass,
      Length armLength,
      Angle minAngle,
      Angle maxAngle,
      boolean simulateGravity,
      double kG,
      Angle startingAngle) {
    this.armLength = armLength;
    this.minAngle = minAngle;
    this.maxAngle = maxAngle;
    this.startingAngle = startingAngle;
    this.kG = kG;

    simMotor =
        new SingleJointedArmSim(
            _motor.getMotorSimulationType(),
            gearing,
            SingleJointedArmSim.estimateMOI(armLength.getMeters(), mass),
            armLength.getMeters(),
            minAngle.getRadians(),
            maxAngle.getRadians(),
            simulateGravity,
            startingAngle.getRadians());
    simEncoder =
        new SimulatedEncoder(
            MotorFactory.getNextSimEncoderPort(), MotorFactory.getNextSimEncoderPort());
    simEncoder.setPosition(startingAngle.getDegrees());
    simEncoder.setPositionConversion(0.1);
    return this;
  }

  @Override
  public AngularControlMotor setVisualizer(
      Mechanism2d visualizer, Pose3d robotToMotor, String visualName) {
    super.setVisualizer(visualizer, robotToMotor, visualName);

    reference = new DisplayDouble(0, "reference", visualName);
    position = new DisplayDouble(0, "position", visualName);
    control = new DisplayDouble(0, "control", visualName);
    root =
        visualizer.getRoot(
            _visualName,
            getSimX(Length.Meter(robotToMotor.getX())),
            getSimY(Length.Meter(robotToMotor.getZ())));
    simulatedArm =
        new MechanismLigament2d(
            visualName + "-arm",
            armLength.getMeters(),
            startingAngle.getDegrees(),
            5,
            new Color8Bit(MotorFactory.getNextVisualColor()));
    setpoint =
        new MechanismLigament2d(
            visualName + "-setpoint",
            armLength.getMeters(),
            startingAngle.getDegrees(),
            5,
            new Color8Bit(MotorFactory.getNextVisualColor()));
    root.append(simulatedArm);
    root.append(setpoint);
    return this;
  }

  @Override
  public void setReference(double reference) {
    super.setReference(reference);
    if (!RobotBase.isReal()) {
      control.setValue(calculateControlEffort(simEncoder.getPosition()));
      _motor.set(control.getValue() / RobotController.getBatteryVoltage());
    }
  }

  public double getPivotPosition() {
    if (RobotBase.isReal()) {
      return encoder.getPosition() > 180 ? encoder.getPosition() - 360 : encoder.getPosition();
    } else {
      return simEncoder.getPosition();
    }
  }

  @Override
  public double getFeedForward() {
    ArmFeedforward pivotFeedforward =
        new ArmFeedforward(
            getMotorFeedFwd().getkS(), kG, getMotorFeedFwd().getkV(), getMotorFeedFwd().getkA());
    double ff =
        pivotFeedforward.calculate(
            Units.degreesToRadians(getPivotPosition()), Units.degreesToRadians(0));
    return ff;
  }

  @Override
  public void draw() {
    double currentPosition = 0;
    if (RobotBase.isReal()) {
      currentPosition = encoder.getPosition();
    } else {
      currentPosition = simEncoder.getPosition();
    }
    position.setValue(currentPosition);
    reference.setValue(getReference());
    simulatedArm.setAngle(currentPosition);
    setpoint.setAngle(getReference());
  }

  @Override
  public void simulationUpdate() {
    control.setValue(calculateControlEffort(simEncoder.getPosition()));
    simMotor.setInput(_motor.get() * RobotController.getBatteryVoltage());
    simMotor.update(0.020);
    simEncoder.setPosition(Units.radiansToDegrees(simMotor.getAngleRads()));

    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(simMotor.getCurrentDrawAmps()));
  }

  public boolean isAtMaximum() {
    return encoder.getPosition() >= maxAngle.getRadians();
  }

  public boolean isAtMinimum() {
    return encoder.getPosition() <= minAngle.getRadians();
  }

  public boolean isAtStartingAngle() {
    return encoder.getPosition() == startingAngle.getRadians();
  }
}
