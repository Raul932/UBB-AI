package Threads;

import Domain.BirthdayCake;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {
    private final BirthdayCake[] array;
    private final int low;
    private final int high;
    private static final int THRESHOLD = 500; // This can be tuned based on performance

    public ParallelMergeSort(BirthdayCake[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (high - low < THRESHOLD) {
            Arrays.sort(array, low, high, Comparator.comparingInt(cake -> Integer.parseInt(cake.getId())));
        } else {
            int mid = low + (high - low) / 2;
            ParallelMergeSort left = new ParallelMergeSort(array, low, mid);
            ParallelMergeSort right = new ParallelMergeSort(array, mid, high);
            invokeAll(left, right);
            merge(low, mid, high);
        }
    }

    private void merge(int low, int mid, int high) {
        BirthdayCake[] temp = new BirthdayCake[high - low];
        int i = low, j = mid, k = 0;
        while (i < mid && j < high) {
            if (Integer.parseInt(array[i].getId()) <= Integer.parseInt(array[j].getId())) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i < mid) temp[k++] = array[i++];
        while (j < high) temp[k++] = array[j++];
        System.arraycopy(temp, 0, array, low, temp.length);
    }
}
