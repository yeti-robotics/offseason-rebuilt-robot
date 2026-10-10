// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;
import static frc.robot.constants.FieldConstants.Hub.centerHubOpening;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.SOTMCommand;
import frc.robot.constants.Constants;
// import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.drivetrain.TunerConstants;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.hood.HoodIO;
import frc.robot.subsystems.hood.HoodIOTalonFX;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeConfigs;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOTalonFX;
import frc.robot.subsystems.linslide.Linslide;
import frc.robot.subsystems.linslide.LinslideConfigs;
import frc.robot.subsystems.linslide.LinslideIO;
import frc.robot.subsystems.linslide.LinslideIOTalonFX;
import frc.robot.subsystems.miniindexer.MiniIndexer;
import frc.robot.subsystems.miniindexer.MiniIndexerConfigs;
import frc.robot.subsystems.miniindexer.MiniIndexerIO;
import frc.robot.subsystems.miniindexer.MiniIndexerIOTalonFX;
import frc.robot.subsystems.rollerbed.RollerBed;
import frc.robot.subsystems.rollerbed.RollerBedConfigs;
import frc.robot.subsystems.rollerbed.RollerBedIO;
import frc.robot.subsystems.rollerbed.RollerBedIOTalonFX;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterConfigs;
import frc.robot.subsystems.shooter.ShooterIO;
import frc.robot.subsystems.shooter.ShooterIOTalonFX;
import frc.robot.subsystems.singulator.Singulator;
import frc.robot.subsystems.singulator.SingulatorConfigs;
import frc.robot.subsystems.singulator.SingulatorIO;
import frc.robot.subsystems.singulator.SingulatorIOTalonFX;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretIO;
import frc.robot.subsystems.turret.TurretIOTalonFX;
import frc.robot.subsystems.vision.*;
import frc.robot.util.AllianceFlipUtil;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

    CommandXboxController controller;
    CommandXboxController debugController;

    private final Linslide linslide;
    private final Intake intake;
    private final RollerBed rollerBed;
    private final MiniIndexer miniIndexer;
    private final Hood hood;
    private final Turret turret;
    private final Shooter shooter;
    private final Singulator singulator;
    // private final Vision vision;

    private final CommandSwerveDrivetrain drive;

    private final SOTMCommand sotmCommand;
    private final SOTMCommand sotmCommand2;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        controller = new CommandXboxController(Constants.PRIMARY_CONTROLLER_PORT);
        debugController = new CommandXboxController(Constants.DEBUG_CONTROLLER_PORT);

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
                //                vision = new Vision(
                //                        drive,
                //                        new VisionIOPhotonVision(VisionConstants.backCam,
                // VisionConstants.backCamTrans),
                //                        new VisionIOPhotonVision(VisionConstants.leftCam,
                // VisionConstants.leftCamTrans),
                //                        new VisionIOPhotonVision(VisionConstants.rightCam,
                // VisionConstants.rightCamTrans));

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
                //                vision = new Vision(
                //                        drive,
                //                        new VisionIOPhotonVisionSim(
                //                                VisionConstants.backCam, VisionConstants.backCamTrans, () ->
                // drive.getPose()),
                //                        new VisionIOPhotonVisionSim(
                //                                VisionConstants.leftCam, VisionConstants.leftCamTrans, () ->
                // drive.getPose()),
                //                        new VisionIOPhotonVisionSim(
                //                                VisionConstants.rightCam, VisionConstants.rightCamTrans, () ->
                // drive.getPose()));

                break;

            default:
                drive = TunerConstants.createDrivetrain();
                linslide = new Linslide(new LinslideIO() {});
                intake = new Intake(new IntakeIO() {});
                rollerBed = new RollerBed(new RollerBedIO() {});
                miniIndexer = new MiniIndexer(new MiniIndexerIO() {});
                hood = new Hood(new HoodIO() {});
                turret = new Turret(new TurretIO() {});
                shooter = new Shooter(new ShooterIO() {});
                singulator = new Singulator(new SingulatorIO() {});
                //                vision = new Vision(drive, new VisionIO() {});

                break;
        }

        sotmCommand = new SOTMCommand(drive, shooter, hood, turret, centerHubOpening.toTranslation2d());

        sotmCommand2 = new SOTMCommand(drive, shooter, hood, turret, centerHubOpening.toTranslation2d());

        configureBindings();
        configureDebugBindings();
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
        drive.setDefaultCommand(drive.driveFieldRelative(
                () -> -controller.getLeftY() * TunerConstants.kSpeedAt12Volts.magnitude(),
                () -> -controller.getLeftX() * TunerConstants.kSpeedAt12Volts.magnitude(),
                () -> -controller.getRightX() * TunerConstants.MaFxAngularRate));
        turret.setDefaultCommand(turret.defaultCommand(drive));

        controller.start().onTrue(runOnce(drive::seedFieldCentric, drive));

        controller.x().whileTrue(linslide.applyPower(LinslideConfigs.DEPLOY_SPEED));
        controller.b().whileTrue(linslide.applyPower(-LinslideConfigs.DEPLOY_SPEED));

        controller
                .rightBumper()
                .whileTrue((linslide.applyPower(LinslideConfigs.DEPLOY_SPEED)
                        .withTimeout(.3)
                        .andThen(intake.applyPower(IntakeConfigs.ROLLER_SPEED)
                                .alongWith(rollerBed.spinRollerBed(RollerBedConfigs.ROLLER_BED_SPEED)))));
        controller
                .leftTrigger()
                .whileTrue(intake.applyPower(IntakeConfigs.ROLLER_SPEED)
                        .alongWith(linslide.applyPower(LinslideConfigs.DEPLOY_SPEED)
                                .until(linslide::isDeployed)
                                .andThen(linslide.applyPower(0.15))));

        controller
                .y()
                .whileTrue(Commands.parallel(
                        intake.applyPower(-IntakeConfigs.ROLLER_SPEED),
                        rollerBed.applyPower(-RollerBedConfigs.ROLLER_BED_SPEED),
                        miniIndexer.applyPower(-MiniIndexerConfigs.MINI_INDEXER_SPEED),
                        singulator.applyPower(-SingulatorConfigs.INDEXER_SPEED),
                        shooter.applyPower(-ShooterConfigs.SHOOTER_SPEED),
                        linslide.applyPower(LinslideConfigs.DEPLOY_SPEED)));

        controller
                .rightTrigger()
                .whileTrue(Commands.parallel(
                        Commands.either(
                                sotmCommand,
                                sotmCommand2,
                                () -> AllianceFlipUtil.apply(drive.getPose().getX()) < 4.9),
                        linslide.applyPower(LinslideConfigs.DEPLOY_SPEED),
                        Commands.wait(1.0)
                                .andThen(Commands.parallel(
                                        miniIndexer.applyPower(MiniIndexerConfigs.MINI_INDEXER_SPEED),
                                        intake.applyPower(IntakeConfigs.ROLLER_SPEED),
                                        rollerBed.applyPower(RollerBedConfigs.ROLLER_BED_SPEED),
                                        singulator.applyPower(1)))))
                .onFalse(hood.setPosition(0));

        controller.povLeft().onTrue(hood.setPosition(0));
        controller.povRight().onTrue(hood.setPosition(0.65));
    }

    private void configureDebugBindings() {
        debugController.a().whileTrue(intake.applyPower(0.5));
        debugController.b().whileTrue(intake.applyPower(-0.5));
        // debugController.b().whileTrue(turret.applyPower(0.5));
        debugController.x().whileTrue(linslide.applyPower(0.1));
        debugController.y().whileTrue(linslide.deploy());
        debugController.leftTrigger().whileTrue(shooter.applyPower(0.5));
        debugController.rightTrigger().whileTrue(hood.applyPower(0.5));
        debugController
                .povDown()
                .whileTrue(Commands.parallel(
                        rollerBed.applyPower(-0.5), singulator.applyPower(1), miniIndexer.applyPower(-1)));
        debugController
                .povLeft()
                .whileTrue(Commands.parallel(
                        rollerBed.applyPower(-0.5),
                        singulator.applyPower(1),
                        miniIndexer.applyPower(-1),
                        intake.applyPower(-0.5),
                        shooter.applyPower(1)));
        debugController.povUp().whileTrue(singulator.applyPower(0.5));
        debugController.rightBumper().whileTrue(shooter.applyPower(.8));
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
