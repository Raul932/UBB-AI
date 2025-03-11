package classifiers;

import data.Instance;
import utils.DistanceFunction;

import java.util.*;
import java.util.stream.Collectors;

public class KNNModel<F, L> extends AbstractModel<F, L> {
    private int k;
    private List<Instance<F, L>> trainingData;
    private DistanceFunction<F> distanceFunction;

    public KNNModel(int k, DistanceFunction<F> distanceFunction) {
        this.k = k;
        this.distanceFunction = distanceFunction;
    }

    @Override
    public void train(List<Instance<F, L>> instances) {
        this.trainingData = instances;
    }

    @Override
    public List<L> test(List<Instance<F, L>> instances) {
        return instances.stream()
                .map(this::predict)
                .collect(Collectors.toList());
    }

    private L predict(Instance<F, L> testInstance) {
        // Compute distances to all training instances
        List<Neighbor<L>> neighbors = new ArrayList<>();
        for (Instance<F, L> trainInstance : trainingData) {
            double distance = distanceFunction.distance(testInstance.getFeatures(), trainInstance.getFeatures());
            neighbors.add(new Neighbor<>(trainInstance.getLabel(), distance));
        }

        // Sort neighbors by distance
        neighbors.sort(Comparator.comparingDouble(Neighbor::getDistance));

        // Get the k nearest neighbors
        List<Neighbor<L>> kNearestNeighbors = neighbors.subList(0, Math.min(k, neighbors.size()));

        // Perform majority voting
        Map<L, Long> labelCounts = kNearestNeighbors.stream()
                .collect(Collectors.groupingBy(Neighbor::getLabel, Collectors.counting()));

        // Find the label with the highest count
        return labelCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    private static class Neighbor<L> {
        private final L label;
        private final double distance;

        public Neighbor(L label, double distance) {
            this.label = label;
            this.distance = distance;
        }

        public L getLabel() {
            return label;
        }

        public double getDistance() {
            return distance;
        }
    }
}
