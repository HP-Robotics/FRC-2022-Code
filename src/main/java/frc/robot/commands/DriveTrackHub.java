package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class DriveTrackHub extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final JoystickSubsystem m_joystickSubsystem;
    private final ShooterSubsystem m_shooterSubsystem;
    Boolean m_turning;

    public DriveTrackHub(DriveSubsystem subsytem, JoystickSubsystem joysticks, ShooterSubsystem shooter) {
        m_subsystem = subsytem;
        m_joystickSubsystem = joysticks;
        m_shooterSubsystem = shooter;
        addRequirements(m_subsystem);
        addRequirements(m_joystickSubsystem);
    }


    @Override
    public void initialize() {
        m_subsystem.m_gyro.reset();
        m_turning = false;
    }


    @Override
    public void execute() {
        if (m_shooterSubsystem.trackingHub()) {
            if(!m_shooterSubsystem.m_usePython){
                m_subsystem.arcadeDrive(m_joystickSubsystem.driverMove(),
                m_shooterSubsystem.getNormalizedHubX()/56);
                return;
            }
              if(Math
                .abs(m_subsystem.m_left1.getClosedLoopTarget() - m_subsystem.m_left1.getSelectedSensorPosition(0)) < 100
                && Math.abs(m_subsystem.m_left1.getSelectedSensorVelocity(0)) < 50 &&
                Math.abs(m_subsystem.m_right1.getClosedLoopTarget()
                        - m_subsystem.m_right1.getSelectedSensorPosition(0)) < 100
                && Math.abs(m_subsystem.m_right1.getSelectedSensorVelocity(0)) < 50){
                    m_turning = false;
                }
            if(m_subsystem.m_gyroTarget != m_shooterSubsystem.getNormalizedHubX() && (!m_turning)){
                m_subsystem.setGyroTarget((m_shooterSubsystem.getNormalizedHubX()));
                double  m_distance = inchesToTicks(m_subsystem.getGyroAngle() * Constants.degreesToInches);
                m_subsystem.turnpid(m_distance);
                m_turning = true;
            }
        } else {
            m_subsystem.arcadeDrive(m_joystickSubsystem.driverMove(),
                    m_joystickSubsystem.driverSpin());
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.drive(0, 0);
    }


    public double inchesToTicks(double input) {
        return ((2048 * 10.71 * input) / (6 * Math.PI));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
