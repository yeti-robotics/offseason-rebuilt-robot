package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
 @AutoLog
 public static class ShooterIOInputs{
     public double velocityFIRST_MOTOR = 0;
     public double velocitySECOND_MOTOR = 0;
     public double velocityTHIRD_MOTOR = 0;

     public double voltageFIRST_MOTOR = 0;
     public double voltageSECOND_MOTOR = 0;
     public double voltageTHIRD_MOTOR = 0;
 }
public default void updateInputs(ShooterIOInputs inputs){
};
 public default void shooterCommand(double velocity)

}
