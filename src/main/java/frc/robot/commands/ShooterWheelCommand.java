package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterWheelCommand extends CommandBase {
    private final ShooterSubsystem shootSubsystem;

    public ShooterWheelCommand(ShooterSubsystem subsytem) {
        shootSubsystem = subsytem;
        addRequirements(shootSubsystem);
    }

    @Override
    public void initialize() {
        if (shootSubsystem.WheelSetPoint == 0) {
            shootSubsystem.Enable(true);
        } else {
            shootSubsystem.Enable(false);
        }
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
