package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;

public class DriveManualCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private DoubleSupplier m_leftSupplier;
    private DoubleSupplier m_rightSupplier;

    public DriveManualCommand(DriveSubsystem subsytem, DoubleSupplier left, DoubleSupplier right) {
        m_subsystem = subsytem;
        m_leftSupplier = left;
        m_rightSupplier = right;
        addRequirements(m_subsystem);
    }

    @Override
    public void execute() {
        m_subsystem.drive(m_leftSupplier.getAsDouble(), m_rightSupplier.getAsDouble());
        // m_subsystem.arcadeDrive(m_leftSupplier.getAsDouble(),
        // m_rightSupplier.getAsDouble());
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
