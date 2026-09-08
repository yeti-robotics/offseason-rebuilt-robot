package frc.robot.subsystems.rollerbed;

import static edu.wpi.first.wpilibj2.command.Commands.run;
import static edu.wpi.first.wpilibj2.command.Commands.runEnd;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class RollerBed extends SubsystemBase {

    private RollerbedIO io;
    private RollerbedIOInputsAutoLogged inputs = new RollerbedIOInputsAutoLogged();

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Roller", inputs);
    }

    public RollerBed(RollerbedIO io) {
        this.io = io;
    }

    public Command spinRollerBed(double rps) {
        return runEnd(() -> io.spinRoller(rps), () -> io.stopMotor());
    }

    public Command applyPower(double power) {
        return runEnd(() -> io.applyPower(power), () -> io.applyPower(0));
    }

    public Command apply(double power) {
        return run(() -> io.applyPower(power));
    }
}
