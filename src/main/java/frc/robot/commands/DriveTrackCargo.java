package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.JoystickSubsystem;

public class DriveTrackCargo extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final JoystickSubsystem m_joystickSubsystem;
    private final IntakeSubsystem m_intakeSubsystem;

    public DriveTrackCargo(DriveSubsystem subsytem, JoystickSubsystem joysticks, IntakeSubsystem intake) {
        m_subsystem = subsytem;
        m_joystickSubsystem = joysticks;
        m_intakeSubsystem = intake;
        addRequirements(m_subsystem);
        addRequirements(m_joystickSubsystem);
    }

    @Override
    public void execute() {
        m_subsystem.arcadeDrive(m_joystickSubsystem.driverMove(), 
        m_intakeSubsystem.getNormalizedCargoX());
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
