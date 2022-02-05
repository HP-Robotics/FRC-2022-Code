 package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import javax.xml.namespace.QName;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends SubsystemBase {

    public double WheelSetPoint = 0;
    private ShuffleboardTab tab = Shuffleboard.getTab("Shooter Configuration");
    private NetworkTableEntry InputSpeed = tab.add("Input Speed", Constants.shooterSpeed)
            .getEntry();
    public TalonFX m_shooter;
    public TalonFX PreShooter;
    public double lRumble = 0;
    public double rRumble = 0;

    public ShooterSubsystem() {

        m_shooter = new TalonFX(21);
        m_shooter.configFactoryDefault();
        m_shooter.config_kP(0, Constants.shooterkP);
        m_shooter.config_kI(0, Constants.shooterkI);
        m_shooter.config_kD(0, Constants.shooterkD);
        m_shooter.config_kF(0, Constants.shooterkF);
        PreShooter = new TalonFX(22);

        SmartDashboard.putNumber("shooterkP", Constants.shooterkP);
        SmartDashboard.putNumber("shooterkI", Constants.shooterkI);
        SmartDashboard.putNumber("shooterkD", Constants.shooterkD);
        SmartDashboard.putNumber("shooterkF", Constants.shooterkF);

    }

    public void periodic() {
        if (WheelSetPoint == 0){
            m_shooter.set(ControlMode.PercentOutput,0);
        }
         else{
            m_shooter.set(ControlMode.Velocity,WheelSetPoint);
        }
        m_shooter.getSelectedSensorVelocity();
        
        if (WheelSetPoint ==  0){
            lRumble = 0;
            rRumble = 0;

        }
        else if  (Math.abs(WheelSetPoint - m_shooter.getSelectedSensorVelocity()) <100){
            rRumble = 0;
            lRumble = 0.8;
        }
        else {
            lRumble = 0; 
            rRumble = 0.8;
        }
    }

    public void shoot(double speed) {
        PreShooter.set(ControlMode.PercentOutput,speed);
    }

    public void Enable(boolean WheelOn) {
        if (WheelOn) {
            WheelSetPoint = InputSpeed.getDouble(Constants.shooterSpeed);
        } else {
            WheelSetPoint = 0;
        }

    }
}
