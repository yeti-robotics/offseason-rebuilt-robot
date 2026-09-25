package frc.robot.commands;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.linslide.Linslide;
import frc.robot.subsystems.miniindexer.MiniIndexer;
import frc.robot.subsystems.rollerbed.RollerBed;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.turret.Turret;
import frc.robot.util.PathPlannerUtils;
import java.util.Optional;

import static edu.wpi.first.wpilibj2.command.Commands.runEnd;
import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.linslide.Linslide;
import frc.robot.subsystems.miniindexer.MiniIndexer;
import frc.robot.subsystems.rollerbed.RollerBed;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.vision.Vision;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

public class AutoCommands {
    private final CommandSwerveDrivetrain drivetrain;
    private final Hood hood;
    private final Intake intake;
    private final Linslide linslide;
    private final MiniIndexer miniIndexer;
    private final RollerBed rollerBed;
    private final Shooter shooter;
    private final Turret turret;
    private final Vision vision;
    private final AutoFactory autoFactory;

    public AutoCommands(CommandSwerveDrivetrain drivetrain, Hood hood, Intake intake, Linslide linslide, MiniIndexer miniIndexer, RollerBed rollerBed, Shooter shooter, Turret turret, Vision vision, AutoFactory autoFactory) {
        this.drivetrain = drivetrain;
        this.hood = hood;
        this.intake = intake;
        this.linslide = linslide;
        this.miniIndexer = miniIndexer;
        this.rollerBed = rollerBed;
        this.shooter = shooter;
        this.turret = turret;
        this.vision = vision;
        this.autoFactory = autoFactory;
    }

    public Command linSlideOut() {
        return linslide.deploy();
    }

    public Command linSlideIn() {
        return linslide.stow();
    }

    public Command intakeOn() {
        return intake.intakeOn(100);
    }

    public Command intakeOff() {
        return intake.intakeOff();
    }

    public Command hoodTop() {
        return hood.top();
    }

    public Command hoodMid() {
        return hood.mid();
    }

    public Command hoodBottom() {
        return hood.bottom();
    }

    public Command shoot() {
        //Filler vaulues
        return shooter.shoot(0);
    }

    public Boolean trajectoryValid(AutoTrajectory trajectory) {
        return trajectory.getInitialPose().isPresent();
    }

    public Command autoLeftChoreo() {
        AutoRoutine autoRoutine = autoFactory.newRoutine("leftAuto");
        AutoTrajectory intakeOneTraj = autoRoutine.trajectory("intakeOne");
        AutoTrajectory shootOneTraj = autoRoutine.trajectory("shootOne");
        AutoTrajectory intakeTwoTraj = autoRoutine.trajectory("intakeTwo");
        AutoTrajectory shootTwoTraj = autoRoutine.trajectory("shootTwo");

        intakeOneTraj.atTime(0.88).onTrue(linSlideOut().andThen(intakeOn().until(intakeOneTraj.doneFor(0.5))));
        shootOneTraj.atTime(1.38).onTrue(shoot().until(shootOneTraj.doneFor(0)));
        intakeTwoTraj.atTime(0.97).onTrue(linSlideOut().andThen(intakeOn().until(intakeTwoTraj.doneFor(0.5))));
        shootTwoTraj.atTime(1.25).onTrue(shoot().until(shootTwoTraj.doneFor(0)));

        autoRoutine
                .active()
                .onTrue(Commands.sequence(intakeOneTraj.resetOdometry(), intakeOneTraj.cmd(), shootOneTraj.cmd(), intakeTwoTraj.cmd(), shootTwoTraj.cmd())
                        .onlyIf(() -> trajectoryValid(intakeOneTraj) && trajectoryValid(intakeTwoTraj) && trajectoryValid(shootOneTraj) && trajectoryValid(shootTwoTraj)) );

        return autoRoutine.cmd();

    }
    public Command autoRightChoreo() {

        AutoRoutine autoRoutine = autoFactory.newRoutine("rightAuto");
        AutoTrajectory start_blue_RTraj = autoRoutine.trajectory("startROne");

        AutoTrajectory intake_blue_R_1Traj = autoRoutine.trajectory("intakeROne");
        AutoTrajectory intake_blue_R_2Traj = autoRoutine.trajectory("intakeRTwo");
        AutoTrajectory intake_blue_R_3Traj = autoRoutine.trajectory("intakeRThree");
        AutoTrajectory intake_blue_R_4Traj = autoRoutine.trajectory("intakeRFour");

        start_blue_RTraj.atTime(1.1).onTrue(linSlideOut().andThen(intakeOn().until(start_blue_RTraj.doneFor(0.5))));
        intake_blue_R_1Traj.atTime(0).onTrue(shoot().until(intake_blue_R_1Traj.doneFor(3.5)));
        intake_blue_R_1Traj.atTime(4.3).onTrue(linSlideOut().andThen(intakeOn().until(intake_blue_R_1Traj.doneFor(0.4))));
        intake_blue_R_2Traj.atTime(0).onTrue(shoot().until(intake_blue_R_2Traj.doneFor(4)));
        intake_blue_R_2Traj.atTime(4.8).onTrue(linSlideOut().andThen(intakeOn().until(intake_blue_R_2Traj.doneFor(1))));
        intake_blue_R_3Traj.atTime(0).onTrue(shoot().until(intake_blue_R_3Traj.doneFor(4)));
        intake_blue_R_3Traj.atTime(4.8).onTrue(linSlideOut().andThen(intakeOn().until(intake_blue_R_3Traj.doneFor(1))));
        intake_blue_R_4Traj.atTime(0).onTrue(shoot().until(intake_blue_R_4Traj.doneFor(3.7)));
        intake_blue_R_4Traj.atTime(4.4).onTrue(linSlideOut().andThen(intakeOn().until(intake_blue_R_4Traj.doneFor(1.3))));

        autoRoutine
                .active()
                .onTrue(Commands.sequence(start_blue_RTraj.resetOdometry(), start_blue_RTraj.cmd(), intake_blue_R_1Traj.cmd(), intake_blue_R_2Traj.cmd(), intake_blue_R_3Traj.cmd(), intake_blue_R_4Traj.cmd())
                        .onlyIf(() -> trajectoryValid(start_blue_RTraj) && trajectoryValid(intake_blue_R_1Traj) && trajectoryValid(intake_blue_R_2Traj) && trajectoryValid(intake_blue_R_3Traj) && trajectoryValid(intake_blue_R_4Traj)));

        return autoRoutine.cmd();


    }

