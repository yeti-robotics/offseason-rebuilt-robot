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

public class AutoCommands {
    private final CommandSwerveDrivetrain drivetrain;
    private final Intake intake;
    private final Shooter shooter;
    private final Turret turret;
    private final Linslide linslide;
    private final Hood hood;
    private final MiniIndexer miniIndexer;
    private final RollerBed rollerBed;

    public AutoCommands(
            CommandSwerveDrivetrain drivetrain,
            Intake intake,
            Shooter shooter,
            Turret turret,
            Linslide linslide,
            Hood hood,
            MiniIndexer miniIndexer,
            RollerBed rollerBed) {
        this.drivetrain = drivetrain;
        this.intake = intake;
        this.shooter = shooter;
        this.turret = turret;
        this.linslide = linslide;
        this.hood = hood;
        this.miniIndexer = miniIndexer;
        this.rollerBed = rollerBed;
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

}


