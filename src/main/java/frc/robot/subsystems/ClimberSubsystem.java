// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  public TalonFX m_climber;
  private ShuffleboardTab m_tab = Shuffleboard.getTab("Climber");
  private NetworkTableEntry m_inputUp = m_tab.add("Climber Speed Up", Constants.climbSpeedUp)
      .getEntry();
  private NetworkTableEntry m_inputDown = m_tab.add("Climber Speed Down", Constants.climbSpeedDown)
      .getEntry();
  private NetworkTableEntry m_inputStop = m_tab.add("Climber Stop", Constants.climbStop)
      .getEntry();

  public double getSpeedUp() {
    return m_inputUp.getDouble(Constants.climbSpeedUp);
  }

  public double getSpeedDown() {
    return m_inputDown.getDouble(Constants.climbSpeedDown);
  }

  public double getClimbStop() {
    return m_inputStop.getDouble(Constants.climbStop);
  }

  /** Creates a new ExampleSubsystem. */
  public ClimberSubsystem() {
    m_climber = new TalonFX(31);

  }

  public void extend() {
    m_climber.set(ControlMode.PercentOutput, getSpeedUp());
  }

  public void stop() {
    m_climber.set(ControlMode.PercentOutput, getClimbStop());
  }

  public void retract() {
    m_climber.set(ControlMode.PercentOutput, getSpeedDown());
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
