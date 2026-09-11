package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {
 private final ShooterIO io;
 private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

 @Override
 public void perodic () {
     Logger.processInputs("Shooter", inputs);
 }

 public Shooter(ShooterIO io){
     this.io=io;
 }

 public Command shoot(double velocity) {
  runEnd(()-> io.shoot(velocity), ()->io.shoot(0));
 }

    public Command applyPower(double Power) {
        runEnd(()-> io.applyPower(Power), ()->io.applyPower(0));
    }


}
