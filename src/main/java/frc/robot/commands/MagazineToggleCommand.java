package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MagazineSubsystem;

public class MagazineToggleCommand extends CommandBase {
    private final MagazineSubsystem m_subsystem;

    public MagazineToggleCommand(MagazineSubsystem subsystem) {
        m_subsystem = subsystem;

        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        if (m_subsystem.toggled == true) {
            m_subsystem.toggled = false;
        } else {
            m_subsystem.toggled = true;
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
