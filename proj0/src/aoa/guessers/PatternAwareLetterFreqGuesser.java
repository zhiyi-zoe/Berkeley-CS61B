package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> NewWords = new ArrayList<>();
        List<String> ImpossibleCode = new ArrayList<>();
        List<String> PossibleCode = new ArrayList<>();

        /* remove the words with different lengths. */
        for (String ContainWord : words) {
            if (ContainWord.length() == pattern.length()) {
                NewWords.add(ContainWord);
            }
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != '-') {
                char ValidLetter = pattern.charAt(i);
                for (String guess : NewWords) {
                    if (guess.charAt(i) != ValidLetter && !ImpossibleCode.contains(guess)) {
                        ImpossibleCode.add(guess);
                    }
                }
            }
        }
        for (String s : NewWords) {
            if (!ImpossibleCode.contains(s)) {
                PossibleCode.add(s);
            }
        }

        int count = 0;
        Map<Character, Integer> GuesserFrequencyMap = new TreeMap<>();
        for (String PossibleWord : PossibleCode) {
            for (int j = 0; j < PossibleWord.length(); j++) {
                char letter = PossibleWord.charAt(j);
                for (int k = 0; k < 26; k++) {
                    if (letter == (char) ('a' + k)) {
                        if (GuesserFrequencyMap.containsKey((char) ('a' + k))){
                            count = GuesserFrequencyMap.get((char) ('a' + k));
                            GuesserFrequencyMap.put((char) ('a' + k), count + 1);
                        } else {
                            GuesserFrequencyMap.put((char) ('a' + k), 1);
                        }
                    }
                }
            }
        }

        int count2, GuessCount = 0;
        List<Character> GuessList = new ArrayList<>();

        for (Character letter1 : GuesserFrequencyMap.keySet()) {
            Character GuessLetter = letter1;
            GuessCount = GuesserFrequencyMap.get(letter1);

            for (Character letter2 : GuesserFrequencyMap.keySet()) {
                if (!GuessList.contains(letter2)) {
                    count2 = GuesserFrequencyMap.get(letter2);
                    if (count2 > GuessCount) {
                        GuessLetter = letter2;
                        GuessCount = count2;
                    } else if (count2 == GuessCount && letter2 < GuessLetter) {
                        GuessLetter = letter2;
                        GuessCount = count2;
                    }
                }
            }
            if (!GuessList.contains(GuessLetter)) {
                GuessList.add(GuessLetter);
            }
        }

        if (GuessList.size() > 0) {
            for (int i = 0; i < guesses.size(); i++) {
                if (GuessList.contains(guesses.get(i))) {
                    GuessList.remove(guesses.get(i));
                }
            }
        }
        if (GuessList.size() > 0) {
            return GuessList.get(0);
        }
        return '?';
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}