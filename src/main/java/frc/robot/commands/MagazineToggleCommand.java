package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MagazineSubsystem;

public class MagazineToggleCommand extends CommandBase {
    private final MagazineSubsystem MagazineSubsystem;
    private boolean active;

    public MagazineToggleCommand(MagazineSubsystem subsytem, boolean active) {
        MagazineSubsystem = subsytem;
        this.active = active;

        addRequirements(MagazineSubsystem);
    }

    @Override
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
