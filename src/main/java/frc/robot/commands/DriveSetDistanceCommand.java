package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;

public class DriveSetDistanceCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private double m_distance;
    public Timer m_driveTime = new Timer();

    public DriveSetDistanceCommand(DriveSubsystem subsytem, double distance) {
        m_subsystem = subsytem;
        addRequirements(m_subsystem);
        m_distance = inchesToTicks(distance);
    }

    public double inchesToTicks(double input) {
        return ((2048 * 10.71 * input) / (6 * Math.PI));
    }

    @Override
    public void initialize() {
        m_subsystem.enablepid(m_distance);
        m_driveTime.reset();
        m_driveTime.start();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        System.out.println(".");
        System.out.println("Time to Complete: " + m_driveTime.get() + " Distance Moved in Ticks: " + m_distance);
        System.out.println("Left Error: " + (m_subsystem.m_left1.getClosedLoopTarget()-m_subsystem.m_left1.getSelectedSensorPosition(0)));
        System.out.println("Right Error: " + (m_subsystem.m_right1.getClosedLoopTarget()-m_subsystem.m_right1.getSelectedSensorPosition(0)));
        System.out.println("Left Velocity: " + m_subsystem.m_left1.getSelectedSensorVelocity(0));
        System.out.println("Right Velocity: " + m_subsystem.m_right1.getSelectedSensorVelocity(0));
        System.out.println(".");
        m_subsystem.disablepid();
    }

    @Override
    public boolean isFinished() {
        // System.out.println(m_subsystem.m_left1.getClosedLoopTarget()-m_subsystem.m_left1.getSelectedSensorPosition(0));
        return
        Math.abs(m_subsystem.m_left1.getClosedLoopTarget()-m_subsystem.m_left1.getSelectedSensorPosition(0))
        < 300 && Math.abs(m_subsystem.m_left1.getSelectedSensorVelocity(0)) < 300 &&
        Math.abs(m_subsystem.m_right1.getClosedLoopTarget()-m_subsystem.m_right1.getSelectedSensorPosition(0))
        < 300 && Math.abs(m_subsystem.m_right1.getSelectedSensorVelocity(0)) < 300;
        // return false;
    }

}
