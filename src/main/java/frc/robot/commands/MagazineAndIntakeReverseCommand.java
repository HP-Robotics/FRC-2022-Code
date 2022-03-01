// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MagazineSubsystem;

/** An example command that uses an example subsystem. */

public class MagazineAndIntakeReverseCommand extends CommandBase {
  private final IntakeSubsystem m_intake;
  private final MagazineSubsystem m_magazine;

  /**
   * Creates a new ExampleCommand.
   * @param subsystem The subsystem used by this command.
   */
  public MagazineAndIntakeReverseCommand(IntakeSubsystem subsystem, MagazineSubsystem Magazine) {
    m_intake = subsystem;
    m_magazine = Magazine;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    addRequirements(m_magazine);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.reverse();
    m_magazine.reverse();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.off();
    m_magazine.unreverse();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
