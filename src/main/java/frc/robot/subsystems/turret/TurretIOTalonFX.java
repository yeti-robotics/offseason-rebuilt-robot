package frc.robot.subsystems.turret;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Angle;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.subsystems.intake.IntakeConfigs;
import frc.robot.util.sim.PhysicsSim;

public class TurretIOTalonFX implements TurretIO{

    private final TalonFX turretMotor;

    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);
    private final MotionMagicTorqueCurrentFOC magicRequest = new MotionMagicTorqueCurrentFOC(0);

    public TurretIOTalonFX() {
        turretMotor = new TalonFX(TurretConfigs.TURRET_MOTOR_ID, Constants.CAN_S1);
        if (Robot.isSimulation()) {
            if (Robot.isSimulation())
                PhysicsSim.getInstance().addTalonFX(turretMotor);
        }
        turretMotor.getConfigurator().apply(TurretConfigs.TURRET_CONFIGS);
    }
    @Override
    public void applyPower(double percent){
        turretMotor.setControl(dutyCycleOut.withOutput(percent));
    }

    @Override
    public void setPosition(Angle position){
        turretMotor.setControl(magicRequest.withPosition(position));
    }
}
