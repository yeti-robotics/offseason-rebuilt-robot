package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Robot;
import frc.robot.util.sim.PhysicsSim;

public class ShooterIOTalonFX implements ShooterIO {
    private TalonFX motorOne;
    private TalonFX motorTwo;

    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);
    private final MotionMagicVelocityTorqueCurrentFOC magicRequest = new MotionMagicVelocityTorqueCurrentFOC(0);

    public ShooterIOTalonFX() {
        motorOne = new TalonFX(ShooterConfigs.FIRST_MOTOR_ID);
        motorTwo = new TalonFX(ShooterConfigs.SECOND_MOTOR_ID);
        if (Robot.isSimulation()) {
            PhysicsSim.getInstance().addTalonFX(motorOne);
            PhysicsSim.getInstance().addTalonFX(motorTwo);
        }
        motorOne.getConfigurator().apply(ShooterConfigs.TOP_MOTOR_CONFIGS);
        motorTwo.getConfigurator().apply(ShooterConfigs.BOTTOM_MOTOR_CONFIGS);
        motorTwo.setControl(new Follower(ShooterConfigs.FIRST_MOTOR_ID, true));
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.velocityFIRST_MOTOR = motorOne.getVelocity().getValueAsDouble();
        inputs.velocitySECOND_MOTOR = motorTwo.getVelocity().getValueAsDouble();

        inputs.voltageFIRST_MOTOR = motorOne.getMotorVoltage().getValueAsDouble();
        inputs.voltageSECOND_MOTOR = motorTwo.getMotorVoltage().getValueAsDouble();
    }

    @Override
    public void shoot(double velocity) {
        motorOne.setControl(magicRequest.withVelocity(velocity));
    }

    @Override
    public void applyPower(double power) {
        motorOne.setControl(dutyCycleOut.withOutput(power));
    }
}
