package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> SameLengthWords = new ArrayList<>();
        List<String> PossibleWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() == pattern.length()) {
                SameLengthWords.add(word);
            }
        }
        for (String word : SameLengthWords) {
            int JudgeValue = 0;
            for (int i = 0; i < word.length(); i++) {
                char letter = word.charAt(i);
                if (pattern.charAt(i) == '-' && !guesses.contains(letter)) {
                    JudgeValue++;
                } else if (pattern.charAt(i) == letter) {
                    JudgeValue++;
                }
            }
            if (JudgeValue == pattern.length()) {
                PossibleWords.add(word);
            }
        }

        int count = 0;
        Map<Character, Integer> GuesserFrequencyMap = new TreeMap<>();
        for (String PossibleWord : PossibleWords) {
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
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
