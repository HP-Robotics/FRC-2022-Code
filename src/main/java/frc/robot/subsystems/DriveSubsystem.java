package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

    public TalonFX m_left1;
    public TalonFX m_right1;
    public TalonFX m_left2;
    public TalonFX m_right2;

    public DriveSubsystem() {
        m_left1 = new TalonFX(1);
        m_right1 = new TalonFX(2);
        m_left2 = new TalonFX(3);
        m_right2 = new TalonFX(4);

        m_left1.configFactoryDefault();
        m_right1.configFactoryDefault();
        m_left2.configFactoryDefault();
        m_right2.configFactoryDefault();
        
        m_left1.setInverted(true);
        m_left2.setInverted(true);

        setuppid(Constants.drivekP, Constants.drivekI, Constants.drivekD, Constants.drivekF, 100);
    }

    public void periodic() {
        SmartDashboard.putNumber("LeftFront", m_left1.getSelectedSensorVelocity());
        SmartDashboard.putNumber("LeftBack", m_left2.getSelectedSensorVelocity());
        SmartDashboard.putNumber("RightFront", m_right1.getSelectedSensorVelocity());
        SmartDashboard.putNumber("RightBack", m_right2.getSelectedSensorVelocity());
    }

    public void drive(double left, double right) {
        m_left1.set(ControlMode.PercentOutput, left);
        m_right1.set(ControlMode.PercentOutput, right);
        m_left2.set(ControlMode.PercentOutput, left);
        m_right2.set(ControlMode.PercentOutput, right);

    }
    public void setuppid (double kP, double kI, double kD, double kF, int timeout){


        m_left1.config_kP(0, kP, timeout);
        m_left1.config_kI(0, kI, timeout);
        m_left1.config_kD(0, kD, timeout);
        m_left1.config_kF(0, kF, timeout);
        m_left1.configMotionAcceleration(9000);
        m_left1.configMotionCruiseVelocity(9000);
       

        m_right1.config_kP(0, kP, timeout);
        m_right1.config_kI(0, kI, timeout);
        m_right1.config_kD(0, kD, timeout);
        m_right1.config_kF(0, kF, timeout);
        m_right1.configMotionAcceleration(9000);
        m_right1.configMotionCruiseVelocity(9000);
        

        m_left2.config_kP(0, kP, timeout);
        m_left2.config_kI(0, kI, timeout);
        m_left2.config_kD(0, kD, timeout);
        m_left2.config_kF(0, kF, timeout);
        m_left2.configMotionAcceleration(9000);
        m_left2.configMotionCruiseVelocity(9000);
        

        m_right2.config_kP(0, kP, timeout);
        m_right2.config_kI(0, kI, timeout);
        m_right2.config_kD(0, kD, timeout);
        m_right2.config_kF(0, kF, timeout);
        m_right2.configMotionAcceleration(9000);
        m_right2.configMotionCruiseVelocity(9000);
    

    }
    public void enablepid(double distance) {
        m_left1.setSelectedSensorPosition(0);
        m_right1.setSelectedSensorPosition(0);
        m_left2.setSelectedSensorPosition(0);
        m_right2.setSelectedSensorPosition(0);

        m_left2.follow(m_left1);
        m_right2.follow(m_right1);
        m_right1.follow(m_left1);

        m_left1.set(ControlMode.MotionMagic,distance);

    }

    public void disablepid() {
        m_left1.set(ControlMode.PercentOutput,0);
        m_right1.set(ControlMode.PercentOutput,0);
        m_left2.set(ControlMode.PercentOutput,0);
        m_right2.set(ControlMode.PercentOutput,0);
    }
}
