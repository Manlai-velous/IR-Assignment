package org.example;

import org.example.tools.FileHandler;
import org.example.tools.InvertedIndexHandler;
import org.example.tools.TextProcessor;
import org.example.tools.TFIDFHandler;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            /**
             * Tools are modular so you can use them to complete part 2,
             * Can you double check on TFIDFHandler class, the output looks weird. :)
             */
            System.out.println("Starting");

            String folderPath = "src/main/resources/toRead"; // Folder containing text files
            List<String> allFileContents = FileHandler.readAllFiles(folderPath);
            System.out.println("Files read successfully!");

            // Pre-process the text (tokenize, stopword removal, and stemming)
            List<List<String>> allProcessedTokens = TextProcessor.processAll(allFileContents);
            System.out.println(" Text preprocessed successfully!");

            // Calculate TF-IDF for the documents
            TFIDFHandler tfidfCalculator = new TFIDFHandler();
            Map<String, Map<Integer, Double>> tfidfScores = tfidfCalculator.computeTFIDF(allProcessedTokens);
            System.out.println("TF-IDF calculated successfully!");

            //Build the Inverted Index
            InvertedIndexHandler invertedIndex = new InvertedIndexHandler();
            Map<String, Map<Integer, List<Integer>>> index = invertedIndex.buildInvertedIndex(allProcessedTokens);
            System.out.println(" Inverted index created successfully!");

            // Save  TF-IDF matrix and Inverted Index as a file
            FileHandler.writeFile("src/toCreate/tfidf_matrix.txt", FileHandler.createFileTFIDFMatrix(tfidfScores));
            FileHandler.writeFile("src/toCreate/inverted_index.txt", FileHandler.createFileInvertedIndex(index));
            System.out.println(" Files saved in the 'toCreate/' folder!");

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
