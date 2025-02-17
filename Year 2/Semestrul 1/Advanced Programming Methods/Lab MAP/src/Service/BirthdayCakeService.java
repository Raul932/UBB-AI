package Service;

import Domain.BirthdayCake;
import Filter.AbstractFilter;
import Filter.FilterCakeByFlavour;
import Filter.FilterCakeByPrice;
import Repository.Base.FilteredRepository;
import Repository.Base.MemoryRepository;
import Threads.BirthdayCakeMergeSortTask;
import Threads.BirthdayCakeUpdaterExecutor;
import Threads.BirthdayCakeUpdaterThread;
import Threads.ParallelMergeSort;
import Validation.CakeValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class BirthdayCakeService {
    private final MemoryRepository<String, BirthdayCake> repository;
    private final CakeValidator validator = new CakeValidator();
    private ForkJoinPool forkJoinPool = new ForkJoinPool();
    public BirthdayCakeService() {
        repository = new MemoryRepository<>();
    }

    public BirthdayCakeService(MemoryRepository<String, BirthdayCake> repository) {
        this.repository = repository;
    }

    public void addBirthdayCake(int size, String flavour, int candles, double price) {
        BirthdayCake cake = new BirthdayCake(repository.getNextId(), size, flavour, candles, price);
        validator.validate(cake);
        repository.add(cake.getId(), cake);
    }

    public void removeBirthdayCake(int id) {
        repository.delete(Integer.toString(id));
    }

    public Optional<BirthdayCake> getBirthdayCakeById(int id) {
        return repository.findById(Integer.toString(id));
    }

    public void updateBirthdayCake(int id, int size, String flavour, int candles, double price) {
        BirthdayCake cake = new BirthdayCake(Integer.toString(id), size, flavour, candles, price);
        validator.validate(cake);
        repository.modify(cake.getId(), cake);
    }

    public List<BirthdayCake> getAllBirthdayCakes() {
        return repository.getAll();
    }

    public List<BirthdayCake> filterBirthdayCakesByFlavour(String flavour) {
        return getAllBirthdayCakes().stream()
                .filter(cake -> cake.getFlavour().equals(flavour))
                .collect(Collectors.toList());
    }

    public List<BirthdayCake> getSortedCakes() {
        List<BirthdayCake> cakes = repository.getAll();
        BirthdayCake[] cakesArray = cakes.toArray(new BirthdayCake[0]);

        // Create and invoke the parallel merge sort task
        ParallelMergeSort sortTask = new ParallelMergeSort(cakesArray, 0, cakesArray.length);
        forkJoinPool.invoke(sortTask);

        return Arrays.asList(cakesArray); // Convert the array back to a list
    }

    public List<BirthdayCake> filterBirthdayCakesByPrice(double price) {
        return getAllBirthdayCakes().stream()
                .filter(cake -> cake.getPrice() <= price)
                .collect(Collectors.toList());
    }

    public void increasePriceByPercent(int cakeId, double percent) {
        getBirthdayCakeById(cakeId).ifPresent(cake -> {
            double originalPrice = cake.getPrice();
            double newPrice = originalPrice * (1 + percent / 100);
            cake.setPrice(newPrice);
            updateBirthdayCake(cakeId, cake.getSize(), cake.getFlavour(), cake.getCandles(), cake.getPrice());
            System.out.println("Increased price from " + originalPrice + " to " + newPrice);
        });
    }

    public void decreasePriceByPercent(int cakeId, double percent) {
        getBirthdayCakeById(cakeId).ifPresent(cake -> {
            double originalPrice = cake.getPrice();
            double newPrice = originalPrice * (1 - percent / 100);
            cake.setPrice(newPrice);
            updateBirthdayCake(cakeId, cake.getSize(), cake.getFlavour(), cake.getCandles(), cake.getPrice());
            System.out.println("Decreased price from " + originalPrice + " to " + newPrice);
        });
    }
    public double getAverageCakePrice() {
        // Returns the Average Price of a Cake.
        return repository.getAll().stream()
                .mapToDouble(BirthdayCake::getPrice)
                .average()
                .orElse(0);
    }

    public List<BirthdayCake> filterCakesLargerThan8() {
        return repository.getAll().stream()
                .filter(x -> x.getSize() > 8)
                .toList();
    }


    // 1. Traditional Threads for Bulk Update
    public void bulkUpdateCakesTraditional(int numberOfThreads, String newFlavour) {
        List<BirthdayCake> cakes = repository.getAll();
        int subsetSize = cakes.size() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * subsetSize;
            int end = (i == numberOfThreads - 1) ? cakes.size() : (i + 1) * subsetSize;
            List<BirthdayCake> subset = cakes.subList(start, end);

            // Start a new thread for each subset
            new BirthdayCakeUpdaterThread(subset, newFlavour).start();
        }
    }

    // 2. ExecutorService for Bulk Update
    public void bulkUpdateCakesExecutor(int numberOfThreads, String newFlavour) {
        List<BirthdayCake> cakes = repository.getAll();
        BirthdayCakeUpdaterExecutor executor = new BirthdayCakeUpdaterExecutor(numberOfThreads);

        // Update cakes using ExecutorService
        executor.updateCakes(cakes, newFlavour);
    }

    // 3. Fork/Join Framework for Sorting Cakes
    public List<BirthdayCake> sortCakesByIdMultiThreaded() {
        List<BirthdayCake> cakes = repository.getAll();
        ForkJoinPool pool = new ForkJoinPool();

        // Use merge sort task to sort the cakes
        BirthdayCakeMergeSortTask task = new BirthdayCakeMergeSortTask(cakes);
        return pool.invoke(task);
    }

    public double getAverageNumberOfCandles() {
        // Returns the Average Number of Candles.
        return repository.getAll().stream()
                .mapToInt(BirthdayCake::getCandles)
                .average()
                .orElse(0);
    }
}
