package frc.robot.commands;

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

    public AutoCommands(CommandSwerveDrivetrain drivetrain, Hood hood, Intake intake, Linslide linslide, MiniIndexer miniIndexer, RollerBed rollerBed, Shooter shooter, Turret turret, Vision vision) {
        this.drivetrain = drivetrain;
        this.hood = hood;
        this.intake = intake;
        this.linslide = linslide;
        this.miniIndexer = miniIndexer;
        this.rollerBed = rollerBed;
        this.shooter = shooter;
        this.turret = turret;
        this.vision = vision;
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

    public Command autoLeft() {
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
}
