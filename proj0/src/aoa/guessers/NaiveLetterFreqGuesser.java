package aoa.guessers;

import aoa.utils.FileUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        int count = 0;
        Map<Character, Integer> out = new TreeMap<>();
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            for (int j = 0; j < word.length(); j++) {
                char letter = word.charAt(j);
                for (int k = 0; k < 26; k++) {
                    if (letter == (char) ('a' + k)) {
                        if (out.containsKey((char) ('a' + k))){
                            count = out.get((char) ('a' + k));
                            out.put((char) ('a' + k), count + 1);
                        } else {
                            out.put((char) ('a' + k), 1);
                        }
                    }
                }
            }
        }
        return out;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.

        Map<Character, Integer> FrequencyMap = getFrequencyMap();
        int count2, GuessCount = 0;
        List<Character> GuessList = new ArrayList<>();

        for (Character letter1 : FrequencyMap.keySet()) {
            Character GuessLetter = letter1;
            GuessCount = FrequencyMap.get(letter1);

            for (Character letter2 : FrequencyMap.keySet()) {
                if (!GuessList.contains(letter2)) {
                    count2 = FrequencyMap.get(letter2);
                    if (count2 > GuessCount) {
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
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
