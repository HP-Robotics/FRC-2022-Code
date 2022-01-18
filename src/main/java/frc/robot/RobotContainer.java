// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveCommand;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
private int X = 1;
private int A = 2;
private int B = 3;
private int Y = 4;


final Joystick driver = new Joystick(0);
  private final DriveCommand m_autoCommand = new DriveCommand(m_driveSubsystem, 
  () -> { return (Math.pow(driver.getRawAxis(1), 3)); },
  () -> { return (Math.pow(-driver.getRawAxis(3), 3)); });

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_driveSubsystem.setDefaultCommand(m_autoCommand);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driver, X)
    .whenPressed(new DriveCommand(m_driveSubsystem, () -> { return -1;}, () -> { return -1;}))
    .whenReleased(new DriveCommand(m_driveSubsystem, () -> {return 0;}, () -> {return 0;}));   
new JoystickButton(driver, Y)
    .whenPressed(new DriveCommand(m_driveSubsystem, () -> { return -1;}, () -> { return 1;}))
    .whenReleased(new DriveCommand(m_driveSubsystem, () -> {return 0;}, () -> {return 0;}));   
new JoystickButton(driver, B)
    .whenPressed(new DriveCommand(m_driveSubsystem, () -> { return 1;}, () -> { return 1;}))
    .whenReleased(new DriveCommand(m_driveSubsystem, () -> {return 0;}, () -> {return 0;}));   
new JoystickButton(driver, A)
    .whenPressed(new DriveCommand(m_driveSubsystem, () -> { return 1;}, () -> { return -1;}))
    .whenReleased(new DriveCommand(m_driveSubsystem, () -> {return 0;}, () -> {return 0;}));   

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
