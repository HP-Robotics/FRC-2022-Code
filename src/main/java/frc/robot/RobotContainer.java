// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.ClimberToggleRotationCommand;
import frc.robot.commands.DriveStraightCommand;
import frc.robot.commands.IntakeRunMotorCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.commands.DriveSetDistanceCommand;
import frc.robot.commands.ShooterShootCommand;
import frc.robot.commands.ShooterWheelCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
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
  private Boolean m_useShooter = false;
  private Boolean m_useClimber = false;
  private Boolean m_useIntake = false;
  private Boolean m_useDrive = false;
  // The robot's subsystems and commands are defined here...
  private  DriveSubsystem m_driveSubsystem;
  public ShooterSubsystem m_shooterSubsystem;
  // private final PneumaticSubsystem m_pneumaticSubsystem = new
  // PneumaticSubsystem();
  private  ClimberSubsystem m_climberSubsystem;
  private  IntakeSubsystem m_intakeSubsystem;
  public final JoystickSubsystem m_joystickSubsystem = new JoystickSubsystem();

  private DriveManualCommand m_defaultCommand;

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
      m_defaultCommand= new DriveManualCommand(m_driveSubsystem, m_joystickSubsystem);  
    m_driveSubsystem.setDefaultCommand(m_defaultCommand);
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
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.X)
          .whileHeld(new ShooterShootCommand(m_shooterSubsystem));
      new JoystickButton(m_joystickSubsystem.m_operator, Constants.Y)
          .whenPressed(new ShooterWheelCommand(m_shooterSubsystem));
    }
    /*new JoystickButton(m_joystickSubsystem.m_operator, Constants.B)
        .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, -48));
    new JoystickButton(m_joystickSubsystem.m_operator, Constants.A)
        .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, 48));
    new JoystickButton(m_joystickSubsystem.m_driverL, 1)
        .whileHeld(new DriveStraightCommand(m_driveSubsystem, m_joystickSubsystem)); */
    // new JoystickButton(driver,7)
    // .whenPressed(new ClimberToggleRotationCommand(m_climberSubsystem,
    // m_pneumaticSubsystem));
    new JoystickButton(m_joystickSubsystem.m_driverR, 1)
        .whileHeld(new IntakeRunMotorCommand(m_intakeSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_defaultCommand;
  }
}
