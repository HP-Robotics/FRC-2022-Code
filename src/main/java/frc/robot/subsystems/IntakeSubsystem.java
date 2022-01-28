package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

public TalonFX IntakeMotor; 
public boolean isOn = false;
public IntakeSubsystem() {
    IntakeMotor = new TalonFX(11);
}

  @Override
  public void periodic() {


    if (isOn == false) {
        IntakeMotor.set(ControlMode.PercentOutput, 0);
    }
    else {
        IntakeMotor.set(ControlMode.PercentOutput, Constants.IntakeSpeed);
    }
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

