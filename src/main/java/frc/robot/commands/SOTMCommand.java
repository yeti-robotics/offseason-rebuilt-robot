package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterConfigs;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretConfigs;
import frc.robot.util.AllianceFlipUtil;
import frc.robot.util.ShooterStateData;
import org.littletonrobotics.junction.Logger;

public class SOTMCommand extends Command {

    private static final int MAX_FLIGHT_TIME_ITERATIONS = 5;
    private static final double FLIGHT_TIME_TOLERANCE_SECONDS = 0.005;

    private final CommandSwerveDrivetrain drive;
    private final Shooter shooter;
    private final Hood hood;
    private final Turret turret;
    private final Translation2d target;

    public SOTMCommand(CommandSwerveDrivetrain drive, Shooter shooter, Hood hood, Turret turret, Translation2d target) {
        this.drive = drive;
        this.shooter = shooter;
        this.hood = hood;
        this.turret = turret;
        this.target = target;
        addRequirements(shooter, hood, turret);
    }

    private Translation2d getTurretPosition(Pose2d robotPose) {
        return robotPose.getTranslation().plus(TurretConfigs.turretOffset.rotateBy(robotPose.getRotation()));
    }

    private Translation2d getTurretFieldVelocity(Pose2d robotPose, ChassisSpeeds robotRelativeSpeeds) {
        ChassisSpeeds fieldSpeeds = robotRelativeSpeeds.toFieldRelative(robotPose.getRotation());
        Translation2d offsetField = TurretConfigs.turretOffset.rotateBy(robotPose.getRotation());
        Translation2d tangentialVelocity =
                new Translation2d(-fieldSpeeds.omega * offsetField.getY(), fieldSpeeds.omega * offsetField.getX());

        return new Translation2d(fieldSpeeds.vx, fieldSpeeds.vy).plus(tangentialVelocity);
    }

    private Angle resolveReachableTurretAngle(Rotation2d desiredHeading) {
        double currentRotations = turret.getPosition().in(Units.Rotations);
        double minRotations = TurretConfigs.MIN_ANGLE.in(Units.Rotations);
        double maxRotations = TurretConfigs.MAX_ANGLE.in(Units.Rotations);
        double baseRotations = desiredHeading.getRotations();

        long lowK = (long) Math.floor(minRotations - baseRotations) - 1;
        long highK = (long) Math.ceil(maxRotations - baseRotations) + 1;

        double bestRotations = Math.max(minRotations, Math.min(maxRotations, currentRotations));
        double bestDistance = Double.POSITIVE_INFINITY;
        for (long k = lowK; k <= highK; k++) {
            double candidate = baseRotations + k;
            if (candidate < minRotations || candidate > maxRotations) {
                continue;
            }
            double distance = Math.abs(candidate - currentRotations);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestRotations = candidate;
            }
        }

        return Units.Rotations.of(bestRotations);
    }

    @Override
    public void execute() {
        Pose2d robotPose = drive.getState().Pose;
        ChassisSpeeds robotRelativeSpeeds = drive.getState().Speeds;
        Translation2d turretPosition = getTurretPosition(robotPose);

        Translation2d allianceTarget = AllianceFlipUtil.apply(target);

        double timeOfFlight = ShooterConfigs.SHOOTER_MAP
                .get(allianceTarget.getDistance(turretPosition))
                .timeOfFlight;

        Rotation2d releaseRobotRotation = robotPose.getRotation()
                .plus(Rotation2d.fromRadians(robotRelativeSpeeds.omega * ShooterConfigs.SHOOTER_LATENCY_COMP));
        ChassisSpeeds currentFieldSpeeds = robotRelativeSpeeds.toFieldRelative(robotPose.getRotation());
        Translation2d releaseRobotTranslation = robotPose.getTranslation()
                .plus(new Translation2d(currentFieldSpeeds.vx, currentFieldSpeeds.vy)
                        .times(ShooterConfigs.SHOOTER_LATENCY_COMP));
        Pose2d releasePose = new Pose2d(releaseRobotTranslation, releaseRobotRotation);

        Translation2d turretPositionAtRelease = getTurretPosition(releasePose);
        Translation2d turretVelocityAtRelease = getTurretFieldVelocity(releasePose, robotRelativeSpeeds);

        Translation2d compensatedTarget;
        ShooterStateData state;

        int iteration = 0;
        while (true) {
            compensatedTarget = allianceTarget.minus(turretVelocityAtRelease.times(timeOfFlight));

            double compensatedDistance = compensatedTarget.getDistance(turretPositionAtRelease);
            state = ShooterConfigs.SHOOTER_MAP.get(compensatedDistance);

            boolean converged = Math.abs(state.timeOfFlight - timeOfFlight) < FLIGHT_TIME_TOLERANCE_SECONDS;
            timeOfFlight = state.timeOfFlight;
            iteration++;

            if (converged || iteration >= MAX_FLIGHT_TIME_ITERATIONS) {
                break;
            }
        }

        double targetRPS = state.rps;
        Angle targetHoodAngle = state.hoodPos;

        Rotation2d desiredFieldHeading =
                compensatedTarget.minus(turretPositionAtRelease).getAngle();
        Rotation2d desiredTurretHeading = desiredFieldHeading.minus(releaseRobotRotation);
        Angle targetTurretAngle = resolveReachableTurretAngle(desiredTurretHeading);

        Logger.recordOutput("SOTM/Target RPS", targetRPS);
        Logger.recordOutput("SOTM/Target Hood Angle", targetHoodAngle.magnitude());
        Logger.recordOutput("SOTM/Target Turret Angle", targetTurretAngle.magnitude());

        turret.moveTo(targetTurretAngle);
        hood.moveTo(targetHoodAngle);
        shooter.spinAt(targetRPS);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.spinAt(0);
    }
}
