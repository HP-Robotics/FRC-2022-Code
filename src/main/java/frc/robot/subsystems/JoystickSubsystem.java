// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class JoystickSubsystem extends SubsystemBase {
    public final Joystick m_driver = new Joystick(0);
    public final Joystick m_operator = new Joystick(1);

    public JoystickSubsystem() {

    }

    public double driverMove() {
        return applyAxisDeadzone(m_driver.getRawAxis(1));
    }

    public double driverSpin() {
        double spin = m_driver.getRawAxis(0);
        if (spin < 0) {
           return spin * spin * -1;
        }
        return spin * spin;
    }

    public double applyAxisDeadzone(double input) {
        if (Math.abs(input) <= Constants.deadzone) {
            return 0;
        }
        return -input * -input * -input;
    }

    public Boolean triggerPressedLeft(){
        if(m_operator.getRawAxis(2)>=0.6){
          return true;
        }
        else return false;
      }

    public Boolean triggerPressedRight(){
        if(m_operator.getRawAxis(3)>=0.6){
          return true;
        }
        else return false;
      }

      public Boolean povUp(){
        if (m_operator.getPOV() == 0) {
          return true;
        }
        else {
          return false;
        }
      }

      public Boolean povDown(){
        if (m_operator.getPOV() == 180) {
          return true;
        }
        else {
          return false;
        }
      }
      public Boolean povRight(){
        if (m_operator.getPOV() == 90) {
          return true;
        }
        else {
          return false;
        }
      }
      public Boolean povLeft(){
        if (m_operator.getPOV() == 270) {
          return true;
        }
        else {
          return false;
        }
      }


    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
