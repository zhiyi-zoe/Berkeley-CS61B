package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private TreeMap<String, TimeSeries> words;
    private TimeSeries counts;
    private In wordsFile;
    private In countsFile;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsFile = new In(wordsFilename);
        this.countsFile = new In(countsFilename);
        this.words = new TreeMap<>();
        while (this.wordsFile.hasNextLine()) {
            String[] wordsArray = this.wordsFile.readLine().split("\t");
            String word = wordsArray[0];
            TimeSeries ts = new TimeSeries();
            if (this.words.containsKey(word)) {
                ts = this.words.get(word);
            }
            int wordYear = Integer.valueOf(wordsArray[1]);
            double occurrence = Integer.valueOf(wordsArray[2]);
            ts.put(wordYear, occurrence);
            this.words.put(word, ts);
        }

        this.counts = new TimeSeries();
        while (this.countsFile.hasNextLine()) {
            String count = this.countsFile.readLine();
            if (count.length() > 0) {
                String[] countsArray = count.split(",");
                int year = Integer.valueOf(countsArray[0]);
                Double sum = Double.valueOf(countsArray[1]);
                this.counts.put(year, sum);
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries result = new TimeSeries(this.words.get(word), startYear, endYear);
        List<Integer> year = result.years();
        List<Double> data = result.data();
        for (int i = 0; i < year.size(); i++) {
            result.put(year.get(i), data.get(i));
        }
        return result;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries result = countHistory(word, MIN_YEAR, MAX_YEAR);
        return result;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result = new TimeSeries(this.counts, MIN_YEAR, MAX_YEAR);
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries total = this.totalCountHistory();
        TimeSeries result = countHistory(word, startYear, endYear).dividedBy(total);
        return result;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> w,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        Iterator<String> wordsIter = w.iterator();
        while (wordsIter.hasNext()) {
            String word = wordsIter.next();
            if (this.words.containsKey(word)) {
                TimeSeries wordWeight = weightHistory(word, startYear, endYear);
                if (wordWeight != null) {
                    result = result.plus(wordWeight);
                }
            }
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> w) {
        return summedWeightHistory(w, MIN_YEAR, MAX_YEAR);
    }

}
