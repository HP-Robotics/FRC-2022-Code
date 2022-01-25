package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.ShooterSubsystem;

public class ShooterWheelCommand extends CommandBase {
    private final ShooterSubsystem shootSubsystem;
    private boolean active;

    public ShooterWheelCommand(ShooterSubsystem subsytem, boolean active) {
        shootSubsystem = subsytem;
        this.active = active;

        addRequirements(shootSubsystem);
    }

    @Override
    public void execute() {
        if (active) {
            shootSubsystem.speed = -15500;
        } else {
            shootSubsystem.speed = 0;
        }
    }

    @Override
    public void end(boolean interrupted) {
        shootSubsystem.shoot(0);
    }

    @Override
    public boolean isFinished() {
        return true;

    }
}
