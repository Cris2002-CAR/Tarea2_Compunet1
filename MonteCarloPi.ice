module MonteCarloPi {
    interface PiRequester {
        void requestPiEstimation(int totalPoints);
    }

    interface Master {
        void distributeWork(int totalPoints, int numberOfWorkers);
        void reportResult(int pointsInsideCircle);
    }

    interface Worker {
        void performCalculation(int assignedPoints);
    }
}
