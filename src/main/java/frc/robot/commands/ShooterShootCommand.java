package frc.robot.commands;


import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterShootCommand extends CommandBase {
    private final ShooterSubsystem shootsubsytem;
    private final DriveSubsystem m_subsystem;
    private DoubleSupplier m_leftSupplier;
    private DoubleSupplier m_rightSupplier;


    public ShooterShootCommand(ShooterSubsystem subsytem, DriveSubsystem driveSubsystem,DoubleSupplier left, DoubleSupplier right ) {
        shootsubsytem = subsytem;
        m_subsystem = driveSubsystem;
        m_leftSupplier = left;
        m_rightSupplier = right;

        addRequirements(shootsubsytem);
    }

    @Override
    public void execute() {
        if (Math.abs(shootsubsytem.m_shooter.getSelectedSensorVelocity()-Constants.shooterSpeed)<100) {
            shootsubsytem.shoot(Constants.preshooterSpeed);
        } else {
            shootsubsytem.shoot(0);
        }

        //m_subsystem.drive(m_leftSupplier.getAsDouble(), m_rightSupplier.getAsDouble());
        // m_subsystem.arcadeDrive(m_leftSupplier.getAsDouble(),
        // m_rightSupplier.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        shootsubsytem.shoot(0);
    }

    @Override
    public boolean isFinished() {
        return false;

    }
}
