package frc.robot.commands;


import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterShootCommand extends CommandBase {
    private final ShooterSubsystem shootsubsytem;


    public ShooterShootCommand(ShooterSubsystem subsytem) {
        shootsubsytem = subsytem;
        

        addRequirements(shootsubsytem);
    }

    @Override
    public void execute() {
        if (Math.abs(shootsubsytem.m_shooter.getSelectedSensorVelocity()-Constants.shooterSpeed)<100) {
            shootsubsytem.shoot(Constants.preshooterSpeed);
        } else {
            shootsubsytem.shoot(0);
        }


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
