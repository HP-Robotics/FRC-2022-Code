package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends SubsystemBase {

    public double m_wheelSetPoint;
    private ShuffleboardTab m_tab = Shuffleboard.getTab("Shooter Configuration");
    private NetworkTableEntry m_inputSpeed = m_tab.add("Input Speed", Constants.shooterSpeed)
            .getEntry();

    public TalonFX m_shooter;
    public TalonFX m_preShooter;
    public double lRumble = 0;
    public double rRumble = 0;

    public ShooterSubsystem() {

        m_shooter = new TalonFX(21);
        m_shooter.configFactoryDefault();
        m_shooter.config_kP(0, Constants.shooterkP);
        m_shooter.config_kI(0, Constants.shooterkI);
        m_shooter.config_kD(0, Constants.shooterkD);
        m_shooter.config_kF(0, Constants.shooterkF);
        m_preShooter = new TalonFX(22);

        SmartDashboard.putNumber("shooterkP", Constants.shooterkP);
        SmartDashboard.putNumber("shooterkI", Constants.shooterkI);
        SmartDashboard.putNumber("shooterkD", Constants.shooterkD);
        SmartDashboard.putNumber("shooterkF", Constants.shooterkF);

    }

    public double getInputSpeed () {
        return m_inputSpeed.getDouble(Constants.shooterSpeed);
    }


    public void periodic() {
        System.out.println("Velocity " + m_shooter.getSelectedSensorVelocity());
        m_shooter.set(ControlMode.Velocity, m_wheelSetPoint);
        if (m_wheelSetPoint == 0) {
            m_shooter.set(ControlMode.PercentOutput, 0);
        } else {
            m_shooter.set(ControlMode.Velocity, m_wheelSetPoint);
        }
        m_shooter.getSelectedSensorVelocity();

        if (m_wheelSetPoint == 0) {
            lRumble = 0;
            rRumble = 0;

        } else if (Math.abs(m_wheelSetPoint - m_shooter.getSelectedSensorVelocity()) < 100) {
            rRumble = 0;
            lRumble = 0.8;
        } else {
            lRumble = 0;
            rRumble = 0.8;
        }
    }

    public void shoot(double speed) {
        m_preShooter.set(ControlMode.PercentOutput, speed);

    }

    public void enable(boolean wheelOn) {
        if (wheelOn) {
            m_wheelSetPoint = getInputSpeed();
        } else {
            m_wheelSetPoint = 0;
        }

    }
}
