package frc.robot.commands;

import java.util.function.DoubleSupplier;

import org.opencv.features2d.FlannBasedMatcher;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DriveSubsystem;

public class SpeedCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;

public SpeedCommand(DriveSubsystem subsytem){
m_subsystem = subsytem;



addRequirements(m_subsystem);
}

@Override
public void execute() {
    if(m_subsystem.speed == 1.0){
        m_subsystem.speed = 0.5;
    }
    else{
        m_subsystem.speed = 1.0;
    }


}

@Override
public void end(boolean interrupted) {
}

@Override
public boolean isFinished() {
    return true;
}
}
