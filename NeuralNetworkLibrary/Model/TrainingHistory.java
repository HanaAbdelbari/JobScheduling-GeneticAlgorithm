package NeuralNetworkLibrary.Model;

import java.util.ArrayList;

/**
 * Stores training metrics (loss and accuracy) for each epoch.
 * Enables training visualization, performance analysis, and model comparison.
 */
public class TrainingHistory {
    private ArrayList<Double> lossHistory;
    private ArrayList<Double> accuracyHistory;
    private boolean trackAccuracy;

    /**
     * Constructor initializes empty history lists.
     */
    public TrainingHistory() {
        this.lossHistory = new ArrayList<>();
        this.accuracyHistory = new ArrayList<>();
        this.trackAccuracy = false;
    }

    /**
     * Add metrics for a single epoch (loss only).
     * @param loss Loss value for the epoch
     */
    public void addEpoch(double loss) {
        lossHistory.add(loss);
    }

    /**
     * Add metrics for a single epoch (loss and accuracy).
     * @param loss Loss value for the epoch
     * @param accuracy Accuracy value for the epoch
     */
    public void addEpoch(double loss, double accuracy) {
        lossHistory.add(loss);
        accuracyHistory.add(accuracy);
        trackAccuracy = true;
    }

    /**
     * Get the complete loss history.
     * @return ArrayList of loss values per epoch
     */
    public ArrayList<Double> getLossHistory() {
        return lossHistory;
    }

    /**
     * Get the complete accuracy history.
     * @return ArrayList of accuracy values per epoch (may be empty if not tracked)
     */
    public ArrayList<Double> getAccuracyHistory() {
        return accuracyHistory;
    }

    /**
     * Get the most recent loss value.
     * @return Latest loss, or 0.0 if no history exists
     */
    public double getLatestLoss() {
        if (lossHistory.isEmpty()) {
            return 0.0;
        }
        return lossHistory.get(lossHistory.size() - 1);
    }

    /**
     * Get the most recent accuracy value.
     * @return Latest accuracy, or 0.0 if no history exists
     */
    public double getLatestAccuracy() {
        if (accuracyHistory.isEmpty()) {
            return 0.0;
        }
        return accuracyHistory.get(accuracyHistory.size() - 1);
    }

    /**
     * Get the number of epochs recorded.
     * @return Number of training epochs
     */
    public int getEpochCount() {
        return lossHistory.size();
    }

    /**
     * Check if accuracy is being tracked.
     * @return true if accuracy metrics are recorded
     */
    public boolean isTrackingAccuracy() {
        return trackAccuracy;
    }

    /**
     * Print the training history in a formatted table.
     */
    public void printHistory() {
        System.out.println("\n=== Training History ===");
        System.out.println("Epoch\tLoss" + (trackAccuracy ? "\t\tAccuracy" : ""));
        System.out.println("-----\t----" + (trackAccuracy ? "\t\t--------" : ""));

        for (int i = 0; i < lossHistory.size(); i++) {
            System.out.printf("%d\t%.6f", i + 1, lossHistory.get(i));
            if (trackAccuracy && i < accuracyHistory.size()) {
                System.out.printf("\t%.4f%%", accuracyHistory.get(i) * 100);
            }
            System.out.println();
        }
        System.out.println("=======================\n");
    }

    /**
     * Get a summary of the training progress.
     * @return String summary of first and last epoch metrics
     */
    public String getSummary() {
        if (lossHistory.isEmpty()) {
            return "No training history available.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Training Summary (%d epochs):\n", lossHistory.size()));
        sb.append(String.format("  Initial Loss: %.6f\n", lossHistory.get(0)));
        sb.append(String.format("  Final Loss:   %.6f\n", getLatestLoss()));

        if (trackAccuracy && !accuracyHistory.isEmpty()) {
            sb.append(String.format("  Initial Acc:  %.4f%%\n", accuracyHistory.get(0) * 100));
            sb.append(String.format("  Final Acc:    %.4f%%\n", getLatestAccuracy() * 100));
        }

        return sb.toString();
    }
}