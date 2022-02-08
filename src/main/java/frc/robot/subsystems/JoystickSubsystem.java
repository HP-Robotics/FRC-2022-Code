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

    public double GetAxisDeadzone(double axis) {
        return -axis*-axis*-axis;
    }

    @Override
    public void periodic() {

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
