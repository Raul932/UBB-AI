package utils;

import java.util.List;

@FunctionalInterface
public interface DistanceFunction<F> {
    double distance(List<F> a, List<F> b);
}
