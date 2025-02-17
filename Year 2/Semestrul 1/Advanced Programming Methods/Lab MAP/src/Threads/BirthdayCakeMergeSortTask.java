package Threads;

import Domain.BirthdayCake;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class BirthdayCakeMergeSortTask extends RecursiveTask<List<BirthdayCake>> {
    private final List<BirthdayCake> cakes;

    public BirthdayCakeMergeSortTask(List<BirthdayCake> cakes) {
        this.cakes = cakes;
    }

    @Override
    protected List<BirthdayCake> compute() {
        if (cakes.size() <= 1) {
            return cakes;
        }

        int mid = cakes.size() / 2;
        BirthdayCakeMergeSortTask leftTask = new BirthdayCakeMergeSortTask(cakes.subList(0, mid));
        BirthdayCakeMergeSortTask rightTask = new BirthdayCakeMergeSortTask(cakes.subList(mid, cakes.size()));

        invokeAll(leftTask, rightTask);

        List<BirthdayCake> leftResult = leftTask.join();
        List<BirthdayCake> rightResult = rightTask.join();

        return merge(leftResult, rightResult);
    }

    private List<BirthdayCake> merge(List<BirthdayCake> left, List<BirthdayCake> right) {
        int leftIndex = 0, rightIndex = 0;
        List<BirthdayCake> merged = new ArrayList<>();

        while (leftIndex < left.size() && rightIndex < right.size()) {
            // Compare by price instead of id
            if (left.get(leftIndex).getPrice() <= right.get(rightIndex).getPrice()) {
                merged.add(left.get(leftIndex++));
            } else {
                merged.add(right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }
}
