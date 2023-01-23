import javassist.expr.NewArray;

import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int total = 0;
        if (L.size() > 0) {
            for (int elem : L) {
                total += elem;
            }
            return total;
        }
        /* List is empty. */
        return 0;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> out = new ArrayList<>();
        for (int elem : L) {
            if (elem % 2 == 0) {
                out.add(elem);
            }
        }
        if (out.size() > 0) {
            return out;
        }
        return null;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> out= new ArrayList<>();
        for (int elem1 : L1) {
            if (L2.contains(elem1) && !out.contains(elem1)) {
                out.add(elem1);
            }
        }
        if (out.size() > 0) {
            return out;
        }
        return null;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String elem : words) {
            for (int i = 0; i < elem.length(); i++) {
                if (elem.charAt(i) == c) {
                    count++;
                }
            }
        }
        return count;
    }
}
