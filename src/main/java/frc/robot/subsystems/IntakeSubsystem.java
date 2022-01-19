package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{
  TalonFX intakeMotor;
  boolean state = false;
  public IntakeSubsystem(){
    intakeMotor = new TalonFX(68);
  }

  public void toggle(){
    state = !state;
  }

  /*public void IntakeOn(double speed) {
    intakeMotor.set(ControlMode.PercentOutput, speed);
  }

  public void IntakeReverse(double speed) {
    intakeMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void IntakeOff() {
    intakeMotor.set(ControlMode.PercentOutput, 0);
  }*/

  @Override
  public void periodic() {
    if(state == true){
      intakeMotor.set(ControlMode.PercentOutput, 0.5);
    } 
    else{
      intakeMotor.set(ControlMode.PercentOutput, 0);
    }
  }

}

