package classifiers;

import data.Instance;

import java.util.*;
import java.util.stream.Collectors;

public class Perceptron<F extends Number, L> extends AbstractModel<F, L> {
    private double[] weights;
    private double learningRate;
    private int epochs;
    private Map<L, Integer> labelToNumeric;
    private Map<Integer, L> numericToLabel;

    public Perceptron(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    @Override
    public void train(List<Instance<F, L>> instances) {
        if (instances == null || instances.isEmpty()) {
            throw new IllegalArgumentException("Training data cannot be null or empty.");
        }

        // Initialize label mappings
        initializeLabelMappings(instances);

        int numFeatures = instances.get(0).getFeatures().size();
        weights = new double[numFeatures + 1]; // +1 for bias term

        // Training loop
        for (int epoch = 0; epoch < epochs; epoch++) {
            // Optionally shuffle the training data
            Collections.shuffle(instances);

            for (Instance<F, L> instance : instances) {
                // Convert features to double array and add bias term
                double[] inputs = new double[numFeatures + 1];
                inputs[0] = 1.0; // Bias input
                for (int i = 0; i < numFeatures; i++) {
                    inputs[i + 1] = instance.getFeatures().get(i).doubleValue();
                }

                int trueLabel = labelToNumeric.get(instance.getLabel());
                double weightedSum = computeWeightedSum(inputs);

                int predictedLabel = activationFunction(weightedSum);

                // Update weights if prediction is incorrect
                if (predictedLabel != trueLabel) {
                    for (int i = 0; i < weights.length; i++) {
                        weights[i] += learningRate * (trueLabel - predictedLabel) * inputs[i];
                    }
                }
            }
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
        int numFeatures = instance.getFeatures().size();
        double[] inputs = new double[numFeatures + 1];
        inputs[0] = 1.0; // Bias input
        for (int i = 0; i < numFeatures; i++) {
            inputs[i + 1] = instance.getFeatures().get(i).doubleValue();
        }

        double weightedSum = computeWeightedSum(inputs);
        int predictedLabel = activationFunction(weightedSum);
        return numericToLabel.get(predictedLabel);
    }

    private double computeWeightedSum(double[] inputs) {
        double weightedSum = 0.0;
        for (int i = 0; i < weights.length; i++) {
            weightedSum += weights[i] * inputs[i];
        }
        return weightedSum;
    }

    private int activationFunction(double weightedSum) {
        double sigmoid = 1 / (1 + Math.exp(-weightedSum));
        return sigmoid >= 0.5 ? 1 : -1;
    }

    private void initializeLabelMappings(List<Instance<F, L>> instances) {
        labelToNumeric = new HashMap<>();
        numericToLabel = new HashMap<>();

        Set<L> labels = instances.stream()
                .map(Instance::getLabel)
                .collect(Collectors.toSet());

        if (labels.size() != 2) {
            throw new IllegalArgumentException("Perceptron is a binary classifier. Found labels: " + labels);
        }

        Iterator<L> iterator = labels.iterator();
        L label1 = iterator.next();
        L label2 = iterator.next();

        labelToNumeric.put(label1, 1);
        labelToNumeric.put(label2, -1);

        numericToLabel.put(1, label1);
        numericToLabel.put(-1, label2);
    }
}
