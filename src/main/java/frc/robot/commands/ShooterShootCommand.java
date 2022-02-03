package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterShootCommand extends CommandBase {
    private final ShooterSubsystem shootsubsytem;
    private Boolean fire;

    public ShooterShootCommand(ShooterSubsystem subsytem, Boolean fire) {
        shootsubsytem = subsytem;
        this.fire = fire;

        addRequirements(shootsubsytem);
    }

    @Override
    public void execute() {
        if (fire) {
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
