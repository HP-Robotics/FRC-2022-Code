package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class PneumaticSubsystem {
    Compressor c;
    
    DoubleSolenoid climberRotation;
    DoubleSolenoid intakeExtend;
    // c.enableDigital();
    public PneumaticSubsystem(){
        climberRotation = new DoubleSolenoid(50,PneumaticsModuleType.REVPH, 0, 1);
        climberRotation.set(Value.kReverse);

        intakeExtend = new DoubleSolenoid(50,PneumaticsModuleType.REVPH, 2, 3);

        c = new Compressor(50, PneumaticsModuleType.REVPH); 
        //c.enableAnalog(0,10);
    }
 
}

