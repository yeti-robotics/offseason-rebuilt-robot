package frc.robot.commands;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
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

public class AutoCommands {
    private final CommandSwerveDrivetrain drivetrain;
    private final Hood hood;
    private final Intake intake;
    private final Linslide linslide;
    private final MiniIndexer miniIndexer;
    private final RollerBed rollerBed;
    private final Shooter shooter;
    private final Turret turret;
    private final AutoFactory autoFactory;

    public AutoCommands(
            CommandSwerveDrivetrain drivetrain,
            Hood hood,
            Intake intake,
            Linslide linslide,
            MiniIndexer miniIndexer,
            RollerBed rollerBed,
            Shooter shooter,
            Turret turret,
            AutoFactory autoFactory) {
        this.drivetrain = drivetrain;
        this.hood = hood;
        this.intake = intake;
        this.linslide = linslide;
        this.miniIndexer = miniIndexer;
        this.rollerBed = rollerBed;
        this.shooter = shooter;
        this.turret = turret;
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
        // Filler vaulues
        return shooter.shoot(0);
    }

    public Boolean trajectoryValid(AutoTrajectory trajectory) {
        return trajectory.getInitialPose().isPresent();
    }

    public Command hub_neutral_l1() {

        AutoRoutine autoRoutine = autoFactory.newRoutine("hub_neutral_l1");
        AutoTrajectory hub_neutral_l1 = autoRoutine.trajectory("hub_neutral_l1");

        autoRoutine
                .active()
                .onTrue(Commands.sequence(
                                hub_neutral_l1.resetOdometry(),
                                hub_neutral_l1.cmd()
                        .onlyIf(() -> trajectoryValid(hub_neutral_l1))));

        return autoRoutine.cmd();
    }

    public Command hub_neutral_r1() {

        AutoRoutine autoRoutine = autoFactory.newRoutine("hub_neutral_r1");
        AutoTrajectory hub_neutral_r1 = autoRoutine.trajectory("hub_neutral_r1");

        autoRoutine
                .active()
                .onTrue(Commands.sequence(
                        hub_neutral_r1.resetOdometry(),
                        hub_neutral_r1.cmd()
                                .onlyIf(() -> trajectoryValid(hub_neutral_r1))));

        return autoRoutine.cmd();
    }


}
