// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.telemetry;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;

import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.arch.GenericRobot.LogLevel;
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
        return "Shuffleboard/" + tab.getTitle() + "/" + table.getTitle();
    }

    public static boolean robotIsAtLogLevel(LogLevel logLevel) {
        switch (logLevel) {
            case DEBUG: {
                return GenericRobot.logLevel == LogLevel.DEBUG || GenericRobot.logLevel == LogLevel.INFO;
            }
            case INFO: {
                return GenericRobot.logLevel == LogLevel.INFO || GenericRobot.logLevel == LogLevel.DEBUG;
            }
            case CONFIG: {
                return GenericRobot.logLevel == LogLevel.CONFIG || GenericRobot.logLevel == LogLevel.DEBUG;
            }
            case COMPETITION: {
                return true;
            }
            default: {
                return true;
            }
        }
    }

    public DisplayAngle makeDisplayAngle(String name) {
        DisplayAngle angle = new DisplayAngle(0, Degrees, name, getNtName());
        return angle;
    }

    public DisplayAngle makeInfoAngle(String name) {
        DisplayAngle angle = new DisplayAngle(0, Degrees, name, getNtName(), LogLevel.INFO);
        return angle;
    }

    public DisplayAngle makeConfigAngle(String name) {
        DisplayAngle angle = new DisplayAngle(0, Degrees, name, getNtName(), LogLevel.CONFIG);
        return angle;
    }

    public DisplayLength makeDisplayLength(String name) {
        DisplayLength length = new DisplayLength(0, Meters, name, getNtName());
        return length;
    }

    public DisplayLength makeInfoLength(String name) {
        DisplayLength length = new DisplayLength(0, Meters, name, getNtName(), LogLevel.INFO);
        return length;
    }

    public DisplayLength makeConfigLength(String name) {
        DisplayLength length = new DisplayLength(0, Meters, name, getNtName(), LogLevel.CONFIG);
        return length;
    }

    public DisplayTime makeDisplayTime(String name) {
        DisplayTime time = new DisplayTime(0, Seconds, name, getNtName());
        return time;
    }

    public DisplayTime makeInfoTime(String name) {
        DisplayTime time = new DisplayTime(0, Seconds, name, getNtName(), LogLevel.INFO);
        return time;
    }

    public DisplayTime makeConfigTime(String name) {
        DisplayTime time = new DisplayTime(0, Seconds, name, getNtName(), LogLevel.CONFIG);
        return time;
    }

    public DisplayVoltage makeDisplayVoltage(String name) {
        DisplayVoltage voltage = new DisplayVoltage(0, Volts, name, getNtName());
        return voltage;
    }

    public DisplayVoltage makeInfoVoltage(String name) {
        DisplayVoltage voltage = new DisplayVoltage(0, Volts, name, getNtName(), LogLevel.INFO);
        return voltage;
    }

    public DisplayVoltage makeConfigVoltage(String name) {
        DisplayVoltage voltage = new DisplayVoltage(0, Volts, name, getNtName(), LogLevel.CONFIG);
        return voltage;
    }

    public DisplayBoolean makeDisplayBoolean(String name) {
        DisplayBoolean booleanValue = new DisplayBoolean(false, name, getNtName());
        return booleanValue;
    }

    public DisplayBoolean makeInfoBoolean(String name) {
        DisplayBoolean booleanValue = new DisplayBoolean(false, name, getNtName(), LogLevel.INFO);
        return booleanValue;
    }

    public DisplayBoolean makeConfigBoolean(String name) {
        DisplayBoolean booleanValue = new DisplayBoolean(false, name, getNtName(), LogLevel.CONFIG);
        return booleanValue;
    }

    public DisplayDouble makeDisplayDouble(String name) {
        DisplayDouble doubleValue = new DisplayDouble(0, name, getNtName());
        return doubleValue;
    }

    public DisplayDouble makeInfoDouble(String name) {
        DisplayDouble doubleValue = new DisplayDouble(0, name, getNtName(), LogLevel.INFO);
        return doubleValue;
    }

    public DisplayDouble makConfigDouble(String name) {
        DisplayDouble doubleValue = new DisplayDouble(0, name, getNtName(), LogLevel.CONFIG);
        return doubleValue;
    }

    public DisplayString makeDisplayString(String name) {
        DisplayString stringValue = new DisplayString("", name, getNtName());
        return stringValue;
    }

    public DisplayString makeInfoString(String name) {
        DisplayString stringValue = new DisplayString("", name, getNtName(), LogLevel.INFO);
        return stringValue;
    }

    public DisplayString makeConfigString(String name) {
        DisplayString stringValue = new DisplayString("", name, getNtName(), LogLevel.CONFIG);
        return stringValue;
    }

    public DisplayLong makeDisplayLong(String name) {
        DisplayLong longValue = new DisplayLong(0, name, getNtName());
        return longValue;
    }

    public DisplayLong makeInfoLong(String name) {
        DisplayLong longValue = new DisplayLong(0, name, getNtName(), LogLevel.INFO);
        return longValue;
    }

    public DisplayLong makeConfigLong(String name) {
        DisplayLong longValue = new DisplayLong(0, name, getNtName(), LogLevel.CONFIG);
        return longValue;
    }

    public DisplayFloat makeDisplayFloat(String name) {
        DisplayFloat floatValue = new DisplayFloat(0, name, getNtName());
        return floatValue;
    }

    public DisplayFloat makeInfoFloat(String name) {
        DisplayFloat floatValue = new DisplayFloat(0, name, getNtName(), LogLevel.INFO);
        return floatValue;
    }

    public DisplayFloat makeConfigFloat(String name) {
        DisplayFloat floatValue = new DisplayFloat(0, name, getNtName(), LogLevel.CONFIG);
        return floatValue;
    }

    public DisplayCurrent makeDisplayCurrent(String name) {
        DisplayCurrent currentValue = new DisplayCurrent(0, Amps, name, getNtName());
        return currentValue;
    }

    public DisplayCurrent makeInfoCurrent(String name) {
        DisplayCurrent currentValue = new DisplayCurrent(0, Amps, name, getNtName(), LogLevel.INFO);
        return currentValue;
    }

    public DisplayCurrent makeConfigCurrent(String name) {
        DisplayCurrent currentValue = new DisplayCurrent(0, Amps, name, getNtName(), LogLevel.CONFIG);
        return currentValue;
    }
}
