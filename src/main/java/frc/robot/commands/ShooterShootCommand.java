package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.subsystems.ShooterSubsystem;

public class ShooterShootCommand extends CommandBase {
    private final ShooterSubsystem m_shootsubsystem;

    public ShooterShootCommand(ShooterSubsystem subsytem) {
        m_shootsubsystem = subsytem;

        addRequirements(m_shootsubsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Shot Fired " + m_shootsubsystem.m_shooter.getSelectedSensorVelocity()
                + "  Error "
                + (m_shootsubsystem.m_wheelSetPoint - m_shootsubsystem.m_shooter.getSelectedSensorVelocity())
                + "  Mode " + m_shootsubsystem.m_wheelTarget);

    }

    @Override
    public void execute() {
        if (m_shootsubsystem.upToSpeed()) {
            m_shootsubsystem.shoot(Constants.preshooterSpeed);
        } else {
            m_shootsubsystem.shoot(0);
        }

    }

    @Override
    public void end(boolean interrupted) {
        m_shootsubsystem.shoot(0);
    }

    @Override
    public boolean isFinished() {
        return false;

    }
}
