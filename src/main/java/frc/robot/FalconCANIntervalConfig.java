package frc.robot;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class FalconCANIntervalConfig {
   public static int primes[]={101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293};
   public static int index = 0;
   
    public static void ScrambleCANInterval(TalonFX Talon, boolean usingPID, boolean usingEncoder) {
        if (!usingEncoder) {
            Talon.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, primes[index]);
            incrementIndex();
        }
        if (!usingPID) {
            Talon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, primes[index]);
            incrementIndex();
        }
        Talon.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, primes[index]);
        incrementIndex();
    }

    private static void incrementIndex() {
        index += 1;
        if (index >= 37) {
            index = 0;
        }
    }
}
