import MonteCarloPi.*;
import com.zeroc.Ice.*;

import java.util.concurrent.atomic.AtomicInteger;

public class MasterI implements Master {
    private final AtomicInteger totalPointsInsideCircle = new AtomicInteger(0);
    private final int totalWorkers;
    private final int totalPoints;
    private int receivedResults = 0;

    public MasterI(int totalPoints, int totalWorkers) {
        this.totalPoints = totalPoints;
        this.totalWorkers = totalWorkers;
    }

    @Override
    public void distributeWork(int totalPoints, int numberOfWorkers, Current current) {
        int pointsPerWorker = totalPoints / numberOfWorkers;

        for (int i = 0; i < numberOfWorkers; i++) {
            WorkerPrx worker = WorkerPrx.checkedCast(current.adapter.getCommunicator().stringToProxy("worker" + i + ":default -p 10000"));
            worker.performCalculationAsync(pointsPerWorker);
        }
    }

    @Override
    public synchronized void reportResult(int pointsInsideCircle, Current current) {
        totalPointsInsideCircle.addAndGet(pointsInsideCircle);
        receivedResults++;

        if (receivedResults == totalWorkers) {
            double piEstimate = 4.0 * totalPointsInsideCircle.get() / totalPoints;
            System.out.println("Estimación de π: " + piEstimate);
        }
    }
}
