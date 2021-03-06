package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.FalconCANIntervalConfig;

public class IntakeSubsystem extends SubsystemBase {
  private ShuffleboardTab m_tab = Shuffleboard.getTab("Intake Configuration");
  private NetworkTableEntry m_intakeSpeed = m_tab.add("Intake Speed", Constants.IntakeSpeed)
  .getEntry();
  public TalonFX m_intakeMotor;
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("limelight-intake");
  NetworkTableEntry cargoX = table.getEntry("tx");
  NetworkTableEntry team = inst.getTable("FMSInfo").getEntry("IsRedAlliance");
  public int m_cargoSightCounter=0; 
  public IntakeSubsystem() {
    m_intakeMotor = new TalonFX(11);
    FalconCANIntervalConfig.ScrambleCANInterval(m_intakeMotor, false, false);
  }

  public void on() {
    m_intakeMotor.set(ControlMode.PercentOutput, m_intakeSpeed.getDouble(Constants.IntakeSpeed));
  }
  public void reverse() {
    m_intakeMotor.set(ControlMode.PercentOutput, -m_intakeSpeed.getDouble(Constants.IntakeSpeed));
  }

  public void off() {
    m_intakeMotor.set(ControlMode.PercentOutput, 0);
  }

  public double getNormalizedCargoX() {
    return ((cargoX.getDouble(0))/56.0);
  }

  public boolean trackingCargo() {
    if (table.getEntry("tv").getNumber(0).intValue() >= 0.5){
     return true;
    } 
    return false;
    
  }

  @Override
  public void periodic() {
    int teamval = team.getBoolean(true) ? 0 : 1;
    table.getEntry("pipeline").setNumber(teamval);

    if(trackingCargo()){
      m_cargoSightCounter=0;
    }
    else {
      m_cargoSightCounter=m_cargoSightCounter-1;
    }
    // This method will be called once per scheduler run
  }

  public Boolean cargoSeen() {
    return (m_cargoSightCounter>-5);
  }

  public void enableLimelight() {
    table.getEntry("camMode").setNumber(0);
  }

  public void disableLimelight() {
    table.getEntry("camMode").setNumber(1);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
