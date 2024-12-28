// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;

import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.telemetry.DisplayAngle;
import org.frc5010.common.telemetry.DisplayBoolean;
import org.frc5010.common.telemetry.DisplayDouble;
import org.frc5010.common.telemetry.DisplayFloat;
import org.frc5010.common.telemetry.DisplayLength;
import org.frc5010.common.telemetry.DisplayLong;
import org.frc5010.common.telemetry.DisplayString;
import org.frc5010.common.telemetry.DisplayTime;


/**
 * Tests the classes in the {@link org.frc5010.common.telemetry} package that
 * Display values
 */
public class DisplayValueSubsystem extends GenericSubsystem {
    DisplayAngle inputAngle;
    DisplayAngle outputAngle;
    DisplayBoolean inputBoolean;
    DisplayBoolean outputBoolean;
    DisplayDouble inputDouble;
    DisplayDouble outputDouble;
    DisplayFloat inputFloat;
    DisplayFloat outputFloat;
    DisplayLength inputLength;
    DisplayLength outputLength;
    DisplayLong inputLong;
    DisplayLong outputLong;
    DisplayString inputString;
    DisplayString outputString;
    DisplayTime inputTime;
    DisplayTime outputTime;
    DisplayAngle outAngle;

    public DisplayValueSubsystem() {
        super();
        outAngle = displayValues.makeInfoAngle("Out Angle");
        outputAngle = displayValues.makeDisplayAngle("OUTPUT_ANGLE");
        outputBoolean = displayValues.makeDisplayBoolean("OUTPUT_BOOLEAN");
        outputDouble = displayValues.makeDisplayDouble("OUTPUT_DOUBLE");
        outputFloat = displayValues.makeDisplayFloat("OUTPUT_FLOAT");
        displayValues.nextColumn("Config");
        inputAngle = displayValues.makeConfigAngle("INPUT_ANGLE");
        inputBoolean = displayValues.makeConfigBoolean("INPUT_BOOLEAN");
        inputDouble = displayValues.makeConfigDouble("INPUT_DOUBLE");
        inputFloat = displayValues.makeConfigFloat("INPUT_FLOAT");
        displayValues.nextColumn("Input");
        inputLength = displayValues.makeConfigLength("INPUT_LENGTH");
        inputLong = displayValues.makeConfigLong("INPUT_LONG");
        inputString = displayValues.makeConfigString("INPUT_STRING");
        inputTime = displayValues.makeConfigTime("INPUT_TIME");
        displayValues.nextColumn("Debug-Info");
        outputLength = displayValues.makeInfoLength("OUTPUT_LENGTH");
        outputLong = displayValues.makeInfoLong("OUTPUT_LONG");
        outputString = displayValues.makeInfoString("OUTPUT_STRING");
        outputTime = displayValues.makeInfoTime("OUTPUT_TIME");
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        outputAngle.setAngle(inputAngle);
        outputBoolean.setValue(inputBoolean.getValue());
        outputDouble.setValue(inputDouble.getValue());
        outputFloat.setValue(inputFloat.getValue());
        outputLength.setLength(inputLength);
        outputLong.setValue(inputLong.getValue());
        outputString.setValue(inputString.getValue());
        outputTime.setTime(inputTime);
        outAngle.setAngle(Degrees.of(Math.random() * 360.0));
    }
}
