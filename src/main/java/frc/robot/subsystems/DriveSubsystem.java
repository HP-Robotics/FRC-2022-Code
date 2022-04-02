package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.FalconCANIntervalConfig;

public class DriveSubsystem extends SubsystemBase {

  public ADXRS450_Gyro m_gyro;

  private ShuffleboardTab m_tab = Shuffleboard.getTab("Drive Train Configuration");
  private NetworkTableEntry m_rightHandSlowdown = m_tab.add("Right Hand Slowdown", Constants.rightHandSlowdown)
      .getEntry();
  private NetworkTableEntry m_leftHandSlowdown = m_tab.add("Left Hand Slowdown", Constants.leftHandSlowdown)
      .getEntry();

  public TalonFX m_left1;
  public TalonFX m_right1;
  public TalonFX m_left2;
  public TalonFX m_right2;
  public Orchestra m_orchestra;
  public Boolean m_velocityDrive = false;
  public final DifferentialDriveOdometry m_odometry;

  public DriveSubsystem() {
    m_left1 = new TalonFX(1);
    m_right1 = new TalonFX(2);
    m_left2 = new TalonFX(3);
    m_right2 = new TalonFX(4);

    // FalconCANIntervalConfig.ScrambleCANInterval(m_left1, true, true);
    // FalconCANIntervalConfig.ScrambleCANInterval(m_left2, true, true);
    // FalconCANIntervalConfig.ScrambleCANInterval(m_right1, true, true);
    // FalconCANIntervalConfig.ScrambleCANInterval(m_right2, true, true);

    m_orchestra = new Orchestra();
    m_orchestra.addInstrument(m_left1);
    m_orchestra.addInstrument(m_left2);
    m_orchestra.addInstrument(m_right1);
    m_orchestra.addInstrument(m_right2);
    m_orchestra.loadMusic("/home/lvuser/TestChirp.chrp");

    m_left1.configFactoryDefault();
    m_right1.configFactoryDefault();
    m_left2.configFactoryDefault();
    m_right2.configFactoryDefault();

    if (m_velocityDrive) {
      /* First number is a maximum current limit, once that is reached, we are limited to the second number
         for the duration given by the third number */
     /* StatorCurrentLimitConfiguration limit = new StatorCurrentLimitConfiguration(true, 30, 40, 0.5);
      m_left1.configStatorCurrentLimit(limit);
      m_right1.configStatorCurrentLimit(limit);
      m_left2.configStatorCurrentLimit(limit);
      m_right2.configStatorCurrentLimit(limit);*/
    }

    m_right1.setInverted(true);
    m_right2.setInverted(true);

    m_left2.follow(m_left1);
    m_right2.follow(m_right1);

    setuppid(Constants.drivekP, Constants.drivekI, Constants.drivekD, Constants.drivekF, 100);

    m_gyro = new ADXRS450_Gyro();

    m_gyro.calibrate();
    m_gyro.reset();

    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
    resetOdometry(new Pose2d());

  }

  public double ticksToMeters(double input) {
    return (input*6*Math.PI*0.0254)/(2048*10.71);
}


  public void periodic() {
    // SmartDashboard.putNumber("LeftFront", m_left1.getSelectedSensorVelocity());
    // SmartDashboard.putNumber("LeftBack", m_left2.getSelectedSensorVelocity());
    // SmartDashboard.putNumber("RightFront", m_right1.getSelectedSensorVelocity());
    // SmartDashboard.putNumber("RightBack", m_right2.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());
    m_odometry.update(
      m_gyro.getRotation2d(),ticksToMeters( m_left1.getSelectedSensorPosition()), ticksToMeters(m_right1.getSelectedSensorPosition()));

}
  

  public void drive(double left, double right) {
    if (m_velocityDrive) {
      if (Math.abs(left) < 0.05) {
        m_left1.set(ControlMode.PercentOutput, 0);
      } else {
        m_left1.set(ControlMode.Velocity, left * Constants.maxVelocity);
      }
      if (Math.abs(right) < 0.05) {
        m_right1.set(ControlMode.PercentOutput, 0);
      } else {
        m_right1.set(ControlMode.Velocity, right * Constants.maxVelocity);
      }
    } else {
      m_left1.set(ControlMode.PercentOutput, left);
      m_right1.set(ControlMode.PercentOutput, right);
    }
  }

