package frc.robot.subsystems.rollerbed;

import static edu.wpi.first.wpilibj2.command.Commands.runEnd;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class RollerBed extends SubsystemBase {

    private RollerBedIO io;
    private RollerBedIOInputsAutoLogged inputs = new RollerBedIOInputsAutoLogged();

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Roller", inputs);
    }

    public RollerBed(RollerBedIO io) {
        this.io = io;
    }

    public Command spinRollerBed(double rps) {
        return runEnd(() -> io.spinRoller(rps), () -> io.stopMotor());
    }

    public Command useMotor(double power) {
        return runEnd(() -> io.applyPower(power), () -> io.stopMotor());
    }
}
