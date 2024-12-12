package org.example.tools;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.snowball.SnowballFilter;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextProcessor {

    private static final List<String> STOPWORDS = Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
            "has", "he", "in", "is", "it", "its", "of", "on", "that", "the",
            "to", "was", "were", "will", "with"
    );

    public static List<String> process(String text) throws Exception {
        List<String> tokens = new ArrayList<>();
        try (Analyzer analyzer = new StandardAnalyzer();
             var tokenStream = analyzer.tokenStream(null, new StringReader(text))) { //tokenize
            tokenStream.reset();
            var stemmedTokenStream = new SnowballFilter(tokenStream, "English"); // snowball algorithm for stemming
            while (stemmedTokenStream.incrementToken()) {
                String token = stemmedTokenStream.getAttribute(CharTermAttribute.class).toString(); //extract the words from the token, ignoring the stopwords
                if (!STOPWORDS.contains(token)) {
                    tokens.add(token);
                }
            }
            stemmedTokenStream.end();
        }
        return tokens; // returning a list, words that are tokenised, stemmed and stopwords removed
    }

    public static List<List<String>> processAll(List<String> texts) throws Exception {// sublists have tokens for one input text
        List<List<String>> allTokens = new ArrayList<>(); //get the tokens from both document
        for (String text : texts) {
            allTokens.add(process(text));
        }
        return allTokens;
    }
}


