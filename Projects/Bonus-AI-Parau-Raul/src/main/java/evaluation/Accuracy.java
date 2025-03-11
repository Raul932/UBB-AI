package evaluation;

import java.util.List;
import java.util.stream.IntStream;

public class Accuracy<L> implements EvaluationMeasure<L> {
    @Override
    public double evaluate(List<L> trueLabels, List<L> predictedLabels) {
        long correct = IntStream.range(0, trueLabels.size())
                .filter(i -> trueLabels.get(i).equals(predictedLabels.get(i)))
                .count();
        return (double) correct / trueLabels.size();
    }
}
