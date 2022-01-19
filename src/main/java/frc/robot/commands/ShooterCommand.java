package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;




public class ShooterCommand extends CommandBase{
    private final ShooterSubsystem shootsubsytem;
    private DoubleSupplier shootSupplier;



    public ShooterCommand(ShooterSubsystem subsytem, DoubleSupplier shoot){
        shootsubsytem = subsytem;
        shootSupplier = shoot;



        addRequirements(shootsubsytem);
    }

    @Override
public void execute() {
    shootsubsytem.shoot(shootSupplier.getAsDouble());
    }
    
    @Override
    public void end (boolean interrupted) {
      shootsubsytem.shoot(0);
    }


    @Override
    public boolean isFinished() {
        return false;
    
 

}
}


