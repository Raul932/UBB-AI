package data;

import java.util.List;

/**
 * The Instance class represents a data point with features and a label.
 * @param <F> The type of the features (e.g., Double, String).
 * @param <L> The type of the label (e.g., Integer, String).
 */
public class Instance<F, L> {
    private List<F> features; // List of features
    private L label;          // Label associated with the instance

    /**
     * Constructor to initialize features and label.
     * @param features List of features for the instance.
     * @param label Label for the instance.
     */
    public Instance(List<F> features, L label) {
        this.features = features;
        this.label = label;
    }

    /**
     * Get the features of the instance.
     * @return List of features.
     */
    public List<F> getFeatures() {
        return features;
    }

    /**
     * Set new features for the instance.
     * @param features List of new features.
     */
    public void setFeatures(List<F> features) {
        this.features = features;
    }

    /**
     * Get the label of the instance.
     * @return Label of the instance.
     */
    public L getLabel() {
        return label;
    }

    /**
     * Set a new label for the instance.
     * @param label New label to set.
     */
    public void setLabel(L label) {
        this.label = label;
    }

    /**
     * String representation of the Instance object for debugging purposes.
     * @return String showing features and label.
     */
    @Override
    public String toString() {
        return "Instance{" +
                "features=" + features +
                ", label=" + label +
                '}';
    }
}
