package frc.robot.subsystems;



import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ShooterCommand;


public class ShooterSubsystem extends SubsystemBase{
    private ShuffleboardTab tab = Shuffleboard.getTab("Shoot");
    private NetworkTableEntry MaxSpeed =
    tab.add("Max Speed", 1)
       .getEntry();
    public TalonFX Shooter;


    public ShooterSubsystem (){
        Shooter = new TalonFX (21);

    }

    public void shoot(Boolean fire){
    double power = MaxSpeed.getDouble(Constants.shooterSpeed);
    Shooter.set(ControlMode.Velocity, power);

    
}
}
