package org.frc5010.common.arch;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.frc5010.common.config.RobotParser;
import org.frc5010.common.constants.GenericDrivetrainConstants;
import org.frc5010.common.constants.RobotConstantsDef;
import org.frc5010.common.sensors.Controller;
import org.frc5010.common.subsystems.Color;
import org.frc5010.common.telemetery.WpiDataLogging;

/** Robots should extend this class as the entry point into using the library */
public abstract class GenericRobot extends GenericMechanism {
  /** Selector for autonmous modes */
  protected SendableChooser<Command> selectableCommand;
  /** The driver controller */
  protected Controller driver;
  /** The operator controller */
  protected Controller operator;
  /** The current alliance color */
  protected static Alliance alliance;
  /** The map of subsystems created by the configuration system */
  protected Map<String, GenericSubsystem> subsystems = new HashMap<>();
  /** The map of sensors created by the configuration system */
  protected Map<String, Controller> controllers = new HashMap<>();
  /** The configuration parser */
  protected RobotParser parser;
  /** Constants that are used to configure the drivetrain */
  protected GenericDrivetrainConstants drivetrainConstants = new GenericDrivetrainConstants();
  /** The pose supplier */
  protected Supplier<Pose2d> poseSupplier = () -> new Pose2d();

  /** The log level enums */
  public enum LogLevel {
    /** The debug log level */
    DEBUG,
    /** The competition log level */
    COMPETITION
  }

  /** The current log level */
  public static LogLevel logLevel = LogLevel.DEBUG;

