import classifiers.KNNModel;
import classifiers.NaiveBayes;
import classifiers.Perceptron;
import data.DataLoader;
import data.DataSplitter;
import data.Instance;
import evaluation.Accuracy;
import evaluation.EvaluationMeasure;
import evaluation.F1Score;
import evaluation.Precision;
import evaluation.Recall;
import utils.DistanceFunction;
import utils.EuclideanDistance;
import utils.Triple;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        DataLoader<Double, Integer> dataLoader = new DataLoader<>();
        List<Instance<Double, Integer>> data = dataLoader.loadFromCSV("C:\\Users\\parau\\OneDrive\\Desktop\\Bonus AI\\Bonus-AI-Parau-Raul\\src\\main\\resources\\pima-indians-diabetes.csv", tokens -> {
            List<Double> features = Arrays.stream(tokens)
                    .limit(tokens.length - 1)
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
            Integer label = Integer.parseInt(tokens[tokens.length - 1]);
            return new Instance<>(features, label);
        });

        // Split data into training, validation, and test sets
        DataSplitter<Double, Integer> splitter = new DataSplitter<>();
        Triple<List<Instance<Double, Integer>>, List<Instance<Double, Integer>>, List<Instance<Double, Integer>>> splitData =
                splitter.split(data, 0.7, 0.15);

        List<Instance<Double, Integer>> trainingData = splitData.getFirst();
        List<Instance<Double, Integer>> validationData = splitData.getSecond();
        List<Instance<Double, Integer>> testData = splitData.getThird();

        // Train and evaluate KNN Model
        System.out.println("Evaluating KNN Model");
        evaluateKNNModel(trainingData, validationData, testData);

        // Train and evaluate Naive Bayes Model
        System.out.println("\nEvaluating Naive Bayes Model");
        evaluateNaiveBayes(trainingData, testData);

        // Train and evaluate Perceptron Model
        System.out.println("\nEvaluating Perceptron Model");
        evaluatePerceptronModel(trainingData, testData);
    }

    private static void evaluateKNNModel(List<Instance<Double, Integer>> trainingData,
                                         List<Instance<Double, Integer>> validationData,
                                         List<Instance<Double, Integer>> testData) {
        // Find optimal k using validation set
        int[] kValues = {1, 3, 5, 7, 9, 11, 13};
        double bestAccuracy = 0;
        int bestK = kValues[0];
        for (int k : kValues) {
            KNNModel<Double, Integer> knnModel = new KNNModel<>(k, new EuclideanDistance());
            knnModel.train(trainingData);
            List<Integer> predictions = knnModel.test(validationData);

            EvaluationMeasure<Integer> accuracyMeasure = new Accuracy<>();
            double accuracy = accuracyMeasure.evaluate(
                    validationData.stream().map(Instance::getLabel).collect(Collectors.toList()),
                    predictions
            );

            System.out.println("k = " + k + ", Validation Accuracy: " + accuracy);

            if (accuracy > bestAccuracy) {
                bestAccuracy = accuracy;
                bestK = k;
            }
        }

        // Train final model with best k
        KNNModel<Double, Integer> knnModel = new KNNModel<>(bestK, new EuclideanDistance());
        knnModel.train(trainingData);

        // Evaluate on test set
        List<Integer> testPredictions = knnModel.test(testData);
        evaluatePerformance(testData, testPredictions, 1);
    }

    private static void evaluateNaiveBayes(List<Instance<Double, Integer>> trainingData,
                                           List<Instance<Double, Integer>> testData) {
        NaiveBayes<Double, Integer> nbModel = new NaiveBayes<>();
        nbModel.train(trainingData);

        List<Integer> testPredictions = nbModel.test(testData);
        evaluatePerformance(testData, testPredictions, 1);
    }

    private static void evaluatePerceptronModel(List<Instance<Double, Integer>> trainingData,
                                                List<Instance<Double, Integer>> testData) {
        double learningRate = 0.01;
        int epochs = 1000;
        Perceptron<Double, Integer> perceptron = new Perceptron<>(learningRate, epochs);
        perceptron.train(trainingData);

        List<Integer> testPredictions = perceptron.test(testData);
        evaluatePerformance(testData, testPredictions, 1);
    }

    private static void evaluatePerformance(List<Instance<Double, Integer>> testData, List<Integer> predictions, int positiveLabel) {
        List<Integer> trueLabels = testData.stream().map(Instance::getLabel).collect(Collectors.toList());

        EvaluationMeasure<Integer> accuracyMeasure = new Accuracy<>();
        EvaluationMeasure<Integer> precisionMeasure = new Precision<>(positiveLabel);
        EvaluationMeasure<Integer> recallMeasure = new Recall<>(positiveLabel);
        EvaluationMeasure<Integer> f1ScoreMeasure = new F1Score<>();

        double accuracy = accuracyMeasure.evaluate(trueLabels, predictions);
        double precision = precisionMeasure.evaluate(trueLabels, predictions);
        double recall = recallMeasure.evaluate(trueLabels, predictions);
        double f1Score = f1ScoreMeasure.evaluate(trueLabels, predictions);

        System.out.println("Test Accuracy: " + accuracy);
        System.out.println("Test Precision: " + precision);
        System.out.println("Test Recall: " + recall);
        System.out.println("Test F1 Score: " + f1Score);
    }
}
