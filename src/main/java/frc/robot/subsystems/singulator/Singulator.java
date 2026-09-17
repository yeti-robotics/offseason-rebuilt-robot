package frc.robot.subsystems.singulator;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Singulator extends SubsystemBase {
 public  SingulatorIO singIO;
 private  SingulatorIOInputsAutoLogged inputs = new SingulatorIOInputsAutoLogged();

    @Override
    public void periodic() {
        singIO.updateInputs(inputs);
    }
    public Singulator(SingulatorIO io) {
        this.singIO = io;
    }
public Command spinSingulator(double rps) {
        return runEnd(() -> singIO.rollerSpin(rps), () -> singIO.closeMotors());
    }
    public Command usePower(double power) {
        return runEnd(() -> singIO.usePower(power), () -> singIO.usePower(0));
    }
    public Command use(double power) {
        return run(() -> singIO.usePower(power));
    }
}

