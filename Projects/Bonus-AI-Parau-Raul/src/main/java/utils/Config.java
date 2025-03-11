package utils;

import java.io.IOException;
import java.util.Map;

public class Config {
    private String modelType;
    private Map<String, String> hyperparameters;

    public void loadConfig(String filePath) throws IOException {
        // Load properties from file
    }

    public String getModelType() {
        return modelType;
    }

    public Map<String, String> getHyperparameters() {
        return hyperparameters;
    }
}
