package evaluation;

import java.util.List;

public class Precision<L> implements EvaluationMeasure<L> {
    private final L positiveLabel;

    public Precision(L positiveLabel) {
        this.positiveLabel = positiveLabel;
    }

    @Override
    public double evaluate(List<L> trueLabels, List<L> predictedLabels) {
        // Validate inputs
        if (trueLabels == null || predictedLabels == null) {
            throw new IllegalArgumentException("Label lists cannot be null.");
        }
        if (trueLabels.size() != predictedLabels.size()) {
            throw new IllegalArgumentException("Label lists must have the same size.");
        }

        int tp = 0; // True Positives
        int fp = 0; // False Positives

        for (int i = 0; i < trueLabels.size(); i++) {
            L trueLabel = trueLabels.get(i);
            L predictedLabel = predictedLabels.get(i);

            if (predictedLabel.equals(positiveLabel)) {
                if (predictedLabel.equals(trueLabel)) {
                    tp++; // Correct positive prediction
                } else {
                    fp++; // Incorrect positive prediction
                }
            }
            // Else: predicted label is not the positive label, so we ignore it for precision
        }

        // Calculate precision
        if (tp + fp == 0) {
            return 0.0; // No positive predictions, precision is undefined; return 0
        } else {
            return (double) tp / (tp + fp);
        }
    }
}
