// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MagazineSubsystem extends SubsystemBase {
  public TalonSRX m_magazineMotor;
  public boolean toggled = false;
  public boolean reversed = false;
  public Boolean originalState;

  private ShuffleboardTab m_tab = Shuffleboard.getTab("Intake Configuration");
  private NetworkTableEntry m_magazineSpeed = m_tab.add("Magazine Speed", Constants.MagazineSpeed)
   .getEntry();

  /** Creates a new ExampleSubsystem. */
  public MagazineSubsystem() {
    m_magazineMotor = new TalonSRX(12);
    m_magazineMotor.configFactoryDefault();
    m_magazineSpeed.setNumber(Constants.MagazineSpeed);
    m_magazineMotor.setInverted(true);
    m_magazineMotor.configPeakCurrentLimit(30);
    m_magazineMotor.configPeakCurrentDuration(100);
    m_magazineMotor.configContinuousCurrentLimit(5);
    m_magazineMotor.enableCurrentLimit(true);
  }

  @Override
  public void periodic() {

  }
  public void on () {
    toggled = true;
    if(!reversed){
      m_magazineMotor.set(ControlMode.PercentOutput, getMagazineSpeed());
    }
  }
  public void off () {
    toggled = false;
    if(!reversed) {
      m_magazineMotor.set(ControlMode.PercentOutput, 0);
    }
  }
  public void reverse() {
    reversed = true;
    m_magazineMotor.set(ControlMode.PercentOutput, -getMagazineSpeed());
  }
  public void unreverse() {
    reversed = false;
    if(toggled) {
      on();
    }
    else {
      off();
    }
  }

  public double getMagazineSpeed () {
    return m_magazineSpeed.getDouble(Constants.MagazineSpeed);
    //return Constants.MagazineSpeed;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
