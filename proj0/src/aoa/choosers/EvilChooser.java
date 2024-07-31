package aoa.choosers;

import java.util.*;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in this constructor.
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordPool.size() < 1) {
            throw new IllegalStateException();
        }
        pattern = "";
        for (int i = 0; i < wordLength; i++) {
            pattern += "-";
        }
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        Map<String, List> WordFamilies = new TreeMap<>();
        List<String> PossiblePatterns = new ArrayList<>();
        for (String word : wordPool) {
            String UpdatedPattern = "";
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '-') {
                    UpdatedPattern += pattern.charAt(i);
                } else {
                    if (word.charAt(i) == letter) {
                        UpdatedPattern += letter;
                    } else {
                        UpdatedPattern += '-';
                    }
                }
            }
            if (!PossiblePatterns.contains(UpdatedPattern)) {
                PossiblePatterns.add(UpdatedPattern);
            }
        }

        for (String PossiblePattern : PossiblePatterns) {
            List<String> MatchWords = new ArrayList<>();
            for (String word : wordPool) {
                String UpdatedPattern = "";
                for (int i = 0; i < pattern.length(); i++) {
                    if (pattern.charAt(i) != '-') {
                        UpdatedPattern += pattern.charAt(i);
                    } else {
                        if (word.charAt(i) == letter) {
                            UpdatedPattern += letter;
                        } else {
                            UpdatedPattern += '-';
                        }
                    }
                }
                int count = 0;
                for (int j = 0; j < UpdatedPattern.length(); j++) {
                    if (UpdatedPattern.charAt(j) == PossiblePattern.charAt(j)) {
                        count++;
                    }
                }
                if (count == UpdatedPattern.length()) {
                    MatchWords.add(word);
                }
            }
            WordFamilies.put(PossiblePattern, MatchWords);
        }

        List<String> OrderedPossiblePatterns = new ArrayList<>();
        for (String s : WordFamilies.keySet()) {
            OrderedPossiblePatterns.add(s);
        }
        String GuessPattern = OrderedPossiblePatterns.get(0);
        int count = WordFamilies.get(GuessPattern).size();
        for (String PossiblePattern1 : WordFamilies.keySet()) {
            if (WordFamilies.get(PossiblePattern1).size() > count) {
                GuessPattern = PossiblePattern1;
                count = WordFamilies.get(GuessPattern).size();
            }
        }
        pattern = GuessPattern;
        wordPool = WordFamilies.get(GuessPattern);
        int LetterCount = 0;
        for (int k = 0; k < pattern.length(); k++) {
            if (pattern.charAt(k) == letter) {
                LetterCount++;
            }
        }

        return LetterCount;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return wordPool.get(0);
    }
}
