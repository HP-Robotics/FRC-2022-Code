// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.StatusFrame;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.FalconCANIntervalConfig;

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
    //FalconCANIntervalConfig.ScrambleCANInterval(m_climber, false, true);

    System.out.println("Status_1_General: " + m_climber.getStatusFramePeriod(StatusFrame.Status_1_General));
    System.out.println("Status_2_Feedback0: " + m_climber.getStatusFramePeriod(StatusFrame.Status_2_Feedback0));
    System.out.println("Status_4_AinTempVbat: " + m_climber.getStatusFramePeriod(StatusFrame.Status_4_AinTempVbat));
    System.out.println("Status_6_Misc: " + m_climber.getStatusFramePeriod(StatusFrame.Status_6_Misc));
    System.out.println("Status_7_CommStatus: " + m_climber.getStatusFramePeriod(StatusFrame.Status_7_CommStatus));
    System.out.println("Status_9_MotProfBuffer: " + m_climber.getStatusFramePeriod(StatusFrame.Status_9_MotProfBuffer));
    System.out.println("Status_10.1_MotionMagic: " + m_climber.getStatusFramePeriod(StatusFrame.Status_10_MotionMagic));
    System.out.println("Status_10.2_Targets: " + m_climber.getStatusFramePeriod(StatusFrame.Status_10_Targets));
    System.out.println("Status_12_Feedback1: " + m_climber.getStatusFramePeriod(StatusFrame.Status_12_Feedback1));
    System.out.println("Status_13_Base_PIDF0: " + m_climber.getStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0));
    System.out.println("Status_14_Turn_PIDF1: " + m_climber.getStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1));
    System.out.println("Status_15_FirmwareApiStatus: " + m_climber.getStatusFramePeriod(StatusFrame.Status_15_FirmwareApiStatus));
    System.out.println("Status_17_Targets1: " + m_climber.getStatusFramePeriod(StatusFrame.Status_17_Targets1));

 	/*Status_2_Feedback0 =(0x1440)
 
 	Status_4_AinTempVbat =(0x14C0)
 
 	Status_6_Misc =(0x1540)
 
 	Status_7_CommStatus =(0x1580)
 
 	Status_9_MotProfBuffer =(0x1600)
 
 	Status_10_MotionMagic =(0x1640)
 
 	Status_10_Targets =(0x1640)
 
 	Status_12_Feedback1 =(0x16C0)
 
 	Status_13_Base_PIDF0 =(0x1700)
 
 	Status_14_Turn_PIDF1 =(0x1740)
 
 	Status_15_FirmwareApiStatus =(0x1780)
 
 	Status_17_Targets1 =(0x1C00)*/
    ;
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
