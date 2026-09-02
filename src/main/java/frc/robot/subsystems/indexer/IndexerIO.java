package frc.robot.subsystems.indexer;
import org.littletonrobotics.junction.AutoLog;
public interface IndexerIO {

    @AutoLog
    public static class IndexerIOInputs {
        public double rollerSpeed = 0.0;
        public double motorTemp = 0.0;
    }

    public default void rollerSpin(double rps){}
    public default void  updateInputs(IndexerIOInputs inputs){}
    public default void usePower(double power){}
    public default void closeMotors(){}



}