package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class DriveSubsystem extends SubsystemBase {
    public TalonFX Left1;
   public TalonFX Right1;
   // public TalonFX Left2;
  // public TalonFX Right2;
    public DriveSubsystem(){

    Left1 = new TalonFX(12);
    Right1 = new TalonFX(11);
   // Left2 = new TalonFX(2);
   // Right2 = new TalonFX(4);
}

    public void drive(double left, double right){
        Left1.set(ControlMode.PercentOutput, left);
       Right1.set(ControlMode.PercentOutput, right);
   // Left2.set(ControlMode.PercentOutput, left);
    //   Right2.set(ControlMode.PercentOutput, right);
}
}
