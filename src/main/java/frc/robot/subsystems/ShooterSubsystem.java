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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ShooterSubsystem extends SubsystemBase {

    public TalonFX m_shooter;
    public double speed;
    private ShuffleboardTab tab = Shuffleboard.getTab("Shoot");
    private NetworkTableEntry MaxSpeed =
    tab.add("Max Speed", 1)
       .getEntry();
    public TalonFX Shooter;



    public ShooterSubsystem() {
        /*m_shooter = new TalonFX(21);
        m_shooter.configFactoryDefault();
        m_shooter.config_kP(0, Constants.shooterkP);
        m_shooter.config_kI(0, Constants.shooterkI);
        m_shooter.config_kD(0, Constants.shooterkD);
        m_shooter.config_kF(0, Constants.shooterkF);

        SmartDashboard.putNumber("shooterkP", Constants.shooterkP);
        SmartDashboard.putNumber("shooterkI", Constants.shooterkI);
        SmartDashboard.putNumber("shooterkD", Constants.shooterkD);
        SmartDashboard.putNumber("shooterkF", Constants.shooterkF);*/
    }

    public void periodic() {
 
    }

    

    public void shoot(Boolean fire){
    double power = MaxSpeed.getDouble(Constants.shooterSpeed);
    Shooter.set(ControlMode.Velocity, power);

    
}
}
