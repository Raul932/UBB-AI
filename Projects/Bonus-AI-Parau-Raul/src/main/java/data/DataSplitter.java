package data;

import utils.Triple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataSplitter<F, L> {
    public Triple<List<Instance<F, L>>, List<Instance<F, L>>, List<Instance<F, L>>> split(
            List<Instance<F, L>> instances, double trainRatio, double validationRatio) {

        if (trainRatio + validationRatio >= 1.0) {
            throw new IllegalArgumentException("Train and validation ratios must sum to less than 1.0");
        }

        Collections.shuffle(instances);
        int totalSize = instances.size();
        int trainSize = (int) (totalSize * trainRatio);
        int validationSize = (int) (totalSize * validationRatio);

        List<Instance<F, L>> trainingSet = new ArrayList<>(instances.subList(0, trainSize));
        List<Instance<F, L>> validationSet = new ArrayList<>(instances.subList(trainSize, trainSize + validationSize));
        List<Instance<F, L>> testSet = new ArrayList<>(instances.subList(trainSize + validationSize, totalSize));

        return new Triple<>(trainingSet, validationSet, testSet);
    }
}
