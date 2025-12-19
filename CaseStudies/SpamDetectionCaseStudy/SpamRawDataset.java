package CaseStudies.SpamDetectionCaseStudy;

public class SpamRawDataset {
    public final String[] texts;
    public final double[][] labels;

    public SpamRawDataset(String[] texts, double[][] labels) {
        this.texts = texts;
        this.labels = labels;
    }
}
