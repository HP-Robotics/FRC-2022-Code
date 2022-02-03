// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.ClimberToggleRotationCommand;
import frc.robot.commands.DriveBothCommand;
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
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  // private final PneumaticSubsystem m_pneumaticSubsystem = new
  // PneumaticSubsystem();
  private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();

  final Joystick driver = new Joystick(0);

  final Joystick shootingJoystick = new Joystick(1);

  private final DriveManualCommand m_autoCommand = new DriveManualCommand(m_driveSubsystem,
      () -> {
        return (Math.pow(driver.getRawAxis(1) * -1, 3));
      },
      () -> {
        return (Math.pow(shootingJoystick.getRawAxis(1) * -1, 3));
      });

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(m_autoCommand);
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
    new JoystickButton(driver, Constants.X)
        .whenPressed(new ShooterShootCommand(m_shooterSubsystem, true))
        .whenReleased(new ShooterShootCommand(m_shooterSubsystem, false));
    new JoystickButton(driver, Constants.Y)
        .whenPressed(new ShooterWheelCommand(m_shooterSubsystem));
    new JoystickButton(driver, Constants.B)
        .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, -48));
    new JoystickButton(driver, Constants.A)
        .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, 48));
    new JoystickButton(driver, 8)
        .whileHeld(new DriveBothCommand(m_driveSubsystem));
    // new JoystickButton(driver,7)
    // .whenPressed(new ClimberToggleRotationCommand(m_climberSubsystem,
    // m_pneumaticSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