  /**
   * Creates a new robot using the provided configuration directory
   *
   * @param directory the directory to read from
   */
  public GenericRobot(String directory) {
    super(Class.class.getName());
    try {
      parser = new RobotParser(directory, this);
      parser.createRobot(this);

      driver = controllers.get("driver");
      operator = controllers.get("operator");
      if (!operator.isPluggedIn()) {
        operator = driver;
        driver.setSingleControllerMode(true);
      }
      DriverStation.silenceJoystickConnectionWarning(true);
      alliance = determineAllianceColor();
      values.declare("Alliance", alliance.toString());

      // Put Mechanism 2d to SmartDashboard
      mechVisual =
          new Mechanism2d(
              PersistedEnums.ROBOT_VISUAL_H.getInteger(),
              RobotConstantsDef.robotVisualV.getInteger());
      SmartDashboard.putData("Robot Visual", mechVisual);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  /** Creates a new robot using a programmatic configuration */
  public GenericRobot() {
    super(Class.class.getName());

    // Setup controllers
    driver = new Controller(Controller.JoystickPorts.ZERO.ordinal());
    controllers.put("driver", driver);
    operator = new Controller(Controller.JoystickPorts.ONE.ordinal());
    controllers.put("operator", operator);
    if (!operator.isPluggedIn()) {
      operator = driver;
      driver.setSingleControllerMode(true);
    }
    // Put Mechanism 2d to SmartDashboard
    mechVisual =
        new Mechanism2d(
            PersistedEnums.ROBOT_VISUAL_H.getInteger(),
            RobotConstantsDef.robotVisualV.getInteger());
    SmartDashboard.putData("Robot Visual", mechVisual);

    DriverStation.silenceJoystickConnectionWarning(true);
    alliance = determineAllianceColor();
    values.declare("Alliance", alliance.toString());
  }

  /**
   * Get a subsystem from the configuration
   *
   * @param name the key of the subsystem
   * @return the subsystem
   */
  public GenericSubsystem getSubsystem(String name) {
    return subsystems.get(name);
  }

  /**
   * Get the current log level
   *
   * @return the current log level
   */
  public static LogLevel getLoggingLevel() {
    return logLevel;
  }

  /**
   * Set the current log level
   *
   * @param level the new log level
   */
  public static void setLoggingLevel(LogLevel level) {
    logLevel = level;
  }

  /**
   * Return the Robot simulation visual
   *
   * @return the Mechanism 2d
   */
  public Mechanism2d getMechVisual() {
    return mechVisual;
  }

  /** Initialize the robot depending on whether we are simulating or not */
  @Override
  protected void initRealOrSim() {
    if (RobotBase.isReal()) {
      WpiDataLogging.start(true);
    } else {
      WpiDataLogging.start(false);
      // NetworkTableInstance instance = NetworkTableInstance.getDefault();
      // instance.stopServer();
      // set the NT server if simulating this code.
      // "localhost" for photon on desktop, or "photonvision.local" / "[ip-address]"
      // for coprocessor
      // instance.setServer("localhost");
      // instance.startClient4("myRobot");
    }
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  public void configureButtonBindings() {
    configureButtonBindings(driver, operator);
  }

  /** Setup default commands depending on the robot mode */
  public void setupDefaultCommands() {
    if (DriverStation.isTeleop() || DriverStation.isAutonomous()) {
      setupDefaultCommands(driver, operator);
    } else if (DriverStation.isTest()) {
      setupTestDefaultCommmands(driver, operator);
    }
  }

  /** Build the auto commands and command chooser */
  public void buildAutoCommands() {
    initAutoCommands();

    // TODO: Figure out Pathplanner Warmup Command

    selectableCommand = AutoBuilder.buildAutoChooser();
    if (null != selectableCommand) {
      shuffleTab.add("Auto Modes", selectableCommand).withSize(2, 1);
    }
  }

  /**
   * Get the selected auto command and also allow the robot to edit it
   *
   * @return the selected auto command
   */
  public Command getAutonomousCommand() {
    return generateAutoCommand(selectableCommand.getSelected());
  }

  /**
   * Determine the alliance color, returning Blue by default
   *
   * @return the alliance
   */
  public Alliance determineAllianceColor() {
    Optional<Alliance> color = DriverStation.getAlliance();
    return color.orElse(Alliance.Blue);
  }

  /**
   * Choose the alliance color, returning Orange by default if undeterminable
   *
   * @return the alliance color
   */
  public static Color chooseAllianceDisplayColor() {
    Optional<Alliance> allianceColor = DriverStation.getAlliance();
    if (allianceColor.isPresent()) {
      return allianceColor.get() == Alliance.Red ? Color.RED : Color.BLUE;
    }
    return Color.ORANGE;
  }

  /**
   * Get the current alliance
   *
   * @return the alliance
   */
  public static Alliance getAlliance() {
    return alliance;
  }

  /**
   * Add a controller to the configuration
   *
   * @param name the name of the controller
   * @param controller the controller
   */
  public void addController(String name, Controller controller) {
    controllers.put(name, controller);
  }

  /**
   * Get a controller from the configuration
   *
   * @param name the name of the controller
   * @return the controller
   */
  public Controller getController(String name) {
    return controllers.get(name);
  }

  /**
   * Add a subsystem to the configuration
   *
   * @param name the name of the subsystem
   * @param subsystem the subsystem
   */
  public void addSubsystem(String name, GenericSubsystem subsystem) {
    subsystems.put(name, subsystem);
  }

  /**
   * Set the pose supplier
   *
   * @param poseSupplier the pose supplier
   */
  public void setPoseSupplier(Supplier<Pose2d> poseSupplier) {
    this.poseSupplier = poseSupplier;
  }

  /**
   * Get the pose supplier
   *
   * @return the pose supplier
   */
  public Supplier<Pose2d> getPoseSupplier() {
    return poseSupplier;
  }

  /**
   * Get the drivetrain constants
   *
   * @return the drivetrain constants
   */
  public GenericDrivetrainConstants getDrivetrainConstants() {
    return drivetrainConstants;
  }

  /**
   * Set the drivetrain constants
   *
   * @param constants the drivetrain constants
   */
  public void setDrivetrainConstants(GenericDrivetrainConstants constants) {
    this.drivetrainConstants = constants;
  }
}
