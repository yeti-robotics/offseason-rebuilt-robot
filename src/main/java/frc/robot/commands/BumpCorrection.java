package frc.robot.commands;

import static frc.robot.constants.Constants.currentMode;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.constants.Constants;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.TunerConstants;
import frc.robot.subsystems.vision.VisionConstants;

import java.util.Set;

public class BumpCorrection {
    private final CommandSwerveDrivetrain drive;
    private final double bumpY = 4;
    private final SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric()
            .withDeadband(TunerConstants.MAX_VELOCITY_METERS_PER_SECOND * 0.1)
            .withRotationalDeadband(TunerConstants.MaFxAngularRate * 0.1)
            .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage);

    public BumpCorrection(CommandSwerveDrivetrain drive) {
        this.drive = drive;
    }

    public Command correction(double velocity) {
        return Commands.defer(
                () -> drive.runOnce(() -> drive.applyRequest(() -> driveRequest.withVelocityX(velocity))
                        .onlyIf(() -> drive.getState().Pose.getY() - bumpY > 1)),
                Set.of(drive));
    }
}
