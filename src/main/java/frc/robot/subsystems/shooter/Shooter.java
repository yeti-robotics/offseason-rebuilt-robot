package frc.robot.subsystems.shooter;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;


import static org.littletonrobotics.junction.Logger.processInputs;

//*nithya was here //*
public class Shooter extends SubsystemBase {
 private ShooterIO io;
 public ShooterIOInputsAutoLog inputs = new ShooterIOInputsAutoLog ();
 public Shooter(ShooterIO io) {
     this.io = io;
 }
 @Override
public void periodic(){
     io.updateInputs(inputs);
     Logger.processInputs("Shooter", inputs);
     Logger.recordOutput("Shooter/Target Speed", targetSpeed);
     Logger.recordOutput("Shooter/Target Is At Speed", isAtSpeed);

public AngularVelocity getVelocity(){
    return Units.RotationsPerSecond.of(inputs.topMotorRPM);


     }
public Command shoot(double velocity){
    return runOnce(() ->this.targetSpeed = velocity)

     }
}

}
