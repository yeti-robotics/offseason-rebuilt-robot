package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.util.sim.PhysicsSim;

public class ShooterIOTalonFX implements ShooterIO {
    public final TalonFX shooterMotor;
    public final TalonFX secondaryShooterMotor;
    public MotionMagicVelocityTorqueCurrentFOC motionMagic = new MotionMagicVelocityTorqueCurrentFOC(0);
    public DutyCycleOut dutyCycleOut = new DutyCycleOut(0);

    public ShooterIOTalonFX() {
        shooterMotor = new TalonFX(ShooterConfigs.FIRST_MOTOR_ID, Constants.CAN_S1);
        secondaryShooterMotor = new TalonFX(ShooterConfigs.SECOND_MOTOR_ID, Constants.CAN_S1);

        shooterMotor.getConfigurator().apply(ShooterConfigs.TOP_MOTOR_CONFIGS);
        secondaryShooterMotor.setControl(new Follower(ShooterConfigs.BOTTOM_MOTOR_CONFIGS, true));

        if (Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(shooterMotor);
            PhysicsSim.getInstance().addTalonFX(secondaryShooterMotor);
        }
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.velocityFIRST_MOTOR = shooterMotor.getVelocity().getValueAsDouble();
        inputs.velocitySECOND_MOTOR = secondaryShooterMotor.getVelocity().getValueAsDouble();

        inputs.voltageFIRST_MOTOR = shooterMotor.getMotorVoltage().getValueAsDouble();
        inputs.voltageSECOND_MOTOR = shooterMotor.getMotorVoltage().getValueAsDouble();
    }
    @Override
    public void shoot(double velocity){
        shooterMotor.setControl(motionMagic.withVelocity(velocity));


}
@Override
public void applyPower (double power){
   shooterMotor.set(power) ;

}
}
