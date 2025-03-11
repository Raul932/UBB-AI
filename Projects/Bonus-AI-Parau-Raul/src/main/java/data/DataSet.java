package data;

import java.util.ArrayList;
import java.util.List;

public class DataSet<F, L> {
    private final List<Instance<F, L>> instances;

    public DataSet() {
        this.instances = new ArrayList<>();
    }

    public void addInstance(Instance<F, L> instance) {
        instances.add(instance);
    }

    public List<Instance<F, L>> getInstances() {
        return instances;
    }
}
