import MonteCarloPi.*;
import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
        int numberOfWorkers = 5;

        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MonteCarloPiAdapter", "default -p 10000");

            MasterI master = new MasterI(1000000, numberOfWorkers);
            adapter.add(master, Util.stringToIdentity("master"));

            for (int i = 0; i < numberOfWorkers; i++) {
                WorkerI worker = new WorkerI();
                adapter.add(worker, Util.stringToIdentity("worker" + i));
            }

            adapter.activate();
            System.out.println("Servidor iniciado y en espera de solicitudes...");
            communicator.waitForShutdown();
        }
    }
}
