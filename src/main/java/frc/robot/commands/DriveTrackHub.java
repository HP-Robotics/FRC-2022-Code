package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DriveTrackHub extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final JoystickSubsystem m_joystickSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;

    public DriveTrackHub(DriveSubsystem subsytem, JoystickSubsystem joysticks, ShooterSubsystem shooter) {
        m_subsystem = subsytem;
        m_joystickSubsystem = joysticks;
        m_shooterSubsystem = shooter;
        addRequirements(m_subsystem);
        addRequirements(m_joystickSubsystem);
    }

    @Override
    public void execute() {
        if (m_shooterSubsystem.trackingHub()) {
            m_subsystem.arcadeDrive(m_joystickSubsystem.driverMove(),
                    m_shooterSubsystem.getNormalizedHubX());
        } else {
            m_subsystem.arcadeDrive(m_joystickSubsystem.driverMove(),
                    m_joystickSubsystem.driverSpin());
        }
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
