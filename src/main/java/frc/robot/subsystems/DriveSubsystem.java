package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class DriveSubsystem extends SubsystemBase {
    public TalonFX Left1;
   public TalonFX Right1;
   public double speed = 0.5;
   public TalonFX Left2;
  public TalonFX Right2;
    public DriveSubsystem(){

    Left1 = new TalonFX(1);
    Right1 = new TalonFX(2);
   Left2 = new TalonFX(3);
   Right2 = new TalonFX(4);

   Left1.setInverted(true);
   Left2.setInverted(true);
   Right1.setInverted(true);
   Right2.setInverted(true);
}

    public void drive(double left, double right){
        Left1.set(ControlMode.PercentOutput, left * speed);
       Right1.set(ControlMode.PercentOutput, right * speed);
   Left2.set(ControlMode.PercentOutput, left);
    Right2.set(ControlMode.PercentOutput, right);
}
}
