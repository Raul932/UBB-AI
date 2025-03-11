package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import data.Instance;

public class DataLoader<F, L> {

    public List<Instance<F, L>> loadFromCSV(String filePath, Function<String[], Instance<F, L>> mapper) throws IOException {
        List<Instance<F, L>> instances = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header if present
            line = br.readLine(); // Uncomment if your CSV has a header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Instance<F, L> instance = mapper.apply(tokens);
                instances.add(instance);
            }
        }
        return instances;
    }
}
