// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.constants.Constants;
// import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.TunerConstants;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.hood.HoodIOTalonFX;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOTalonFX;
import frc.robot.subsystems.linslide.Linslide;
import frc.robot.subsystems.linslide.LinslideIO;
import frc.robot.subsystems.linslide.LinslideIOTalonFX;
import frc.robot.subsystems.miniindexer.MiniIndexer;
import frc.robot.subsystems.miniindexer.MiniIndexerIO;
import frc.robot.subsystems.miniindexer.MiniIndexerIOTalonFX;
import frc.robot.subsystems.rollerbed.RollerBed;
import frc.robot.subsystems.rollerbed.RollerBedIO;
import frc.robot.subsystems.rollerbed.RollerBedIOTalonFX;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterIO;
import frc.robot.subsystems.shooter.ShooterIOTalonFX;
import frc.robot.subsystems.singulator.Singulator;
import frc.robot.subsystems.singulator.SingulatorIO;
import frc.robot.subsystems.singulator.SingulatorIOTalonFX;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretIO;
import frc.robot.subsystems.turret.TurretIOTalonFX;
import frc.robot.subsystems.vision.*;
import org.littletonrobotics.junction.Logger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    CommandXboxController primary;
    private final Linslide linslide;
    private final Intake intake;
    private final RollerBed rollerBed;
    private final MiniIndexer miniIndexer;
    private final Hood hood;
    private final Turret turret;
    private final Shooter shooter;
    private final Singulator singulator;
    private final Vision vision;

    private final CommandSwerveDrivetrain drive;

    private final SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric()
            .withDeadband(TunerConstants.MAX_VELOCITY_METERS_PER_SECOND * 0.1)
            .withRotationalDeadband(TunerConstants.MaFxAngularRate * 0.1)
            .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        primary = new CommandXboxController(Constants.PRIMARY_CONTROLLER_PORT);

        switch (Constants.currentMode) {
            case REAL:
                drive = TunerConstants.createDrivetrain();
                linslide = new Linslide(new LinslideIOTalonFX());
                intake = new Intake(new IntakeIOTalonFX());
                rollerBed = new RollerBed(new RollerBedIOTalonFX());
                miniIndexer = new MiniIndexer(new MiniIndexerIOTalonFX());
                hood = new Hood(new HoodIOTalonFX());
                turret = new Turret(new TurretIOTalonFX());
                shooter = new Shooter(new ShooterIOTalonFX());
                singulator = new Singulator(new SingulatorIOTalonFX());
                vision = new Vision(drive,
                        new VisionIOPhotonVision(VisionConstants.frontCam, VisionConstants.frontCamTrans),
                        new VisionIOPhotonVision(VisionConstants.backCam, VisionConstants.backCamTrans),
                        new VisionIOPhotonVision(VisionConstants.sideCam, VisionConstants.sideCamTrans));

                break;

            case SIM:
                drive = TunerConstants.createDrivetrain();
                linslide = new Linslide(new LinslideIOTalonFX());
                intake = new Intake(new IntakeIOTalonFX());
                rollerBed = new RollerBed(new RollerBedIOTalonFX());
                miniIndexer = new MiniIndexer(new MiniIndexerIOTalonFX());
                hood = new Hood(new HoodIOTalonFX());
                turret = new Turret(new TurretIOTalonFX());
                shooter = new Shooter(new ShooterIOTalonFX());
                singulator = new Singulator(new SingulatorIOTalonFX());
                vision = new Vision(drive,
                        new VisionIOPhotonVisionSim(VisionConstants.frontCam, VisionConstants.frontCamTrans, () -> drive.getState().Pose),
                        new VisionIOPhotonVisionSim(VisionConstants.backCam, VisionConstants.backCamTrans, () -> drive.getState().Pose),
                        new VisionIOPhotonVisionSim(VisionConstants.sideCam, VisionConstants.sideCamTrans, () -> drive.getState().Pose));

                break;

            default:
                drive = TunerConstants.createDrivetrain();
                linslide = new Linslide(new LinslideIO() {});
                intake = new Intake(new IntakeIO() {});
                rollerBed = new RollerBed(new RollerBedIO() {});
                miniIndexer = new MiniIndexer(new MiniIndexerIO() {});
                hood = new Hood(new HoodIOTalonFX());
                turret = new Turret(new TurretIO() {});
                shooter = new Shooter(new ShooterIO() {});
                singulator = new Singulator(new SingulatorIO() {});
                vision = new Vision(drive, new VisionIO() {}, new VisionIO() {});

                break;
        }

        configureBindings();
    }

    public void updateVisionSim() {
        Pose3d sideCameraPose = new Pose3d(drive.getState().Pose).transformBy(VisionConstants.sideCamTrans);
        Pose3d frontCameraPose = new Pose3d(drive.getState().Pose).transformBy(VisionConstants.frontCamTrans);
        Pose3d backCameraPose = new Pose3d(drive.getState().Pose).transformBy(VisionConstants.backCamTrans);
        Logger.recordOutput("Side Cam Transform", sideCameraPose);
        Logger.recordOutput("Front Cam Transform", frontCameraPose);
        Logger.recordOutput("Back Cam Transform", backCameraPose);
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        drive.setDefaultCommand(drive.applyRequest(() -> driveRequest
                .withVelocityX(-primary.getLeftY() * TunerConstants.kSpeedAt12Volts.magnitude())
                .withVelocityY(-primary.getLeftX() * TunerConstants.kSpeedAt12Volts.magnitude())
                .withRotationalRate(-primary.getRightX() * TunerConstants.MaFxAngularRate)));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return null;
    }
}
