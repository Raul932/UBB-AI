package evaluation;

import java.util.List;

public class Recall<L> implements EvaluationMeasure<L> {
    private final L positiveLabel;

    public Recall(L positiveLabel) {
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
        int fn = 0; // False Negatives

        for (int i = 0; i < trueLabels.size(); i++) {
            L trueLabel = trueLabels.get(i);
            L predictedLabel = predictedLabels.get(i);

            if (trueLabel.equals(positiveLabel)) {
                if (predictedLabel.equals(positiveLabel)) {
                    tp++; // Correct positive prediction
                } else {
                    fn++; // Positive instance predicted as negative
                }
            }
            // Else: true label is not the positive label, so we ignore it for recall
        }

        // Calculate recall
        if (tp + fn == 0) {
            return 0.0; // No actual positive instances, recall is undefined; return 0
        } else {
            return (double) tp / (tp + fn);
        }
    }
}
