// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static double maxVelocity = 11000;
    public static double rightHandSlowdown = 1.0;
    public static double leftHandSlowdown = 1.0;
    public static double preshooterSpeed = 0.5;
    public static double shooterHighSpeed = 14100 * (2.0 / 3.0); // 9400
    public static double shooterLowSpeed = 8000 * (2.0 / 3.0); // 5333
    public static double shooterSafeSpeed = 10750;
    public static double shooterSpeedThreshold = 200;
    public static double shooterDistanceFudge = 0;
    public static double shooterAngleFudge = -20; // NOT IN SMARTDASHBOARD
    public static double shooterkD = 7.5;
    public static double shooterkF = 0.055;
    public static double shooterkI = 0.0005;
    public static double shooterkP = 0.145;
    public static double shooterkIzone = 500;
    
    // Spin Constants: kP = 0.25, kI = 0.002, kF = 0.06, kD = 2
    public static double drivekP = 0.25;
    public static double drivekI = 0.001;
    public static double drivekD = 0.02;
    public static double drivekF = 0.09;
    public static double degreesToInches = 1 / (-4.7);

    public static int X = 3;
    public static int A = 1;
    public static int B = 2;
    public static int Y = 4;
    public static int driveStraight = 2;

    public static double climbSpeedUp = 1;
    public static double climbSpeedDown = -1;
    public static double climbSpeedUpSlow = 0.3;
    public static double climbSpeedDownSlow = -0.5;
    public static double climbStop = 0;
    public static double climberMin = -24000;
    public static double climberMax = 215000;
    public static double climberFastPoint = -23500;
    public static double climberSlowPoint = 179000;
    public static double climberHigherPoint = 90000;

    // starting position -247000;
    public static double climberTarget = 500;
    public static int LB = 5;
    public static int RB = 6;

    public static boolean programmerMode = true;

    public static double MagazineSpeed = -0.6;
    public static double IntakeSpeed = 1.0;
    public static double deadzone = 0;

    public static int cameraPort = 1181;

    public static final int lidarId = 0;

    public static double blinkenPattern = -0.77;

    // For trajectory driving//
    public static final double ksVolts = 0.66155;
    public static final double kvVoltSecondsPerMeter = 2.4447;
    public static final double kaVoltSecondsSquaredPerMeter = 0.37029;

    // Example value only - as above, this must be tuned for your drive!
    public static final double kPDriveVel = 7;
    // 3.3954
    public static final double kTrackwidthMeters = 0.63134;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(
            kTrackwidthMeters);

    public static final double kMaxSpeedMetersPerSecond = 2.25;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1.75;
    // Change to three meters later//

    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

}
