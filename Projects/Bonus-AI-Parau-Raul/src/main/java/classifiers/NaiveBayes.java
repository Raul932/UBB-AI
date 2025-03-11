package classifiers;

import data.Instance;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class NaiveBayes<F extends Number, L> extends AbstractModel<F, L> implements Serializable {
    private Set<L> classLabels;
    private Map<L, Double> classPriors;
    private Map<L, List<Double>> featureMeans;
    private Map<L, List<Double>> featureVariances;

    @Override
    public void train(List<Instance<F, L>> instances) {
        if (instances == null || instances.isEmpty()) {
            throw new IllegalArgumentException("Training data cannot be null or empty.");
        }

        int numFeatures = instances.get(0).getFeatures().size();

        // Initialize data structures
        classLabels = new HashSet<>();
        classPriors = new HashMap<>();
        featureMeans = new HashMap<>();
        featureVariances = new HashMap<>();

        // Calculate class counts
        Map<L, Integer> classCounts = new HashMap<>();
        for (Instance<F, L> instance : instances) {
            L label = instance.getLabel();
            classLabels.add(label);
            classCounts.put(label, classCounts.getOrDefault(label, 0) + 1);
        }

        int totalInstances = instances.size();

        // Calculate class priors
        for (L label : classLabels) {
            classPriors.put(label, (double) classCounts.get(label) / totalInstances);
        }

        // Calculate means and variances
        for (L label : classLabels) {
            // Filter instances by class label
            List<Instance<F, L>> classInstances = instances.stream()
                    .filter(instance -> instance.getLabel().equals(label))
                    .collect(Collectors.toList());

            // Initialize lists for means and variances
            List<Double> means = new ArrayList<>(Collections.nCopies(numFeatures, 0.0));
            List<Double> variances = new ArrayList<>(Collections.nCopies(numFeatures, 0.0));

            // Calculate means
            for (int i = 0; i < numFeatures; i++) {
                final int index = i;
                double mean = classInstances.stream()
                        .mapToDouble(instance -> instance.getFeatures().get(index).doubleValue())
                        .average()
                        .orElse(0.0);
                means.set(i, mean);
            }

            // Calculate variances
            for (int i = 0; i < numFeatures; i++) {
                final int index = i;
                double mean = means.get(i);
                double variance = classInstances.stream()
                        .mapToDouble(instance -> {
                            double diff = instance.getFeatures().get(index).doubleValue() - mean;
                            return diff * diff;
                        })
                        .average()
                        .orElse(0.0);
                variances.set(i, variance);
            }

            // Store means and variances
            featureMeans.put(label, means);
            featureVariances.put(label, variances);
        }
    }


    @Override
    public List<L> test(List<Instance<F, L>> instances) {
        if (instances == null || instances.isEmpty()) {
            throw new IllegalArgumentException("Test data cannot be null or empty.");
        }

        return instances.stream()
                .map(this::predict)
                .collect(Collectors.toList());
    }


    private L predict(Instance<F, L> instance) {
        double maxLogProb = Double.NEGATIVE_INFINITY;
        L bestLabel = null;

        for (L label : classLabels) {
            double logProb = Math.log(classPriors.get(label));

            List<Double> means = featureMeans.get(label);
            List<Double> variances = featureVariances.get(label);

            for (int i = 0; i < instance.getFeatures().size(); i++) {
                double x = instance.getFeatures().get(i).doubleValue();
                double mean = means.get(i);
                double variance = variances.get(i);

                // Avoid division by zero
                if (variance == 0) {
                    variance = 1e-6;
                }

                // Calculate Gaussian probability density function in log space
                double exponent = -((x - mean) * (x - mean)) / (2 * variance);
                double term = exponent - 0.5 * Math.log(2 * Math.PI * variance);
                logProb += term;
            }

            if (logProb > maxLogProb) {
                maxLogProb = logProb;
                bestLabel = label;
            }
        }

        return bestLabel;
    }

}
