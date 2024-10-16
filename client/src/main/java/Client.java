import MonteCarloPi.*;
import com.zeroc.Ice.*;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            PiRequesterPrx master = PiRequesterPrx.checkedCast(communicator.stringToProxy("master:default -p 10000"));
            if (master == null) {
                throw new Error("Invalid proxy");
            }
            int totalPoints = 1000000;
            master.requestPiEstimation(totalPoints);
        }
    }
}
