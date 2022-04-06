package frc.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.FalconCANIntervalConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends SubsystemBase {
    private Spark m_blinken;
    private String m_wheelTarget = "High"; //changed in robot.java auto and teleop init
    public double m_wheelSetPoint;
    public double m_wheelSmartSetPoint;
    public ShuffleboardTab m_driverTab = Shuffleboard.getTab("SmartDashboard");
    private ShuffleboardTab m_tab = Shuffleboard.getTab("Shooter Configuration");
    private NetworkTableEntry m_inputHighSpeed = m_tab.add("Input Speed High", Constants.shooterHighSpeed)
            .getEntry();
    private NetworkTableEntry m_inputSafeSpeed = m_tab.add("Input Speed Safe Zone", Constants.shooterSafeSpeed)
            .getEntry();
    private NetworkTableEntry m_inputLowSpeed = m_tab.add("Input Speed Low", Constants.shooterLowSpeed)
            .getEntry();
    private NetworkTableEntry m_speedThreshold = m_tab.add("Speed Threshold", Constants.shooterSpeedThreshold)
            .getEntry();
    private NetworkTableEntry m_smartSpeed = m_tab.add("Smart Speed", Constants.shooterHighSpeed)
            .getEntry();
            private NetworkTableEntry m_fudge = m_tab.add("Hub Distance fudge", Constants.shooterDistanceFudge)
            .getEntry();
            private NetworkTableEntry m_distance = m_tab.add("limelight distance", -1)
            .getEntry();
    private BooleanSupplier m_atSpeed = () -> upToSpeed();;
    private Supplier<String> m_getWheelTarget = () -> {
        return m_wheelTarget;
    };

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("limelight-shooter");
    NetworkTableEntry hubX = table.getEntry("tx");
    NetworkTableEntry hubD = table.getEntry("llpython");

    public TalonFX m_shooter;
    public TalonFX m_preShooter;
    public TalonFX m_shooterFollower;
    public double lRumble = 0;
    public double rRumble = 0;
    public double[] visionDefault = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public boolean m_usePython = false;
    public int m_hubCounter = 0;
    

    public ShooterSubsystem() {

        m_shooter = new TalonFX(21);
        m_shooter.configFactoryDefault();
        // FalconCANIntervalConfig.ScrambleCANInterval(m_shooter, true, true);
        m_shooter.config_kP(0, Constants.shooterkP);
        m_shooter.config_kI(0, Constants.shooterkI);
        m_shooter.config_kD(0, Constants.shooterkD);
        m_shooter.config_kF(0, Constants.shooterkF);
        m_shooter.config_IntegralZone(0, Constants.shooterkIzone, 100);
        m_preShooter = new TalonFX(22);
        m_preShooter.configFactoryDefault();
        // FalconCANIntervalConfig.ScrambleCANInterval(m_preShooter, false, true);
        m_preShooter.setNeutralMode(NeutralMode.Brake);
        m_shooterFollower = new TalonFX(23);
        m_shooterFollower.configFactoryDefault();
        m_shooterFollower.follow(m_shooter);
        m_shooterFollower.setInverted(true);

        m_blinken = new Spark(0);

        m_driverTab.addBoolean("Shooter at Speed", m_atSpeed);
        m_driverTab.addString("Shooter Wheel Target", m_getWheelTarget);
    }

    public double getInputSpeed() {
        if (m_wheelTarget == "High" || m_wheelTarget == "Smart") {
            return m_inputHighSpeed.getDouble(Constants.shooterHighSpeed);
        } else if (m_wheelTarget == "Low") {
            return m_inputLowSpeed.getDouble(Constants.shooterLowSpeed);
        } else if (m_wheelTarget == "Safe Zone") {
            return m_inputSafeSpeed.getDouble(Constants.shooterSafeSpeed);
        }
        return 0;
    }

    public double distanceToSpeed(double distance){
        double s = 28.2787363*distance+6205.814292;
        if(s<5000){
            s=5000;
        }
        if(s>12000){
            s=12000;
        }
        return s;
    }

    public double getSpeedThreshold() {
        return m_speedThreshold.getDouble(Constants.shooterSpeedThreshold);
    }

    public Boolean upToSpeed() {
        return Math.abs(m_shooter.getSelectedSensorVelocity() - m_wheelSetPoint) < getSpeedThreshold();
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

        m_blinken.set(Constants.blinkenPattern);
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

        //m_distance.setDouble(getHubDistance());


        if(m_wheelTarget == "Smart"){
            if(trackingHub()&& m_usePython){
                m_hubCounter=0;
                m_wheelSmartSetPoint = distanceToSpeed(getHubDistance());
                
              }
              else {
                m_hubCounter -= 1;
              }
              if(m_hubCounter<-10){
                m_wheelSmartSetPoint = m_inputHighSpeed.getDouble(Constants.shooterHighSpeed);
             }
             if(m_wheelSetPoint!=0 && m_wheelSetPoint != m_wheelSmartSetPoint){
                setSpeed(m_wheelSmartSetPoint); 
             }
             //m_smartSpeed.setDouble(m_wheelSmartSetPoint);
        }
        

        
    }

    public void shoot(double speed) {
        m_preShooter.set(ControlMode.PercentOutput, speed);

    }
//TODO should speed change turn on wheel
    public void safeSpeed() {
        m_wheelTarget = "Safe Zone";
    ///    this.enable(true);
    }

    public void lowSpeed() {
        m_wheelTarget = "Low";
      //  this.enable(true);
    }

    public void highSpeed() {
        m_wheelTarget = "High";
      //  this.enable(true);
    }

    public void smartSpeed(){
        if(!m_usePython){
            return;
        }
        m_wheelTarget = "Smart";
       // this.enable(true);
    }


    public double getNormalizedHubX() {
        if (m_usePython) {
            double[] visionResult = hubD.getDoubleArray(visionDefault);
            double pythonHubX = visionResult[1];
            return (((pythonHubX - 480.0 - Constants.shooterAngleFudge) / 960.0)*56);
        }
        return ((hubX.getDouble(0)));
    }

    public boolean trackingHub() {
        if (m_usePython) {
            double[] visionResult = hubD.getDoubleArray(visionDefault);
            return visionResult[0] >= 0.99;
        }
        return (table.getEntry("tv").getNumber(0).intValue() >= 0.5);
    }

    public double getHubDistance() {
        double[] visionResult = hubD.getDoubleArray(visionDefault);
        return visionResult[8] + m_fudge.getDouble(Constants.shooterDistanceFudge);
    }

    public void enable(boolean wheelOn) {
        if (wheelOn) {
            setSpeed(getInputSpeed());
        } else {
            setSpeed(0);
        }

    }

    public void enableLimelight() {
        table.getEntry("ledMode").setNumber(0);
        table.getEntry("camMode").setNumber(0);
    }

    public void disableLimelight() {
        table.getEntry("ledMode").setNumber(1);
        table.getEntry("camMode").setNumber(1);
    }

}