    public Command LeftAutoPathPlanner() {
        Optional<PathPlannerPath> Trench_Neutral_L1 = PathPlannerUtils.loadPathByName("Trench_Neutral_L1");
        Optional<PathPlannerPath> Neutral_Shoot_L2 = PathPlannerUtils.loadPathByName("Trench_Neutral_L2");
        Optional<PathPlannerPath> Shoot_Neutral_L3 = PathPlannerUtils.loadPathByName("Shoot_Neutral_L3");
        Optional<PathPlannerPath> Neutral_Neutral_L4 = PathPlannerUtils.loadPathByName("Neutral_Neutral_L4");
        Optional<PathPlannerPath> Shoot_Neutral_L5 = PathPlannerUtils.loadPathByName("Shoot_Neutral_L5");
        Optional<PathPlannerPath> Neutral_Shoot_L6 = PathPlannerUtils.loadPathByName("Neutral_Shoot_L6");

        PathPlannerAuto Auto;

        var cmd = Trench_Neutral_L1.isEmpty()
                || Neutral_Shoot_L2.isEmpty()
                || Shoot_Neutral_L3.isEmpty()
                || Neutral_Neutral_L4.isEmpty()
                || Shoot_Neutral_L5.isEmpty()
                || Neutral_Shoot_L6.isEmpty()
                ? Commands.none()
                : Commands.sequence(

        );

        Auto = new PathPlannerAuto(cmd);

        return Auto;
    }

    public Command RightAutoPathPlanner() {
        Optional<PathPlannerPath> start_neutral_R1 = PathPlannerUtils.loadPathByName("start_neutral_R1");
        Optional<PathPlannerPath> neutral_home_R2 = PathPlannerUtils.loadPathByName("neutral_home_R2");
        Optional<PathPlannerPath> home_trench_R3 = PathPlannerUtils.loadPathByName("home_trench_R3");
        Optional<PathPlannerPath> trench_neutral_R4 = PathPlannerUtils.loadPathByName("trench_neutral_R4");
        Optional<PathPlannerPath> neutral_trench_R5 = PathPlannerUtils.loadPathByName("neutral_trench_R5");
        Optional<PathPlannerPath> trench_home_R6 = PathPlannerUtils.loadPathByName("trench_home_R6");

        PathPlannerAuto Auto;

        var cmd = start_neutral_R1.isEmpty()
                || neutral_home_R2.isEmpty()
                || home_trench_R3.isEmpty()
                || trench_neutral_R4.isEmpty()
                || neutral_trench_R5.isEmpty()
                || trench_home_R6.isEmpty()
                ? Commands.none()
                : Commands.sequence(

        );

        Auto = new PathPlannerAuto(cmd);

        return Auto;
    }

}
