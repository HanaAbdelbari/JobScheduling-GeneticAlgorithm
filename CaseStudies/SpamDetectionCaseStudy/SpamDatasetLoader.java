package CaseStudies.SpamDetectionCaseStudy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SpamDatasetLoader {

    public static SpamRawDataset load(String path) {

        List<String> texts = new ArrayList<>();
        List<double[]> labels = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length != 2) continue;

                texts.add(parts[1]);
                labels.add(new double[]{
                        parts[0].trim().equalsIgnoreCase("spam") ? 1.0 : 0.0
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load dataset", e);
        }

        return new SpamRawDataset(
                texts.toArray(new String[0]),
                labels.toArray(new double[0][])
        );
    }
}
