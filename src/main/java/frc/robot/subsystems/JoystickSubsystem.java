// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class JoystickSubsystem extends SubsystemBase {
    public final Joystick m_driverL = new Joystick(0);
    public final Joystick m_driverR = new Joystick(1);
    public final Joystick m_operator = new Joystick(2);

    // return (Math.pow(driver.getRawAxis(1) * -1, 3));
    /** Creates a new ExampleSubsystem. */
    public JoystickSubsystem() {

    }

    public double driverLeft() {
        return applyAxisDeadzone(m_driverL.getRawAxis(1));
    }
    public double driverRight() {
        return applyAxisDeadzone(m_driverR.getRawAxis(1));
    }

    public double driverSpin() {
        double spin = m_driverR.getRawAxis(0);
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

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
