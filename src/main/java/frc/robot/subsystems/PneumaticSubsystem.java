package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class PneumaticSubsystem {
    Compressor c = new Compressor(0, PneumaticsModuleType.CTREPCM); 
    DoubleSolenoid climberRotation = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);
    // c.enableDigital();
}
