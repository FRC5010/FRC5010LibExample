// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.frc5010.common.arch.GenericSubsystem;
import org.frc5010.common.telemetry.DisplayAngle;
import org.frc5010.common.telemetry.DisplayBoolean;
import org.frc5010.common.telemetry.DisplayDouble;
import org.frc5010.common.telemetry.DisplayFloat;
import org.frc5010.common.telemetry.DisplayLength;
import org.frc5010.common.telemetry.DisplayLong;
import org.frc5010.common.telemetry.DisplayString;
import org.frc5010.common.telemetry.DisplayTime;
import org.frc5010.common.units.Angle.AngleUnit;
import org.frc5010.common.units.Length.LengthUnit;
import org.frc5010.common.units.Time.TimeUnit;

/** Tests the classes in the {@link org.frc5010.common.telemetry} package that Display values */
public class DisplayValueSubsystem extends GenericSubsystem {
  public final String TAB = "Test";
  public final String INPUT_TABLE = "Test/Input";
  public final String OUTPUT_TABLE = "Test/Output";

  DisplayAngle inputAngle = new DisplayAngle(AngleUnit.TURN, 0.0, "Input Angle", INPUT_TABLE);
  DisplayAngle outputAngle = new DisplayAngle(AngleUnit.DEGREE, 0.0, "Output Angle", OUTPUT_TABLE);
  DisplayBoolean inputBoolean = new DisplayBoolean(false, "Input Boolean", INPUT_TABLE);
  DisplayBoolean outputBoolean = new DisplayBoolean(false, "Output Boolean", OUTPUT_TABLE);
  DisplayDouble inputDouble = new DisplayDouble(0.0, "Input Double", INPUT_TABLE);
  DisplayDouble outputDouble = new DisplayDouble(0.0, "Output Double", OUTPUT_TABLE);
  DisplayFloat inputFloat = new DisplayFloat(0, "Input Float", INPUT_TABLE);
  DisplayFloat outputFloat = new DisplayFloat(0, "Output Float", OUTPUT_TABLE);
  DisplayLength inputLength = new DisplayLength(LengthUnit.FOOT, 0.0, "Input Length", INPUT_TABLE);
  DisplayLength outputLength = new DisplayLength(LengthUnit.METER, 0.0, "Output Length", OUTPUT_TABLE);
  DisplayLong inputLong = new DisplayLong(0, "Input Long", INPUT_TABLE);
  DisplayLong outputLong = new DisplayLong(0, "Output Long", OUTPUT_TABLE);
  DisplayString inputString = new DisplayString("default", "Input String", INPUT_TABLE);
  DisplayString outputString = new DisplayString("default", "Output String", OUTPUT_TABLE);
  DisplayTime inputTime = new DisplayTime(TimeUnit.SECOND, 0.0, "Input Time", INPUT_TABLE);
  DisplayTime outputTime = new DisplayTime(TimeUnit.MILLISECOND, 0.0, "Output Time", OUTPUT_TABLE);

    public DisplayValueSubsystem() {
        super(null);
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
    }
}
