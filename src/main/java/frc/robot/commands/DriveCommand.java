package frc.robot.commands;

import java.util.function.DoubleSupplier;



import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private DoubleSupplier leftSupplier;
    private DoubleSupplier rightSupplier;

public DriveCommand(DriveSubsystem subsytem, DoubleSupplier left1, DoubleSupplier right1){
    m_subsystem = subsytem;
    leftSupplier=left1;
    rightSupplier=right1;


addRequirements(m_subsystem);
}

@Override
public void execute() {
    m_subsystem.drive(leftSupplier.getAsDouble(), rightSupplier.getAsDouble());
}

@Override
public void end(boolean interrupted) {
  m_subsystem.drive(0, 0);
}

@Override
public boolean isFinished() {
    return false;
}
}
