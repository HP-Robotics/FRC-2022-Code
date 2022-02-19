// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MagazineSubsystem extends SubsystemBase {
  public TalonFX m_magazineMotor;
  public boolean toggled = false;
  public boolean reversed = false;
  public Boolean originalState;

  /** Creates a new ExampleSubsystem. */
  public MagazineSubsystem() {
    m_magazineMotor = new TalonFX(12);
  }

  @Override
  public void periodic() {
    if (reversed) { 
      m_magazineMotor.set(ControlMode.PercentOutput, -Constants.MagazineSpeed);
    }
    else if (toggled == false) {
      m_magazineMotor.set(ControlMode.PercentOutput, 0);
    }
    else {
      m_magazineMotor.set(ControlMode.PercentOutput, Constants.MagazineSpeed);
      }
    
  }
  public void reverse() {
    reversed = true;
  }

  public void off() {
    reversed = false;
  }


  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
