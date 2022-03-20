package frc.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.FalconCANIntervalConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends SubsystemBase {
    private String m_wheelTarget = "High";
    public double m_wheelSetPoint;
    public ShuffleboardTab m_driverTab = Shuffleboard.getTab("Driver View");
    private ShuffleboardTab m_tab = Shuffleboard.getTab("Shooter Configuration");
    private NetworkTableEntry m_inputHighSpeed = m_tab.add("Input Speed High" , Constants.shooterHighSpeed)
            .getEntry();
    private NetworkTableEntry m_inputLowSpeed = m_tab.add("Input Speed Low", Constants.shooterLowSpeed)
            .getEntry();
    private NetworkTableEntry m_speedThreshold = m_tab.add("Speed Threshold", Constants.shooterSpeedThreshold)
            .getEntry();
    private BooleanSupplier m_atSpeed = ()->upToSpeed();;
        private Supplier<String> m_getWheelTarget = ()->{ return  m_wheelTarget;};

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight-shooter");
    NetworkTableEntry hubX = table.getEntry("tx");
   

    public TalonFX m_shooter;
    public TalonFX m_preShooter;
    public double lRumble = 0;
    public double rRumble = 0;

    public ShooterSubsystem() {

        m_shooter = new TalonFX(21);
        m_shooter.configFactoryDefault();
        //FalconCANIntervalConfig.ScrambleCANInterval(m_shooter, true, true);
        m_shooter.config_kP(0, Constants.shooterkP);
        m_shooter.config_kI(0, Constants.shooterkI);
        m_shooter.config_kD(0, Constants.shooterkD);
        m_shooter.config_kF(0, Constants.shooterkF);
        m_shooter.config_IntegralZone(0, Constants.shooterkIzone, 100);
        m_preShooter = new TalonFX(22);
        m_preShooter.configFactoryDefault();
        //FalconCANIntervalConfig.ScrambleCANInterval(m_preShooter, false, true);
        m_preShooter.setNeutralMode(NeutralMode.Brake);

        SmartDashboard.putNumber("shooterkP", Constants.shooterkP);
        SmartDashboard.putNumber("shooterkI", Constants.shooterkI);
        SmartDashboard.putNumber("shooterkD", Constants.shooterkD);
        SmartDashboard.putNumber("shooterkF", Constants.shooterkF);
        m_driverTab.addBoolean("Shooter at Speed", m_atSpeed);
        m_driverTab.addString("Shooter Wheel Target", m_getWheelTarget);
    }

    public double getInputSpeed () {
        if(m_wheelTarget == "High"){
            return m_inputHighSpeed.getDouble(Constants.shooterHighSpeed);
        }
        else if (m_wheelTarget == "Low"){
            return m_inputLowSpeed.getDouble(Constants.shooterLowSpeed);
        }
        return 0;
    }
    public double getSpeedThreshold () {
        return m_speedThreshold.getDouble(Constants.shooterSpeedThreshold);
    }

    public Boolean upToSpeed () {
        return Math.abs(m_shooter.getSelectedSensorVelocity()-getInputSpeed())<getSpeedThreshold();
    }

    public void setSpeed(double setPoint) {
        m_wheelSetPoint = setPoint;
        if (setPoint == 0) {
            m_shooter.set(ControlMode.PercentOutput, 0);
        } else {
            m_shooter.set(ControlMode.Velocity, setPoint);
        }
    }

    public void periodic() {
        

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

    public void lowSpeed() {
        m_wheelTarget = "Low";
        this.enable(true);
    }   

    public void highSpeed() {
        m_wheelTarget = "High";
        this.enable(true);
    }   

    public double getNormalizedHubX() {
        return ((hubX.getDouble(0))/56);
    }
    
    public boolean trackingHub() {
        if (table.getEntry("tv").getNumber(0).intValue() >= 0.5){
         return true;
        } 
        return false;
        
      }

    public void enable(boolean wheelOn) {
        if (wheelOn) {
            setSpeed(getInputSpeed());
        } else {
            setSpeed(0);
        }

    }
}
