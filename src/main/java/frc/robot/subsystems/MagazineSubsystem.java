// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
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
  }

  @Override
  public void periodic() {
    if (reversed) { 
      m_magazineMotor.set(ControlMode.PercentOutput, -getMagazineSpeed());
    }
    else if (toggled == false) {
      m_magazineMotor.set(ControlMode.PercentOutput, 0);
    }
    else {
      m_magazineMotor.set(ControlMode.PercentOutput, getMagazineSpeed());
      }
    
  }
  public void reverse() {
    reversed = true;
  }

  public void off() {
    reversed = false;
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
