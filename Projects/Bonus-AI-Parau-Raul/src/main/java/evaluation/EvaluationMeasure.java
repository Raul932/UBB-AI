package evaluation;

import java.util.List;

@FunctionalInterface
public interface EvaluationMeasure<L> {
    double evaluate(List<L> trueLabels, List<L> predictedLabels);
}
