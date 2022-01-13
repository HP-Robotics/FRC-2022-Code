package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class DriveSubsystem extends SubsystemBase {
    public TalonSRX Left1;
    public TalonSRX Right1;
    public DriveSubsystem(){

    Left1 = new TalonSRX(11);
    Right1 = new TalonSRX(12);
}

    public void drive(double left1, double right1){
        Left1.set(ControlMode.PercentOutput, left1);
        Right1.set(ControlMode.PercentOutput, right1);
}
}
