import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> out = new TreeMap<>();
        for (int i = 0; i < 26; i++) {
            out.put((char)('a' + i), i + 1);
        }
        return out;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> out = new TreeMap<>();
        if (nums.size() > 0) {
            for (int i = 0; i < nums.size(); i++) {
                int input = nums.get(i);
                out.put(input, (int) Math.pow(input, 2));
            }
            return out;
        }
        return null;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        if (words.size() > 0) {
            Map<String, Integer> out = new TreeMap<>();
            for (int i = 0; i < words.size(); i++) {
                String word = words.get(i);
                if (out.containsKey(word)) {
                    out.put(word, out.get(word) + 1);
                }
                else {
                    out.put(word, 1);
                }
            }
            return out;
        }
        return null;
    }
}
