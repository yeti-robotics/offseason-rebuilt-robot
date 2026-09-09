package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.hardware.TalonFX;

public class ShooterIOTalonFX implements ShooterIO {
    public TalonFX motorOne;
    public TalonFX motorTwo;

    public ShooterIOTalonFX() {
        this.motorOne = new TalonFX(ShooterConfigs.FIRST_MOTOR_ID);
        this.motorTwo = new TalonFX(ShooterConfigs.SECOND_MOTOR_ID);
    }

        @Override
    public void updateInputs(ShooterIOInputs inputs){
            inputs.velocityFIRST_MOTOR =  motorOne.getVelocity().getValueAsDouble();
            inputs.velocitySECOND_MOTOR =  motorTwo.getVelocity().getValueAsDouble();

            inputs.voltageFIRST_MOTOR =  motorOne.getMotorVoltage().getValueAsDouble();
            inputs.voltageFIRST_MOTOR = motorTwo.getMotorVoltage().getValueAsDouble();
        }

    @Override
    public void shooterCommand(double velocity) {
    }

    }

