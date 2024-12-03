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
    public final String INPUT_TABLE = "Input";
    public final String OUTPUT_TABLE = "Output";

    public final String INPUT_ANGLE = "Input Angle";
    public final String OUTPUT_ANGLE = "Output Angle";
    public final String INPUT_BOOLEAN = "Input Boolean";
    public final String OUTPUT_BOOLEAN = "Output Boolean";
    public final String INPUT_DOUBLE = "Input Double";
    public final String OUTPUT_DOUBLE = "Output Double";
    public final String INPUT_FLOAT = "Input Float";
    public final String OUTPUT_FLOAT = "Output Float";
    public final String INPUT_LENGTH = "Input Length";
    public final String OUTPUT_LENGTH = "Output Length";
    public final String INPUT_LONG = "Input Long";
    public final String OUTPUT_LONG = "Output Long";
    public final String INPUT_STRING = "Input String";
    public final String OUTPUT_STRING = "Output String";
    public final String INPUT_TIME = "Input Time";
    public final String OUTPUT_TIME = "Output Time";
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
        inputAngle = displayValues.makeConfigAngle(INPUT_ANGLE);
        inputBoolean = displayValues.makeConfigBoolean(INPUT_BOOLEAN);
        inputDouble = displayValues.makConfigDouble(INPUT_DOUBLE);
        inputFloat = displayValues.makeFloat(INPUT_FLOAT);
        displayValues.nextColumn(INPUT_TABLE);
        inputLength = displayValues.makeLength(INPUT_LENGTH);
        inputLong = displayValues.makeLong(INPUT_LONG);
        inputString = displayValues.makeString(INPUT_STRING);
        inputTime = displayValues.makeTime(INPUT_TIME);
        displayValues.nextColumn(OUTPUT_TABLE);
        outputAngle = displayValues.makeDisplayAngle(OUTPUT_ANGLE);
        outputBoolean = displayValues.makeDisplayBoolean(OUTPUT_BOOLEAN);
        outputDouble = displayValues.makeDisplayDouble(OUTPUT_DOUBLE);
        outputFloat = displayValues.makeFloat(OUTPUT_FLOAT);
        displayValues.nextColumn(OUTPUT_TABLE + " 2");
        outputLength = displayValues.makeLength(OUTPUT_LENGTH);
        outputLong = displayValues.makeLong(OUTPUT_LONG);
        outputString = displayValues.makeString(OUTPUT_STRING);
        outputTime = displayValues.makeTime(OUTPUT_TIME);
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
