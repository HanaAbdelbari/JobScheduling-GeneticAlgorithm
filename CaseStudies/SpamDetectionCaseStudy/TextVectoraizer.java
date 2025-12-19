package CaseStudies.SpamDetectionCaseStudy;

import NeuralNetworkLibrary.Utility.Matrix;

import java.util.*;

/**
 * Simple Bag-of-Words text vectorizer.
 * Converts text documents into numerical feature vectors.
 */
public class TextVectoraizer {

    private Map<String, Integer> vocabulary;
    private int maxFeatures;
    private boolean fitted;

    public TextVectoraizer(int maxFeatures) {
        if (maxFeatures <= 0) {
            throw new IllegalArgumentException("maxFeatures must be positive.");
        }
        this.maxFeatures = maxFeatures;
        this.vocabulary = new HashMap<>();
        this.fitted = false;
    }

    /**
     * Fit the vectorizer on training data and transform it.
     */
    public Matrix fitTransform(String[] texts) {
        buildVocabulary(texts);
        return transform(texts);
    }

    /**
     * Transform text using an already-built vocabulary.
     */
    public Matrix transform(String[] texts) {
        if (!fitted) {
            throw new IllegalStateException("Vectorizer has not been fitted yet.");
        }

        double[][] features = new double[texts.length][vocabulary.size()];

        for (int i = 0; i < texts.length; i++) {
            String[] tokens = tokenize(texts[i]);

            for (String token : tokens) {
                Integer index = vocabulary.get(token);
                if (index != null) {
                    features[i][index] += 1.0; // term frequency
                }
            }
        }

        return new Matrix(features);
    }

    /**
     * Build vocabulary from training texts.
     */
    private void buildVocabulary(String[] texts) {
        Map<String, Integer> frequency = new HashMap<>();

        for (String text : texts) {
            for (String token : tokenize(text)) {
                frequency.put(token, frequency.getOrDefault(token, 0) + 1);
            }
        }

        // Sort by frequency (descending)
        List<Map.Entry<String, Integer>> sorted =
                new ArrayList<>(frequency.entrySet());

        sorted.sort((a, b) -> b.getValue() - a.getValue());

        int index = 0;
        for (Map.Entry<String, Integer> entry : sorted) {
            if (index >= maxFeatures) break;
            vocabulary.put(entry.getKey(), index);
            index++;
        }

        fitted = true;
    }

    /**
     * Basic tokenizer:
     * - lowercase
     * - remove non-letters
     * - split by spaces
     */
    private String[] tokenize(String text) {
        return text
                .toLowerCase()
                .replaceAll("[^a-z ]", "")
                .split("\\s+");
    }

    public int getVocabularySize() {
        return vocabulary.size();
    }
}
