package org.example.tools;

import java.util.*;

public class InvertedIndexHandler { // formatting for readiblity

    public Map<String, Map<Integer, List<Integer>>> buildInvertedIndex(List<List<String>> allTokens) {
        Map<String, Map<Integer, List<Integer>>> index = new HashMap<>();
        for (int docId = 0; docId < allTokens.size(); docId++) {
            List<String> tokens = allTokens.get(docId);
            for (int i = 0; i < tokens.size(); i++) {
                String term = tokens.get(i);
                index.computeIfAbsent(term, k -> new HashMap<>()) // if not already in outermap, craete an aentry
                        .computeIfAbsent(docId, k -> new ArrayList<>()) // if no document id exists in innermap, create an empty list to store the position
                        .add(i);
            }
        }
        return index; // nested map, outermap- key=term, value= map of document id and positioon, innermap key= documentid, value= where it appears in the document
    }
}
