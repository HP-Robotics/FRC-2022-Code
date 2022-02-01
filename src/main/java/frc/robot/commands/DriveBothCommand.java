package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DriveSubsystem;

public class DriveBothCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;

    public DriveBothCommand(DriveSubsystem subsytem){
        m_subsystem =subsytem;
        addRequirements(m_subsystem);
    }

    @Override
    public void initialize(){
        m_subsystem.setBoth(true);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.setBoth(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
