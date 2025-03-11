package utils;

import java.util.List;

public class EuclideanDistance implements DistanceFunction<Double> {
    @Override
    public double distance(List<Double> a, List<Double> b) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            double diff = a.get(i) - b.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
