// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;

import javax.swing.plaf.TreeUI;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.subsystems.LIDARSubsystem;
import frc.robot.subsystems.MagazineSubsystem;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.ClimberExtendCommand;
import frc.robot.commands.ClimberRetractCommand;
import frc.robot.commands.ClimberToggleRotationCommand;
import frc.robot.commands.DriveStraightCommand;
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
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ShooterSubsystem;

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
  private  CameraServer m_camera = null;
  public static Boolean m_useShooter = false;
  private Boolean m_useClimber = false;
  private Boolean m_useIntake = true;
  private Boolean m_useDrive = false;
  private Boolean m_useMagazine = true;
  private Boolean m_usePneumatic = true;
  // The robot's subsystems and commands are defined here...
  private DriveSubsystem m_driveSubsystem;
  public ShooterSubsystem m_shooterSubsystem;
  private PneumaticSubsystem m_pneumaticSubsystem;
  private ClimberSubsystem m_climberSubsystem;
  private IntakeSubsystem m_intakeSubsystem;
  public final JoystickSubsystem m_joystickSubsystem = new JoystickSubsystem();
  private MagazineSubsystem m_magazineSubsystem;
  //private LIDARSubsystem m_LidarSubsystem = new LIDARSubsystem();
  
  private DriveManualCommand m_defaultCommand;
  private final SendableChooser<Command> m_autonomousChooser;

  private Command m_justShoot;
  private Command m_twoBallAuto;
  // arcade drive
  /*
   * () -> {
   * return (Math.pow(driver.getRawAxis(0), 3));
   * });
   */

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
      m_justShoot = new SequentialCommandGroup(
          new ShooterWheelCommand(m_shooterSubsystem),
          new ShooterShootCommand(m_shooterSubsystem));
      if (m_useDrive && m_useIntake && m_useMagazine) {
        m_twoBallAuto = new SequentialCommandGroup(
            new ShooterWheelCommand(m_shooterSubsystem),
            new ShooterShootCommand(m_shooterSubsystem).withTimeout(3),
            new IntakeUpDownCommand(m_pneumaticSubsystem),
            new MagazineToggleCommand(m_magazineSubsystem, false),
            new IntakeRunMotorCommand(m_intakeSubsystem),
            new DriveSetDistanceCommand(m_driveSubsystem, 120),
            new IntakeRunMotorCommand(m_intakeSubsystem),
            new IntakeUpDownCommand(m_pneumaticSubsystem),
            new DriveSetDistanceCommand(m_driveSubsystem, -120),
            new ShooterShootCommand(m_shooterSubsystem).withTimeout(3));
      }

    }
    
    if (m_useClimber) {
      m_climberSubsystem = new ClimberSubsystem();
      new ClimberExtendCommand(m_climberSubsystem);
      new ClimberRetractCommand(m_climberSubsystem);
    }
    if (m_useIntake) {
      m_intakeSubsystem = new IntakeSubsystem();
    }
    
    if (m_useDrive) {
      m_driveSubsystem = new DriveSubsystem();
      m_defaultCommand = new DriveManualCommand(m_driveSubsystem, m_joystickSubsystem);
      m_driveSubsystem.setDefaultCommand(m_defaultCommand);
    }

    m_autonomousChooser = new SendableChooser<Command>();
    m_autonomousChooser.addOption("Do Nothing", new InstantCommand());

    if (m_useShooter) {
      m_autonomousChooser.addOption("Just Shoot", m_justShoot);
      m_autonomousChooser.setDefaultOption("Just Shoot", m_justShoot);
    }

    if (m_useShooter && m_useDrive && m_useIntake && m_useMagazine) {
      m_autonomousChooser.addOption("Two Ball Auto", m_twoBallAuto);
    }
    SmartDashboard.putData("Autonomous Mode", m_autonomousChooser);

    
/*
    UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
    usbCamera.setResolution(160, 120);
    usbCamera.setFPS(10);
    MjpegServer mjpegServer1 = new MjpegServer("serve_USB Camera 0", 1181);
    mjpegServer1.setSource(usbCamera);
    System.out.println(mjpegServer1.getListenAddress());
*/

   /* m_camera = CameraServer.getInstance();
    if (m_camera != null) {
      UsbCamera usbCamera = m_camera.startAutomaticCapture();
      if (usbCamera != null) {
        System.out.println("Yay, we have a camera!");
        usbCamera.setResolution(160, 120);
        usbCamera.setFPS(10);
      } else {
        System.out.println("startAutomaticCapture() failed, no USB Camera");
      }
      
    } else {
      System.out.println("CAMERA WAS NOT CONNECTED");
    }
    */
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
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.X)
          .whileHeld(new ShooterShootCommand(m_shooterSubsystem));
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.Y)
          .whenPressed(new ShooterWheelCommand(m_shooterSubsystem));
    }

    /*
     * new JoystickButton(m_joystickSubsystem.m_operator, Constants.B)
     * .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, -48));
     * new JoystickButton(m_joystickSubsystem.m_operator, Constants.A)
     * .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, 48));
     */
    if (m_useDrive) {
      new JoystickButton(m_joystickSubsystem.m_driverR, Constants.driveStraight)
          .whileHeld(new DriveStraightCommand(m_driveSubsystem, m_joystickSubsystem));
    }
    // new JoystickButton(driver,7)
    // .whenPressed(new ClimberToggleRotationCommand(m_climberSubsystem,
    // m_pneumaticSubsystem));
    if (m_useIntake && m_usePneumatic && m_useMagazine) {
      new JoystickButton(m_joystickSubsystem.m_driverR, 1)
          .whileHeld(new IntakeRunMotorCommand(m_intakeSubsystem))
          .whileHeld(new IntakeUpDownCommand(m_pneumaticSubsystem))
          .whenPressed(new MagazineToggleCommand(m_magazineSubsystem, true));
    }
    if (m_useIntake && m_useMagazine){
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.B)
        .whileHeld(new MagazineAndIntakeReverseCommand(m_intakeSubsystem, m_magazineSubsystem));
    }

    if(m_useMagazine){
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.A )
      .whenPressed(new MagazineToggleCommand(m_magazineSubsystem, false));    }

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
