package Threads;

import Domain.BirthdayCake;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BirthdayCakeUpdaterExecutor {
    private final ExecutorService executorService;
    private final int numberOfThreads;

    public BirthdayCakeUpdaterExecutor(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.numberOfThreads = numberOfThreads;
    }

    public void updateCakes(List<BirthdayCake> cakes, String newFlavour) {
        int subsetSize = cakes.size() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * subsetSize;
            int end = (i == numberOfThreads - 1) ? cakes.size() : (i + 1) * subsetSize;

            List<BirthdayCake> subset = cakes.subList(start, end);
            executorService.submit(() -> {
                for (BirthdayCake cake : subset) {
                    // Update cakes with size greater than 8 to a new flavour
                    if (cake.getSize() > 8) {
                        cake.setFlavour(newFlavour);
                        System.out.println("Updated cake " + cake.getId() + " to flavour: " + newFlavour);
                    }
                }
            });
        }

        executorService.shutdown();
    }
}
