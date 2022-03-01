package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MagazineSubsystem;

public class MagazineToggleCommand extends CommandBase {
    private final MagazineSubsystem m_subsystem;
    private boolean m_leaveOn;
    public MagazineToggleCommand(MagazineSubsystem subsystem, boolean leaveOn) {
        m_subsystem = subsystem;
        m_leaveOn = leaveOn;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        if (m_subsystem.toggled == true && !m_leaveOn) {
            m_subsystem.off();
        } else {
            m_subsystem.on();
        }
    }

    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;

    }
}
