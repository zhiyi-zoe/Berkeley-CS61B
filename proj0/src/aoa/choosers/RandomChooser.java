package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (words.size() < 1) {
            throw new IllegalStateException();
        }
        int numWords = words.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = words.get(randomlyChosenWordNumber);
        pattern = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            pattern += "-";
        }
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int count = 0;
        String UpdatedPattern = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                count ++;
                UpdatedPattern += letter;
            } else {
                UpdatedPattern += pattern.charAt(i);
            }
        }
        pattern = UpdatedPattern;
        return count;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