  public void arcadeDrive(double left, double right) {

    double leftSpeed = 0.0;
    double rightSpeed = 0.0;

    double maxInput = Math.copySign(Math.max(Math.abs(left), Math.abs(right)),
        left);

    if (left >= 0.0) {
      // First quadrant, else second quadrant
      if (right >= 0.0) {
        leftSpeed = maxInput;
        rightSpeed = left - right;
      } else {
        leftSpeed = left + right;
        rightSpeed = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (right >= 0.0) {
        leftSpeed = left + right;
        rightSpeed = maxInput;
      } else {
        leftSpeed = maxInput;
        rightSpeed = left - right;
      }
    }
    // System.out.println(leftSpeed);
    drive(leftSpeed, rightSpeed);
  }

  public void setuppid(double kP, double kI, double kD, double kF, int timeout) {

    if (m_velocityDrive) {
      kI = 0.0;
    }

    m_left1.config_kP(0, kP, timeout);
    m_left1.config_kI(0, kI, timeout);
    m_left1.config_kD(0, kD, timeout);
    m_left1.config_kF(0, kF, timeout);
    m_left1.configMotionAcceleration(9000);
    m_left1.configMotionCruiseVelocity(9000);

    m_right1.config_kP(0, kP, timeout);
    m_right1.config_kI(0, kI, timeout);
    m_right1.config_kD(0, kD, timeout);
    m_right1.config_kF(0, kF, timeout);
    m_right1.configMotionAcceleration(9000);
    m_right1.configMotionCruiseVelocity(9000);

    m_left2.config_kP(0, kP, timeout);
    m_left2.config_kI(0, kI, timeout);
    m_left2.config_kD(0, kD, timeout);
    m_left2.config_kF(0, kF, timeout);
    m_left2.configMotionAcceleration(9000);
    m_left2.configMotionCruiseVelocity(9000);

    m_right2.config_kP(0, kP, timeout);
    m_right2.config_kI(0, kI, timeout);
    m_right2.config_kD(0, kD, timeout);
    m_right2.config_kF(0, kF, timeout);
    m_right2.configMotionAcceleration(9000);
    m_right2.configMotionCruiseVelocity(9000);

  }

  public void enablepid(double distance) {
    m_left1.setSelectedSensorPosition(0);
    m_right1.setSelectedSensorPosition(0);
    m_left2.setSelectedSensorPosition(0);
    m_right2.setSelectedSensorPosition(0);

    m_right1.set(ControlMode.MotionMagic, distance);

    m_left1.set(ControlMode.MotionMagic, distance);

  }

  public void turnpid(double distance) {
    m_left1.setSelectedSensorPosition(0);
    m_right1.setSelectedSensorPosition(0);
    m_left2.setSelectedSensorPosition(0);
    m_right2.setSelectedSensorPosition(0);

    m_right1.set(ControlMode.MotionMagic, distance);
    m_left1.set(ControlMode.MotionMagic, -distance);
  }

  public void setCANFrame(TalonFX Talon, int period) {
    Talon.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, period);
    Talon.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, period);
    Talon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, period);
  }

  public void disablepid() {
    m_left1.set(ControlMode.PercentOutput, 0);
    m_right1.set(ControlMode.PercentOutput, 0);
  }

  public void playorchestra() {
    if (!m_orchestra.isPlaying()) {
      m_orchestra.play();
    }
  }

  public void stoporchestra() {
    if (m_orchestra.isPlaying()) {
      m_orchestra.stop();
    }
  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(10*ticksToMeters(m_left1.getSelectedSensorVelocity()),10*ticksToMeters( m_right1.getSelectedSensorVelocity()));
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   * @return 
   */

   public void zOdemtry() {
     resetOdometry(new Pose2d());
   }

  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_gyro.reset();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_left1.set(ControlMode.PercentOutput, leftVolts/12);
      m_right1.set(ControlMode.PercentOutput, rightVolts/12);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_left1.setSelectedSensorPosition(0);
    m_right1.setSelectedSensorPosition(0);
    m_left2.setSelectedSensorPosition(0);
    m_right2.setSelectedSensorPosition(0);
  }
}
