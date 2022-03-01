package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.JoystickSubsystem;

public class DriveStraightCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final JoystickSubsystem m_joystickSubsystem;
    


    public DriveStraightCommand(DriveSubsystem subsytem, JoystickSubsystem joysticks) {
        m_subsystem = subsytem;
        m_joystickSubsystem = joysticks;
        addRequirements(m_subsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_subsystem.driveStraight(m_joystickSubsystem.m_driver.getRawAxis(1) * -1);
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
