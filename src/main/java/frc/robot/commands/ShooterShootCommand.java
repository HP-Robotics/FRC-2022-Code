package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;




public class ShooterShootCommand extends CommandBase{
    private final ShooterSubsystem shootsubsytem;
    private DoubleSupplier shootSupplier;



    public ShooterShootCommand(ShooterSubsystem subsytem, DoubleSupplier shoot){
        shootsubsytem = subsytem;
        shootSupplier = shoot;



        addRequirements(shootsubsytem);
    }

    @Override
public void execute() {
    shootsubsytem.shoot(true);
    }
    
    @Override
    public void end (boolean interrupted) {
      shootsubsytem.shoot(false);
    }


    @Override
    public boolean isFinished() {
        return false;
    
 

}
}


