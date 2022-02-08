package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.simulation.JoystickSim;
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
        m_subsystem.drive(m_joystickSubsystem.m_driverL.getRawAxis(1), m_joystickSubsystem.m_driverR.getRawAxis(1));
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
