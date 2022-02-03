// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class MagazineSubsystem extends SubsystemBase {
  public TalonFX MagazineMotor;
  public boolean toggled = false;

  /** Creates a new ExampleSubsystem. */
  public MagazineSubsystem() {
    MagazineMotor = new TalonFX(12);
  }

  @Override
  public void periodic() {
    if (toggled == false) {
      MagazineMotor.set(ControlMode.PercentOutput, 0);
    } else {
      MagazineMotor.set(ControlMode.PercentOutput, Constants.MagazineSpeed);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
