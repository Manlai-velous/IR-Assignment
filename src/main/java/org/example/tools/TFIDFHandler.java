package org.example.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TFIDFHandler {

    public Map<String, Map<Integer, Double>> computeTFIDF(List<List<String>> allTokens) { // computing tf_IDf
        Map<String, Map<Integer, Double>> tfidf = new HashMap<>();
        Map<String, Double> idfScores = computeIDF(allTokens);

        for (int docId = 0; docId < allTokens.size(); docId++) {
            List<String> tokens = allTokens.get(docId);
            Map<String, Double> tf = computeTF(tokens);
            for (String term : tf.keySet()) {
                double score = tf.get(term) * idfScores.get(term);
                tfidf.computeIfAbsent(term, k -> new HashMap<>()).put(docId, score);
            }
        }

        return tfidf;
    }

    private Map<String, Double> computeIDF(List<List<String>> allTokens) {
        Map<String, Double> idfScores = new HashMap<>();
        double numDocuments = allTokens.size();
        for (List<String> tokens : allTokens) {
            Set<String> uniqueTerms = new HashSet<>(tokens); // maps for the token and their own idf score
            for (String term : uniqueTerms) {
                idfScores.put(term, idfScores.getOrDefault(term, 0.0) + 1);
            }
        }
        for (String term : idfScores.keySet()) {
            double count = idfScores.get(term);
            idfScores.put(term, Math.log(numDocuments / (1 + count)));
        }
        return idfScores;
    }

    private Map<String, Double> computeTF(List<String> tokens) {
        Map<String, Double> tf = new HashMap<>(); // maps for the token and their own idf score
        double totalTerms = tokens.size();
        for (String term : tokens) {
            tf.put(term, tf.getOrDefault(term, 0.0) + 1.0);
        }
        for (String term : tf.keySet()) {
            tf.put(term, tf.get(term) / totalTerms);
        }
        return tf;
    }
}
