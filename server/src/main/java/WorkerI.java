import MonteCarloPi.*;
import com.zeroc.Ice.*;

import java.util.Random;

public class WorkerI implements Worker {
    @Override
    public void performCalculation(int assignedPoints, Current current) {
        int pointsInsideCircle = 0;
        Random rand = new Random();

        for (int i = 0; i < assignedPoints; i++) {
            double x = rand.nextDouble() * 2 - 1;
            double y = rand.nextDouble() * 2 - 1;

            if (x * x + y * y <= 1) {
                pointsInsideCircle++;
            }
        }

        MasterPrx master = MasterPrx.checkedCast(current.adapter.getCommunicator().stringToProxy("master:default -p 10000"));
        master.reportResult(pointsInsideCircle);
    }
}
