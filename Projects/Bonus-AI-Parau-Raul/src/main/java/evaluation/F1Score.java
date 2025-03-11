package evaluation;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.IntStream;

public class F1Score<L> implements EvaluationMeasure<L> {
    @Override
    public double evaluate(List<L> trueLabels, List<L> predictedLabels) {
        // Validate input
        if (trueLabels == null || predictedLabels == null) {
            throw new IllegalArgumentException("Label lists cannot be null.");
        }
        if (trueLabels.size() != predictedLabels.size()) {
            throw new IllegalArgumentException("Label lists must have the same size.");
        }

        // Get the set of unique labels
        Set<L> labels = new HashSet<>(trueLabels);
        labels.addAll(predictedLabels);

        double totalF1 = 0.0;
        int labelCount = labels.size();

        // Calculate F1 Score for each label
        for (L label : labels) {
            int tp = 0; // True Positives
            int fp = 0; // False Positives
            int fn = 0; // False Negatives

            for (int i = 0; i < trueLabels.size(); i++) {
                L trueLabel = trueLabels.get(i);
                L predictedLabel = predictedLabels.get(i);

                if (label.equals(predictedLabel)) {
                    if (label.equals(trueLabel)) {
                        tp++;
                    } else {
                        fp++;
                    }
                } else {
                    if (label.equals(trueLabel)) {
                        fn++;
                    }
                }
            }

            double precision = (tp + fp) == 0 ? 0 : (double) tp / (tp + fp);
            double recall = (tp + fn) == 0 ? 0 : (double) tp / (tp + fn);
            double f1 = (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);

            totalF1 += f1;
        }

        // Return the average F1 Score (macro-averaged)
        return totalF1 / labelCount;
    }
}
