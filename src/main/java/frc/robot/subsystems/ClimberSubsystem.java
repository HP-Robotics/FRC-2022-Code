// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
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
      private NetworkTableEntry m_climbMax = m_tab.add("Climber Maxiumum", Constants.climberMax)
      .withWidget(BuiltInWidgets.kTextView)
      .getEntry();
      private NetworkTableEntry m_climbMin = m_tab.add("Climber Minimum", Constants.climberMin)
      .withWidget(BuiltInWidgets.kTextView)
      .getEntry();
      private NetworkTableEntry m_resetEncoder = m_tab.add("Reset Encoder", false)
      .withWidget(BuiltInWidgets.kToggleButton)
      .getEntry();
      private NetworkTableEntry m_ignoreLimits = m_tab.add("Manual Override", false)
      .withWidget(BuiltInWidgets.kToggleButton)
      .getEntry();

  public double getSpeedUp() {
    return m_inputUp.getDouble(Constants.climbSpeedUp);
  }

  public double getClimberMax() {
    return m_climbMax.getDouble(Constants.climberMax);
  }

  public double getClimberMin() {
    return m_climbMin.getDouble(Constants.climberMin);
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
    m_climber.configFactoryDefault();
    //m_climber.configForwardSoftLimitThreshold(Constants.climberMax);
    //m_climber.configReverseSoftLimitThreshold(Constants.climberMin);
    //m_climber.configForwardSoftLimitEnable(true);
    //m_climber.configReverseSoftLimitEnable(true);
    m_climber.setSelectedSensorPosition(0);
    m_climber.setNeutralMode(NeutralMode.Brake);

    m_resetEncoder.addListener((Notification)->{
      m_climber.setSelectedSensorPosition(0);
    }, EntryListenerFlags.kUpdate);

    m_ignoreLimits.addListener((Notification)->{
   //  m_climber.configForwardSoftLimitEnable(!Notification.value.getBoolean());
   //  m_climber.configReverseSoftLimitEnable(!Notification.value.getBoolean());
    }, EntryListenerFlags.kUpdate);

    m_climbMax.addListener((Notification)->{
    //  m_climber.configForwardSoftLimitThreshold(m_climbMax.getDouble(Constants.climberMax));
    }, EntryListenerFlags.kUpdate);

    m_climbMin.addListener((Notification)->{
    //  m_climber.configReverseSoftLimitThreshold(m_climbMin.getDouble(Constants.climberMin));
    }, EntryListenerFlags.kUpdate);
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

  public void extendSpecific(double target) {
    if (m_climber.getSelectedSensorPosition(0) > target) {
      stop();
      }
      else {
        m_climber.set(ControlMode.PercentOutput, getSpeedUp());
      }
  }

  public void autoClimber() {
    m_climber.set(ControlMode.MotionMagic, Constants.climberTarget);
  }
  
  @Override
  public void periodic() {
    //System.out.println(m_climber.getSelectedSensorPosition());
    
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
