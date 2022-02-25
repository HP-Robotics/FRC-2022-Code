// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  public TalonFX m_climber;
  /** Creates a new ExampleSubsystem. */
  public ClimberSubsystem() {
    m_climber = new TalonFX(31);
    m_climber.configFactoryDefault();
    m_climber.setNeutralMode(NeutralMode.Brake);
  }

  public void extend () {
    m_climber.set(ControlMode.PercentOutput,Constants.climbSpeed);
  }
  public void stop () {
    m_climber.set(ControlMode.PercentOutput,0.0);
  }

  public void retract () {
    m_climber.set(ControlMode.PercentOutput,-Constants.climbSpeed);
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
