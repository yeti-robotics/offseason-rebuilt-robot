package frc.robot.subsystems.rollerbed;

import edu.wpi.first.wpilibj2.command.Command;
import org.littletonrobotics.junction.Logger;

import static edu.wpi.first.wpilibj2.command.Commands.run;
import static edu.wpi.first.wpilibj2.command.Commands.runEnd;

public class RollerBed {

    private RollerbedIO io;
    private RollerbedInputsAutoLogged inputs = new RollerbedInputsAutoLogged();

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Roller", inputs);
    }

    public RollerBed(RollerbedIO io) {
        this.io = io;
    }

    public Command spinRollerBed(double rps) {
        return runEnd(() -> io.spinRoller(rps),() -> io.stopMotor());
    }
    public Command applyPower (double power) {
        return runEnd(() -> io.applyPower(power),() -> io.applyPower(0));
    }
    public Command apply (double power) {
        return run(() -> io.applyPower(power));
    }
}
