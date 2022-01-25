// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static double shooterkD = 0.5;
    public static double shooterkF = 0.048;
    public static double shooterkI = 0.0;
    public static double shooterkP = 0.12;

    public static double drivekP = 0.5;
    public static double drivekI = 0;
    public static double drivekD = 0;
    public static double drivekF = 0;

    public static int X = 1;
    public static int A = 2;
    public static int B = 3;
    public static int Y = 4;

    public static boolean programmerMode = true;

    public static double MagazineSpeed = 1.0;
    public static double IntakeSpeed = 1.0;
}
