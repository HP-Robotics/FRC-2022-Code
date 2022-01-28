package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    public TalonFX m_shooter;
    public double speed;

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
        if (speed == 0) {
            this.shootRaw(0);
        } else {
            this.shoot(speed);
        }
    }

    public void shoot(double speed) {
       // m_shooter.set(ControlMode.Velocity, speed);
    }

    public void shootRaw(double power) {
        //m_shooter.set(ControlMode.PercentOutput, power);
    }
}
