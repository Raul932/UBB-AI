package gui;

import classifiers.KNNModel;
import classifiers.NaiveBayes;
import classifiers.Perceptron;
import data.DataLoader;
import data.DataSplitter;
import data.Instance;
import evaluation.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import utils.EuclideanDistance;
import utils.Triple;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private TextField parameterTextField;

    @FXML
    private Button loadDataButton;

    @FXML
    private Button trainButton;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Label bestModelLabel;

    @FXML
    private TreeView<String> bestModelTreeView;

    @FXML
    private Button browseButton;

    @FXML
    private TextField filePathTextField;

    @FXML
    private TextField trainPercentTextField;

    @FXML
    private TextField validationPercentTextField;

    @FXML
    private TextField testPercentTextField;

    @FXML
    private TableView<List<String>> confusionMatrixTable;

    @FXML
    private TableColumn<List<String>, String> classColumn;


    private List<Instance<Double, Integer>> data;

    private final Map<String, Double> accuracyMap = new HashMap<>();
    private final Map<String, Double> precisionMap = new HashMap<>();
    private final Map<String, Double> recallMap = new HashMap<>();
    private final Map<String, Double> f1ScoreMap = new HashMap<>();

    @FXML
    public void initialize() {
        // Initialize the model selection ComboBox
        modelComboBox.setItems(FXCollections.observableArrayList("KNN", "Naive Bayes", "Perceptron"));

        // Adjust the prompt text and enable/disable the parameterTextField based on the selected model
        modelComboBox.setOnAction(event -> {
            String selectedModel = modelComboBox.getValue();
            switch (selectedModel) {
                case "KNN":
                    parameterTextField.clear(); // Clear previous input
                    parameterTextField.setDisable(false); // Enable the field
                    parameterTextField.setPromptText("k (e.g., 3)");
                    break;
                case "Perceptron":
                    parameterTextField.clear();
                    parameterTextField.setDisable(false); // Enable the field
                    parameterTextField.setPromptText("Learning Rate (e.g., 0.01)");
                    break;
                case "Naive Bayes":
                    parameterTextField.clear();
                    parameterTextField.setDisable(true);
                    parameterTextField.setPromptText("");
                    break;
                default:
                    parameterTextField.clear();
                    parameterTextField.setDisable(true);
                    parameterTextField.setPromptText("");
                    break;
            }
        });

        // Setup confusion matrix table initial column
        classColumn.setCellValueFactory(param -> {
            // This column will just show row class labels when we have them.
            // We'll handle that after generating confusion matrix.
            return null;
        });
    }

    @FXML
    private void browseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV Data File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
        if (selectedFile != null) {
            filePathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void loadData() {
        String filePath = filePathTextField.getText().trim();
        if (filePath.isEmpty()) {
            outputTextArea.appendText("Please choose a file first.\n");
            return;
        }

        // Load the dataset
        try {
            DataLoader<Double, Integer> dataLoader = new DataLoader<>();
            data = dataLoader.loadFromCSV(filePath, tokens -> {
                List<Double> features = Arrays.stream(tokens)
                        .limit(tokens.length - 1)
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
                Integer label = Integer.parseInt(tokens[tokens.length - 1]);
                return new Instance<>(features, label);
            });
            outputTextArea.appendText("Data loaded successfully from " + filePath + ".\n");
        } catch (IOException e) {
            outputTextArea.appendText("Error loading data: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void trainModel() {
        String selectedModel = modelComboBox.getValue();
        if (selectedModel == null) {
            outputTextArea.appendText("Please select a model.\n");
            return;
        }
        if (data == null) {
            outputTextArea.appendText("Please load data first.\n");
            return;
        }

        double trainPercent, validationPercent, testPercent;
        try {
            trainPercent = Double.parseDouble(trainPercentTextField.getText().trim()) / 100.0;
            validationPercent = Double.parseDouble(validationPercentTextField.getText().trim()) / 100.0;
            testPercent = Double.parseDouble(testPercentTextField.getText().trim()) / 100.0;

            if (trainPercent + validationPercent + testPercent != 1.0) {
                outputTextArea.appendText("Train/Validation/Test percentages must sum up to 100%.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputTextArea.appendText("Please enter valid percentages.\n");
            return;
        }

        try {
            // Split data into training, validation and test sets
            DataSplitter<Double, Integer> splitter = new DataSplitter<>();
            Triple<List<Instance<Double, Integer>>, List<Instance<Double, Integer>>, List<Instance<Double, Integer>>> splitData =
                    splitter.split(data, trainPercent, validationPercent);
            List<Instance<Double, Integer>> trainingData = splitData.getFirst();
            List<Instance<Double, Integer>> testData = splitData.getSecond();
            List<Instance<Double, Integer>> validationData = splitData.getThird();

            List<Integer> predictions = null;

            if (selectedModel.equals("KNN")) {
                int k;
                try {
                    k = Integer.parseInt(parameterTextField.getText().trim());
                } catch (NumberFormatException e) {
                    outputTextArea.appendText("Please enter a valid integer for k.\n");
                    return;
                }
                KNNModel<Double, Integer> knnModel = new KNNModel<>(k, new EuclideanDistance());
                knnModel.train(trainingData);
                predictions = knnModel.test(testData);
            } else if (selectedModel.equals("Naive Bayes")) {
                NaiveBayes<Double, Integer> nbModel = new NaiveBayes<>();
                nbModel.train(trainingData);
                predictions = nbModel.test(testData);
            } else if (selectedModel.equals("Perceptron")) {
                double learningRate;
                try {
                    learningRate = Double.parseDouble(parameterTextField.getText().trim());
                } catch (NumberFormatException e) {
                    outputTextArea.appendText("Please enter a valid number for learning rate.\n");
                    return;
                }
                int epochs = 1000;
                Perceptron<Double, Integer> perceptron = new Perceptron<>(learningRate, epochs);
                perceptron.train(trainingData);
                predictions = perceptron.test(testData);
            } else {
                outputTextArea.appendText("Unknown model selected.\n");
                return;
            }

            if (predictions != null) {
                evaluatePerformance(testData, predictions, selectedModel);
            }

        } catch (Exception e) {
            outputTextArea.appendText("Error training model: " + e.getMessage() + "\n");
        }
    }

    private void evaluatePerformance(List<Instance<Double, Integer>> testData, List<Integer> predictions, String modelName) {
        List<Integer> trueLabels = testData.stream().map(Instance::getLabel).collect(Collectors.toList());
        int positiveLabel = 1; // Adjust if needed depending on dataset

        // Compute basic metrics
        EvaluationMeasure<Integer> accuracyMeasure = new Accuracy<>();
        EvaluationMeasure<Integer> precisionMeasure = new Precision<>(positiveLabel);
        EvaluationMeasure<Integer> recallMeasure = new Recall<>(positiveLabel);
        EvaluationMeasure<Integer> f1ScoreMeasure = new F1Score<>();

        double accuracy = accuracyMeasure.evaluate(trueLabels, predictions);
        double precision = precisionMeasure.evaluate(trueLabels, predictions);
        double recall = recallMeasure.evaluate(trueLabels, predictions);
        double f1Score = f1ScoreMeasure.evaluate(trueLabels, predictions);

        accuracyMap.put(modelName, accuracy);
        precisionMap.put(modelName, precision);
        recallMap.put(modelName, recall);
        f1ScoreMap.put(modelName, f1Score);

        outputTextArea.appendText("\nEvaluation Results for " + modelName + ":\n");
        outputTextArea.appendText(String.format("Accuracy: %.4f\n", accuracy));
        outputTextArea.appendText(String.format("Precision: %.4f\n", precision));
        outputTextArea.appendText(String.format("Recall: %.4f\n", recall));
        outputTextArea.appendText(String.format("F1 Score: %.4f\n", f1Score));

        // Display Confusion Matrix
        displayConfusionMatrix(trueLabels, predictions);

        updateBestModel();
    }

    private void displayConfusionMatrix(List<Integer> trueLabels, List<Integer> predictions) {
        // Identify unique classes
        Set<Integer> uniqueClasses = new HashSet<>(trueLabels);
        uniqueClasses.addAll(predictions);

        // Check if binary classification (e.g., classes 0 and 1)
        if (uniqueClasses.size() == 2 && uniqueClasses.contains(0) && uniqueClasses.contains(1)) {
            // Binary confusion matrix
            int tp = 0, fp = 0, fn = 0, tn = 0;
            for (int i = 0; i < trueLabels.size(); i++) {
                int actual = trueLabels.get(i);
                int predicted = predictions.get(i);
                if (actual == 1 && predicted == 1) {
                    tp++;
                } else if (actual == 1 && predicted == 0) {
                    fn++;
                } else if (actual == 0 && predicted == 1) {
                    fp++;
                } else if (actual == 0 && predicted == 0) {
                    tn++;
                }
            }

            // Clear old columns and set up for a 2x2 matrix
            confusionMatrixTable.getColumns().clear();

            // We'll create a table with two columns for predicted classes
            // Row headers will contain "Actual Positive" or "Actual Negative"
            // We'll show TP, FN, FP, TN in the cells

            // Define columns
            TableColumn<List<String>, String> actualLabelColumn = new TableColumn<>(" ");
            actualLabelColumn.setCellValueFactory(param ->
                    new ReadOnlyStringWrapper(param.getValue().get(0))
            );

            TableColumn<List<String>, String> predictedPositiveColumn = new TableColumn<>("Predicted Positive");
            predictedPositiveColumn.setCellValueFactory(param ->
                    new ReadOnlyStringWrapper(param.getValue().get(1))
            );

            TableColumn<List<String>, String> predictedNegativeColumn = new TableColumn<>("Predicted Negative");
            predictedNegativeColumn.setCellValueFactory(param ->
                    new ReadOnlyStringWrapper(param.getValue().get(2))
            );

            confusionMatrixTable.getColumns().addAll(actualLabelColumn, predictedPositiveColumn, predictedNegativeColumn);

            // Construct rows
            // First row: Actual Positive
            // TP in Predicted Positive cell, FN in Predicted Negative cell
            List<String> actualPositiveRow = new ArrayList<>();
            actualPositiveRow.add("Actual Positive");
            actualPositiveRow.add("TP = " + tp);
            actualPositiveRow.add("FN = " + fn);

            // Second row: Actual Negative
            // FP in Predicted Positive cell, TN in Predicted Negative cell
            List<String> actualNegativeRow = new ArrayList<>();
            actualNegativeRow.add("Actual Negative");
            actualNegativeRow.add("FP = " + fp);
            actualNegativeRow.add("TN = " + tn);

            confusionMatrixTable.setItems(FXCollections.observableArrayList(actualPositiveRow, actualNegativeRow));

        } else {
            // If not binary, revert to the generic confusion matrix display
            displayGenericConfusionMatrix(trueLabels, predictions);
        }
    }

    private void displayGenericConfusionMatrix(List<Integer> trueLabels, List<Integer> predictions) {
        // Identify unique classes
        Set<Integer> uniqueClasses = new HashSet<>(trueLabels);
        uniqueClasses.addAll(predictions);
        List<Integer> sortedClasses = uniqueClasses.stream().sorted().collect(Collectors.toList());

        int size = sortedClasses.size();
        int[][] matrix = new int[size][size];

        Map<Integer, Integer> classIndexMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            classIndexMap.put(sortedClasses.get(i), i);
        }

        for (int i = 0; i < trueLabels.size(); i++) {
            int actual = trueLabels.get(i);
            int pred = predictions.get(i);
            matrix[classIndexMap.get(actual)][classIndexMap.get(pred)]++;
        }

        confusionMatrixTable.getColumns().clear();

        TableColumn<List<String>, String> classLabelColumn = new TableColumn<>("Class");
        classLabelColumn.setCellValueFactory(param ->
                new ReadOnlyStringWrapper(param.getValue().get(0))
        );
        confusionMatrixTable.getColumns().add(classLabelColumn);

        // Add columns for predicted classes
        for (int colIndex = 0; colIndex < size; colIndex++) {
            Integer predictedClass = sortedClasses.get(colIndex);
            TableColumn<List<String>, String> col = new TableColumn<>("Pred=" + predictedClass);
            final int colIdx = colIndex + 1;
            col.setCellValueFactory(param ->
                    new ReadOnlyStringWrapper(param.getValue().get(colIdx))
            );
            confusionMatrixTable.getColumns().add(col);
        }

        List<List<String>> rows = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            List<String> row = new ArrayList<>();
            // First cell is the actual class label
            row.add("Actual=" + sortedClasses.get(rowIndex));
            // The rest are counts
            for (int colIndex = 0; colIndex < size; colIndex++) {
                row.add(String.valueOf(matrix[rowIndex][colIndex]));
            }
            rows.add(row);
        }

        confusionMatrixTable.setItems(FXCollections.observableArrayList(rows));
    }


    private void updateBestModel() {
        String bestModel = null;
        double bestF1Score = -1.0;

        for (Map.Entry<String, Double> entry : f1ScoreMap.entrySet()) {
            if (entry.getValue() > bestF1Score) {
                bestModel = entry.getKey();
                bestF1Score = entry.getValue();
            }
        }

        if (bestModel != null) {
            // Create a root node for the TreeView
            TreeItem<String> rootItem = new TreeItem<>("Best Model: " + bestModel);
            rootItem.setExpanded(true);

            // Add metrics as child nodes
            rootItem.getChildren().add(new TreeItem<>(String.format("Accuracy: %.4f", accuracyMap.get(bestModel))));
            rootItem.getChildren().add(new TreeItem<>(String.format("Precision: %.4f", precisionMap.get(bestModel))));
            rootItem.getChildren().add(new TreeItem<>(String.format("Recall: %.4f", recallMap.get(bestModel))));
            rootItem.getChildren().add(new TreeItem<>(String.format("F1 Score: %.4f", f1ScoreMap.get(bestModel))));

            // Set the TreeView root
            bestModelTreeView.setRoot(rootItem);
        } else {
            bestModelTreeView.setRoot(null);
        }
    }
}
