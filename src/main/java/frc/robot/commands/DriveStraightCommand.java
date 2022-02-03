package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;

public class DriveStraightCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private DoubleSupplier m_leftSupplier;


    public DriveStraightCommand(DriveSubsystem subsytem, DoubleSupplier left) {
        m_subsystem = subsytem;
        m_leftSupplier = left;
        addRequirements(m_subsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_subsystem.driveStraight(m_leftSupplier.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.driveStraight(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
