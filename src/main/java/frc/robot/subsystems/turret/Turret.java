package frc.robot.subsystems.turret;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.util.AllianceFlipUtil;
import org.littletonrobotics.junction.Logger;

public class Turret extends SubsystemBase {
    private final TurretIO io;
    private final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();

    public Turret(TurretIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Turret", inputs);
    }

    public Command setPosition(Angle position) {
        return runOnce(() -> io.setPosition(position));
    }

    public void moveTo(Angle position) {
        io.setPosition(position);
    }

    public Angle getPosition() {
        return Units.Rotations.of(inputs.position);
    }

    public Command applyPower(double percent) {
        return runEnd(() -> io.applyPower(percent), () -> io.applyPower(0));
    }

    private Translation2d getTurretPosition(Pose2d robotPose) {
        return robotPose.getTranslation().plus(TurretConfigs.turretOffset.rotateBy(robotPose.getRotation()));
    }

    private Translation2d getAimTarget(Pose2d robotPose) {
        Translation2d target = FieldConstants.isInOwnAllianceZone(robotPose.getX())
                ? FieldConstants.Hub.centerHubOpening.toTranslation2d()
                : FieldConstants.Shuttle.shuttleTargetZone;
        return AllianceFlipUtil.apply(target);
    }

    private Angle resolveReachableTurretAngle(Rotation2d desiredHeading, double currentRotations) {
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

    /**
     * Point at the hub while in our own alliance zone (where it can be
     * scored on), or at the shuttle target otherwise (neutral zone or opponent's zone).
     */
    public Command defaultCommand(CommandSwerveDrivetrain drive) {
        return run(() -> {
            Pose2d robotPose = drive.getState().Pose;
            Translation2d turretPosition = getTurretPosition(robotPose);
            Translation2d aimTarget = getAimTarget(robotPose);

            Rotation2d desiredFieldHeading = aimTarget.minus(turretPosition).getAngle();
            Rotation2d desiredTurretHeading = desiredFieldHeading.minus(robotPose.getRotation());
            Angle targetTurretAngle = resolveReachableTurretAngle(desiredTurretHeading, getPosition().in(Units.Rotations));

            moveTo(targetTurretAngle);
        });
    }
}
