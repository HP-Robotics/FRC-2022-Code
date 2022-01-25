package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ShooterCommand;


public class ShooterSubsystem extends SubsystemBase{
    

    public TalonFX Shooter;

    public ShooterSubsystem (){
        Shooter = new TalonFX (21);

    }

    public void shoot(double power){
    Shooter.set(ControlMode.PercentOutput, power);
}
}
