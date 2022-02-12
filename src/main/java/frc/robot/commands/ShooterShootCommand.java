package frc.robot.commands;


import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterShootCommand extends CommandBase {
    private final ShooterSubsystem m_shootsubsystem;


    public ShooterShootCommand(ShooterSubsystem subsytem) {
        m_shootsubsystem = subsytem;
        

        addRequirements(m_shootsubsystem);
    }

    @Override
    public void execute() {
        
        //Probably should put this into shooter subsystem
        if (Math.abs(m_shootsubsystem.m_shooter.getSelectedSensorVelocity()-m_shootsubsystem.getInputSpeed())<Constants.shooterSpeedThreshold) {
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
