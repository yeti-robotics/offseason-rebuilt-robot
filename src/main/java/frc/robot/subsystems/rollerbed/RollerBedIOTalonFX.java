package frc.robot.subsystems.rollerbed;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.constants.Constants;

public class RollerBedIOTalonFX implements RollerbedIO {
    public final TalonFX rollerBed;
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);
    private final MotionMagicVelocityTorqueCurrentFOC  velocityRequest = new MotionMagicVelocityTorqueCurrentFOC(0);

    public RollerBedIOTalonFX() {
        rollerBed = new TalonFX(RollerBedConfigs.ROLLER_BED_ID, Constants.CAN_S1);
        rollerBed.getConfigurator().apply(RollerBedConfigs.TALON_FX_CONFIGS);
           /* if (Robot.isSimulation)
                PhysicsSim.getInstance().addTalonFX(rollerBed);
    */
    }


    @Override
    public void updateInputs(RollerbedIOinputs inputs) {
        inputs.rollerSpeed = rollerBed.getVelocity().getValueAsDouble();
        inputs.motortemp = rollerBed.getDeviceTemp().getValueAsDouble();
    }
    @Override
    public void spinRoller (double rps) {
        rollerBed.setControl(velocityRequest.withVelocity(rps));
    }
    @Override
    public void applyPower(double percent) {
        rollerBed.setControl(dutyCycleOut.withOutput(percent));
    }
    @Override
    public void stopMotor() {
        rollerBed.stopMotor();
    }
}