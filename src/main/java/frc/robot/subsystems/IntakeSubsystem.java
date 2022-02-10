package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

  public TalonFX m_intakeMotor;
  public boolean m_isUp = true;

  public IntakeSubsystem() {
    m_intakeMotor = new TalonFX(11);
  }

  public void on() {
    m_intakeMotor.set(ControlMode.PercentOutput, Constants.IntakeSpeed);
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
