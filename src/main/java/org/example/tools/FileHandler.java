package org.example.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileHandler {

    public static String readFile(String filePath) throws IOException { //readfile -> return it as string
        try {
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {
            throw new IOException("Cant read the file: " + filePath, e);
        }
    }

    public static List<String> readAllFiles(String folderPath) throws IOException { // read both text files in the folder, return as a list of strings.
        List<String> allFileContents = new ArrayList<>();
        Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile) // avoids the error: is a directory
                .forEach(path -> {
                    try {
                        String content = Files.readString(path);
                        allFileContents.add(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return allFileContents;
    }

    public static void writeFile(String filePath, String content) throws IOException { // writes the file and directs where to write the file
        Files.write(Paths.get(filePath), content.getBytes());
    }

    public static String createFileTFIDFMatrix(Map<String, Map<Integer, Double>> tfidf) { // Saving  TFIDF the file
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Double>> entry : tfidf.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }

    public static String createFileInvertedIndex(Map<String, Map<Integer, List<Integer>>> index) { // saving the inverted index as a file
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<Integer, List<Integer>>> entry : index.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
