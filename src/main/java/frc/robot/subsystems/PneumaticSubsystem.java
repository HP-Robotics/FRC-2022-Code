package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticSubsystem extends SubsystemBase {
    Compressor c;
    Boolean climberRotated = false;
    public boolean m_isUp = true;
    DoubleSolenoid climberRotation;
    DoubleSolenoid intakeExtend;

    public PneumaticSubsystem() {
        climberRotation = new DoubleSolenoid(50, PneumaticsModuleType.REVPH, 2, 3);
        climberRotation.set(Value.kReverse);

        intakeExtend = new DoubleSolenoid(50, PneumaticsModuleType.REVPH, 0, 1);
        intakeExtend.set(Value.kReverse);

        c = new Compressor(50, PneumaticsModuleType.REVPH);
        c.enableAnalog(50, 60);
        SetClimb(climberRotated);
    }

    public void SetClimb(Boolean state) {
        if (state) {
            climberRotation.set(Value.kForward);
        } else {
            climberRotation.set(Value.kReverse);
        }

    }

    public void climberForward (){
        SetClimb(true);
        System.out.println(true);
    }

    public void climberBack (){
        SetClimb(false);
        System.out.println(false);
    }

    public void ToggleClimb() {
        climberRotated = !climberRotated;
        SetClimb(climberRotated);
        System.out.println(climberRotated);
    }


    public void SetIntake(Boolean state) {
        if (state) {
            intakeExtend.set(Value.kForward);
        } else {
            intakeExtend.set(Value.kReverse);
        }

    }
}
