// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.telemetry;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.WpiHelperInterface;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/** Registers values with the network tables */
public class DisplayValuesHelper implements WpiHelperInterface {
    protected final ShuffleboardTab tab;
    protected ShuffleboardLayout table;
    protected int column = 0;

    public DisplayValuesHelper(String tab, String table) {
        this.tab = Shuffleboard.getTab(tab);
        this.table = this.tab.getLayout(table, BuiltInLayouts.kList).withSize(2, 4).withPosition(column, 0);
    }

    public void nextColumn(String name) {
        column += 2;
        table = tab.getLayout(name, BuiltInLayouts.kList).withSize(2, 4).withPosition(column, 0);
    }

    private String getNtName() {
        return "SmartDashboard/" + tab.getTitle() + "/" + table.getTitle();
    }

    public static boolean isDisplayed(boolean debug) {
        return debug || GenericRobot.logLevel == GenericRobot.LogLevel.INFO
                || GenericRobot.logLevel == GenericRobot.LogLevel.DEBUG;
    }

    public DisplayAngle makeDisplayAngle(String name) {
        DisplayAngle angle = new DisplayAngle(0, Degrees, name, getNtName());
        table.addDouble(name, () -> angle.getAngleInDegrees());
        return angle;
    }

    public DisplayAngle makeInfoAngle(String name) {
        DisplayAngle angle = new DisplayAngle(0, Degrees, name, getNtName(), true);
        if (isDisplayed(true)) {
            table.addDouble(name, () -> angle.getAngleInDegrees());
        }
        return angle;
    }

    public ConfigAngle makeConfigAngle(String name) {
        ConfigAngle angle = new ConfigAngle(0, Degrees, name, getNtName());
        return angle;
    }

    public DisplayLength makeLength(String name) {
        DisplayLength length = new DisplayLength(0, Meters, name, getNtName());
        table.addDouble(name, () -> length.getLengthInMeters());
        return length;
    }

    public DisplayTime makeTime(String name) {
        DisplayTime time = new DisplayTime(0, Seconds, name, getNtName());
        table.addDouble(name, () -> time.getTimeInSeconds());
        return time;
    }

    public DisplayVoltage makeVoltage(String name) {
        DisplayVoltage voltage = new DisplayVoltage(0, Volts, name, getNtName());
        table.addDouble(name, () -> voltage.getVoltageInVolts());
        return voltage;
    }

    public DisplayBoolean makeDisplayBoolean(String name) {
        DisplayBoolean booleanValue = new DisplayBoolean(false, name, getNtName());
        table.addBoolean(name, () -> booleanValue.getValue());
        return booleanValue;
    }

    public DisplayBoolean makeInfoBoolean(String name) {
        DisplayBoolean booleanValue = new DisplayBoolean(false, name, getNtName(), true);
        if (isDisplayed(true)) {
            table.addBoolean(name, () -> booleanValue.getValue());
        }
        return booleanValue;
    }

    public ConfigBoolean makeConfigBoolean(String name) {
        ConfigBoolean booleanValue = new ConfigBoolean(false, name, getNtName());
        return booleanValue;
    }

    public DisplayDouble makeDisplayDouble(String name) {
        DisplayDouble doubleValue = new DisplayDouble(0, name, getNtName());
        table.addDouble(name, () -> doubleValue.getValue());
        return doubleValue;
    }

    public DisplayDouble makeInfoDouble(String name) {
        DisplayDouble doubleValue = new DisplayDouble(0, name, getNtName(), true);
        if (isDisplayed(true)) {
            table.addDouble(name, () -> doubleValue.getValue());
        }
        return doubleValue;
    }

    public ConfigDouble makConfigDouble(String name) {
        ConfigDouble doubleValue = new ConfigDouble(0, name, getNtName());
        return doubleValue;
    }

    public DisplayString makeString(String name) {
        DisplayString stringValue = new DisplayString("", name, getNtName());
        table.addString(name, () -> stringValue.getValue());
        return stringValue;
    }

    public DisplayLong makeLong(String name) {
        DisplayLong longValue = new DisplayLong(0, name, getNtName());
        table.addNumber(name, () -> longValue.getValue());
        return longValue;
    }

    public DisplayFloat makeFloat(String name) {
        DisplayFloat floatValue = new DisplayFloat(0, name, getNtName());
        table.addNumber(name, () -> floatValue.getValue());
        return floatValue;
    }
}
