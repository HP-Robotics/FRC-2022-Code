package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
