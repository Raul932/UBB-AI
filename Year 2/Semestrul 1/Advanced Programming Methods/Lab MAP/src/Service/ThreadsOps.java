package Service;

import javafx.scene.control.Alert;
import Domain.BirthdayCake;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadsOps {
    static public void updateCakePricesUsingExecutorService(BirthdayCakeService cakeService, Integer NUM_THREADS) throws SQLException, IOException {
        List<BirthdayCake> cakes = cakeService.getAllBirthdayCakes(); // Ensure cars is not modified concurrently
        int batchSize = (cakes.size() + NUM_THREADS - 1) / NUM_THREADS; // Handle uneven batches
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * batchSize;
            int end = Math.min(start + batchSize, cakes.size());

            tasks.add(() -> {
                for (int j = start; j < end; j++) {
                    BirthdayCake cake = cakes.get(j);
                    String cakeFlavour = cake.getFlavour();
                    if (cakeFlavour.equals("vanilla")) {
                        cake.setPrice((int) (cake.getPrice() * 1.2));
                    }
                }
                return null;
            });
        }

        try {
            executorService.invokeAll(tasks); // Run all tasks
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                    executorService.shutdownNow(); // Force shutdown if tasks didn't finish
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow(); // Force shutdown on interruption
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }
    }

    static public void updateCakePricesThreads(BirthdayCakeService cakeService, Integer NUM_THREADS) throws SQLException, IOException {
        List<BirthdayCake> cakes = cakeService.getAllBirthdayCakes(); // Ensure cars is not modified concurrently
        int batchSize = (cakes.size() + NUM_THREADS - 1) / NUM_THREADS; // Handle uneven batches

        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * batchSize;
            int end = Math.min(start + batchSize, cakes.size());

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    BirthdayCake cake = cakes.get(j);
                    String cakeFlavour = cake.getFlavour();
                    if (cakeFlavour.equals("vanilla")) {
                        cake.setPrice((int) (cake.getPrice() * 1.2));
                    }
                    }
            });
        }

        // Start all threads
        for (Thread thread : threads) {
            if (thread != null) {
                thread.start();
            }
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
        }
    }
}
