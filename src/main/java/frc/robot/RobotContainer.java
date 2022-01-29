// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
<<<<<<< HEAD
<<<<<<< HEAD
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
=======
=======
>>>>>>> c01ce1fefe61c6348615ca22dab932731fa510ad
import frc.robot.commands.DriveManualCommand;
import frc.robot.commands.DriveSetDistanceCommand;
import frc.robot.commands.ShooterShootCommand;
import frc.robot.commands.ShooterWheelCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PneumaticSubsystem;
import edu.wpi.first.wpilibj.Joystick;
<<<<<<< HEAD
>>>>>>> cb9fa7c8168273d69c94052e90129e0eb30b1e56
=======
>>>>>>> c01ce1fefe61c6348615ca22dab932731fa510ad
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ShooterSubsystem;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final PneumaticSubsystem m_pneumaticSubsystem = new PneumaticSubsystem();


final Joystick driver = new Joystick(0);
  private final DriveManualCommand m_autoCommand = new DriveManualCommand(m_driveSubsystem, 
  () -> { return (Math.pow(driver.getRawAxis(1), 3)); },
  () -> { return (Math.pow(driver.getRawAxis(3), 3)); });

final Joystick shootingJoystick = new Joystick(1);
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
<<<<<<< HEAD
<<<<<<< HEAD
    
=======
=======

>>>>>>> c01ce1fefe61c6348615ca22dab932731fa510ad
    new JoystickButton(driver, Constants.X)
    .whenPressed(new ShooterShootCommand(m_shooterSubsystem, true))
    .whenReleased(new ShooterShootCommand(m_shooterSubsystem, false));  
new JoystickButton(driver, Constants.Y)
    .whenPressed(new ShooterWheelCommand(m_shooterSubsystem));
new JoystickButton(driver, Constants.B)
    .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, -48));   
new JoystickButton(driver, Constants.A)
    .whenPressed(new DriveSetDistanceCommand(m_driveSubsystem, 48)); 

<<<<<<< HEAD
>>>>>>> cb9fa7c8168273d69c94052e90129e0eb30b1e56
=======
>>>>>>> c01ce1fefe61c6348615ca22dab932731fa510ad
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
