package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.JoystickSubsystem;

public class DriveManualCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final JoystickSubsystem m_joystickSubsystem;

    public DriveManualCommand(DriveSubsystem subsytem, JoystickSubsystem joysticks) {
        m_subsystem = subsytem;
        m_joystickSubsystem = joysticks;
        addRequirements(m_subsystem);
        addRequirements(m_joystickSubsystem);
    }

    @Override
    public void execute() {
        m_subsystem.arcadeDrive(m_joystickSubsystem.driverMove(), 
        m_joystickSubsystem.driverSpin());
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
