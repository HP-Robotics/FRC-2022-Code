// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.UsbCamera;

import java.util.concurrent.TimeUnit;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.subsystems.LIDARSubsystem;
import frc.robot.subsystems.MagazineSubsystem;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.ClimberClimbCommand;
import frc.robot.commands.ClimberExtendCommand;
import frc.robot.commands.ClimberFastRetractCommand;
import frc.robot.commands.ClimberRetractCommand;
import frc.robot.commands.ClimberSlowExtendCommand;
import frc.robot.commands.ClimberSlowRetractCommand;
import frc.robot.commands.ClimberToggleRotationCommand;
import frc.robot.commands.DriveStraightCommand;
import frc.robot.commands.DriveTrackCargo;
import frc.robot.commands.DriveTrackHub;
import frc.robot.commands.DriveTurnCommand;
import frc.robot.commands.IntakeRunMotorCommand;
import frc.robot.commands.MagazineAndIntakeReverseCommand;
import frc.robot.commands.IntakeUpDownCommand;
import frc.robot.commands.MagazineToggleCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.DriveSetDistanceCommand;
import frc.robot.commands.ShooterShootCommand;
import frc.robot.commands.ShooterWheelCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  public ShuffleboardTab m_driverTab = Shuffleboard.getTab("Driver View");
  public static Boolean m_useShooter = true;
  private Boolean m_useClimber = true;
  private Boolean m_useIntake = true;
  private Boolean m_useDrive = true;
  private Boolean m_useMagazine = true;
  private Boolean m_usePneumatic = true;
  private Boolean m_useCamera = false;
  // The robot's subsystems and commands are defined here...
  private DriveSubsystem m_driveSubsystem;
  public ShooterSubsystem m_shooterSubsystem;
  private PneumaticSubsystem m_pneumaticSubsystem;
  private ClimberSubsystem m_climberSubsystem;
  private IntakeSubsystem m_intakeSubsystem;
  public final JoystickSubsystem m_joystickSubsystem = new JoystickSubsystem();
  private MagazineSubsystem m_magazineSubsystem;
  // private LIDARSubsystem m_LidarSubsystem = new LIDARSubsystem();

  private DriveManualCommand m_defaultCommand;
  private final SendableChooser<Command> m_autonomousChooser;

  private Command m_justShoot;
  private Command m_twoBallAuto;
  private Command m_threeBallAuto;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    if (m_useMagazine) {
      m_magazineSubsystem = new MagazineSubsystem();

    }

    if (m_usePneumatic) {
      m_pneumaticSubsystem = new PneumaticSubsystem();
    }

    if (m_useShooter) {
      m_shooterSubsystem = new ShooterSubsystem();
    }

    if (m_useClimber) {
      m_climberSubsystem = new ClimberSubsystem();
    }

    if (m_useIntake) {
      m_intakeSubsystem = new IntakeSubsystem();
    }

    if (m_useDrive) {
      m_driveSubsystem = new DriveSubsystem();
      m_defaultCommand = new DriveManualCommand(m_driveSubsystem, m_joystickSubsystem);
      m_driveSubsystem.setDefaultCommand(m_defaultCommand);
    }

    if (m_useShooter) {
      m_justShoot = new SequentialCommandGroup(
          new ShooterWheelCommand(m_shooterSubsystem),
          new ShooterShootCommand(m_shooterSubsystem).withTimeout(3.0));

      if (m_useDrive && m_useIntake && m_useMagazine && m_usePneumatic) {
        m_twoBallAuto = new SequentialCommandGroup(
            new InstantCommand(m_pneumaticSubsystem::intakeUp, m_pneumaticSubsystem),
            new ParallelCommandGroup(
                new IntakeRunMotorCommand(m_intakeSubsystem),
                new DriveSetDistanceCommand(m_driveSubsystem, 58)).withTimeout(2),
            new InstantCommand(m_pneumaticSubsystem::intakeDown, m_pneumaticSubsystem),
            new DriveSetDistanceCommand(m_driveSubsystem, -16).withTimeout(2),
            new ShooterWheelCommand(m_shooterSubsystem),
            new MagazineToggleCommand(m_magazineSubsystem, true),
            new ShooterShootCommand(m_shooterSubsystem).withTimeout(3),
            new PrintCommand("This is a test"),
            new ShooterShootCommand(m_shooterSubsystem).withTimeout(3),
            new MagazineToggleCommand(m_magazineSubsystem, false),
            new ShooterWheelCommand(m_shooterSubsystem));

      }
      m_threeBallAuto = new SequentialCommandGroup(
          new InstantCommand(m_pneumaticSubsystem::intakeUp, m_pneumaticSubsystem),
          new ShooterWheelCommand(m_shooterSubsystem),
          new ParallelDeadlineGroup(
              new DriveSetDistanceCommand(m_driveSubsystem, 58),
              new IntakeRunMotorCommand(m_intakeSubsystem),
              new MagazineToggleCommand(m_magazineSubsystem, true)).withTimeout(2),
          new InstantCommand(m_pneumaticSubsystem::intakeDown, m_pneumaticSubsystem),
          
              new DriveSetDistanceCommand(m_driveSubsystem, -16).withTimeout(1),
              new ShooterShootCommand(m_shooterSubsystem).withTimeout(2),
          new DriveTurnCommand(m_driveSubsystem, 111), // 103.27 clockwise
          new InstantCommand(m_pneumaticSubsystem::intakeUp, m_pneumaticSubsystem),
          new ParallelDeadlineGroup(
              new DriveSetDistanceCommand(m_driveSubsystem, 104),
              new IntakeRunMotorCommand(m_intakeSubsystem)).withTimeout(3),
          new DriveTurnCommand(m_driveSubsystem, -58.455), // 58.455 counterclockwise
          new DriveTrackHub(m_driveSubsystem, m_joystickSubsystem, m_shooterSubsystem).withTimeout(1),
          new InstantCommand(m_pneumaticSubsystem::intakeDown, m_pneumaticSubsystem),
          new ShooterShootCommand(m_shooterSubsystem).withTimeout(2),//*/
          new MagazineToggleCommand(m_magazineSubsystem, false),
          new ShooterWheelCommand(m_shooterSubsystem));

    }

    m_autonomousChooser = new SendableChooser<Command>();
    m_autonomousChooser.addOption("Do Nothing", new InstantCommand());

    if (m_useShooter) {
      m_autonomousChooser.addOption("Just Shoot", m_justShoot);
      m_autonomousChooser.setDefaultOption("Just Shoot", m_justShoot);

    }

    if (m_useShooter && m_useDrive && m_useIntake && m_useMagazine) {
      m_autonomousChooser.addOption("Two Ball Auto", m_twoBallAuto);
      m_autonomousChooser.addOption("Three Ball Auto", m_threeBallAuto);
    }
    SmartDashboard.putData("Autonomous Mode", m_autonomousChooser);

    if (m_useCamera) {
      UsbCamera usbCamera = CameraServer.startAutomaticCapture(0);
      if (usbCamera != null) {
        System.out.println("Yay, we have a camera!");
        usbCamera.setResolution(160, 120);
        usbCamera.setFPS(10);
      } else {
        System.out.println("startAutomaticCapture() failed, no USB Camera");
      }
    }


    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    if (m_useShooter) {
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.B)
          .whileHeld(new ShooterShootCommand(m_shooterSubsystem))
          .whenPressed(new MagazineToggleCommand(m_magazineSubsystem, true))
          .whenReleased(new MagazineToggleCommand(m_magazineSubsystem, false));
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.A)
          .whenPressed(new ShooterWheelCommand(m_shooterSubsystem));

      new Trigger(m_joystickSubsystem::povUp)
          .whenActive(new InstantCommand(m_shooterSubsystem::highSpeed, m_shooterSubsystem));

      new Trigger(m_joystickSubsystem::povDown)
          .whenActive(new InstantCommand(m_shooterSubsystem::lowSpeed, m_shooterSubsystem));
    }

    /*
     * new JoystickButton(m_joystickSubsystem.m_operator, Constants.B)
     * .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, -48));
     * new JoystickButton(m_joystickSubsystem.m_operator, Constants.A)
     * .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, 48));
     */

    // new JoystickButton(m_joystickSubsystem.m_driver, 9)
    // .whenPressed(new DriveTurnCommand(m_driveSubsystem, 90));
    if (m_useDrive) {
      new JoystickButton(m_joystickSubsystem.m_driver, Constants.driveStraight)
          .whileHeld(new DriveStraightCommand(m_driveSubsystem, m_joystickSubsystem));
      new JoystickButton(m_joystickSubsystem.m_driver, 13)
          .whenPressed(new InstantCommand(m_driveSubsystem::playorchestra, m_driveSubsystem));
      new JoystickButton(m_joystickSubsystem.m_driver, 12)
          .whenPressed(new InstantCommand(m_driveSubsystem::stoporchestra, m_driveSubsystem));
      new JoystickButton(m_joystickSubsystem.m_driver, 2)
          .whileHeld(new DriveTrackCargo(m_driveSubsystem, m_joystickSubsystem, m_intakeSubsystem));
    }

    if (m_useShooter && m_useDrive) {
      new JoystickButton(m_joystickSubsystem.m_driver, 8)
          .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, 36).withTimeout(5));
      new JoystickButton(m_joystickSubsystem.m_driver, 4)
          .whileHeld(new DriveTrackHub(m_driveSubsystem, m_joystickSubsystem, m_shooterSubsystem));
    }

    // new JoystickButton(driver,7)
    // .whenPressed(new ClimberToggleRotationCommand(m_climberSubsystem,
    // m_pneumaticSubsystem));
    if (m_useIntake && m_usePneumatic && m_useMagazine) {
      new JoystickButton(m_joystickSubsystem.m_driver, 1)
          .whileHeld(new IntakeRunMotorCommand(m_intakeSubsystem))
          .whileHeld(new IntakeUpDownCommand(m_pneumaticSubsystem))
          .whenPressed(new MagazineToggleCommand(m_magazineSubsystem, false))
          .whenReleased(new MagazineToggleCommand(m_magazineSubsystem, false));
    }
    if (m_useIntake && m_useMagazine) {
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.Y)
          .whileHeld(new MagazineAndIntakeReverseCommand(m_intakeSubsystem, m_magazineSubsystem))
          .whileHeld(new IntakeUpDownCommand(m_pneumaticSubsystem));
    }

    if (m_useMagazine) {
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.X)
          .whenPressed(new MagazineToggleCommand(m_magazineSubsystem, false));
    }
    if (m_useClimber) {
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.LB)
          .whileHeld(new ClimberExtendCommand(m_climberSubsystem));
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.RB)
          .whileHeld(new ClimberRetractCommand(m_climberSubsystem));

      new Trigger(m_joystickSubsystem::triggerPressedRight)
          .whenActive(new InstantCommand(m_pneumaticSubsystem::climberForward, m_pneumaticSubsystem));

      new Trigger(m_joystickSubsystem::triggerPressedLeft)
          .whenActive(new InstantCommand(m_pneumaticSubsystem::climberBack, m_pneumaticSubsystem));

      new JoystickButton(m_joystickSubsystem.m_operator, 8)
          .whenPressed(new SequentialCommandGroup(
              (new ClimberClimbCommand(m_climberSubsystem)),
              new ClimberToggleRotationCommand(m_climberSubsystem, m_pneumaticSubsystem)));

      new JoystickButton(m_joystickSubsystem.m_driver, 5)
          .whenPressed(new InstantCommand(m_pneumaticSubsystem::intakeUp, m_pneumaticSubsystem));

      new Trigger(m_joystickSubsystem::povRight)
          .whileActiveContinuous(new SequentialCommandGroup(
              new ClimberSlowRetractCommand(m_climberSubsystem),
              new ClimberFastRetractCommand(m_climberSubsystem),
              new ClimberSlowExtendCommand(m_climberSubsystem),
              new InstantCommand(m_pneumaticSubsystem::climberBack, m_pneumaticSubsystem),
              new WaitCommand(10)));
    }

  }



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    if (m_autonomousChooser.getSelected() == null) {
      return new InstantCommand();
    } else {
      return m_autonomousChooser.getSelected();
    }
  }
}
