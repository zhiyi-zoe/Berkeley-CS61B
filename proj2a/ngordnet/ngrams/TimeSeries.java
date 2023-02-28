package ngordnet.ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static int MIN_YEAR = 1400;
    private static int MAX_YEAR = 2100;
    private TimeSeries timeSeries;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                timeSeries.put(year, ts.get(year));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> result = new ArrayList<>();
        for (int year : this.keySet()) {
            result.add(year);
        }
        return result;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> result = new ArrayList<>();
        for (int year : this.keySet()) {
            result.add(this.get(year));
        }
        return result;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries sumTimeSeries = new TimeSeries();
        if (this.isEmpty() && ts.isEmpty()) {
            return sumTimeSeries;
        }
        for (int year : this.keySet()) {
            sumTimeSeries.put(year, this.get(year));
        }
        for (int year : ts.keySet()) {
            if (sumTimeSeries.containsKey(year)) {
                Double updatedValue = sumTimeSeries.get(year) + ts.get(year);
                sumTimeSeries.put(year, updatedValue);
            } else {
                sumTimeSeries.put(year, ts.get(year));
            }
        }
        return sumTimeSeries;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries sumTimeSeries = new TimeSeries();
        for (int year : this.keySet()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException();
            }
            Double updatedValue = this.get(year) / ts.get(year);
            sumTimeSeries.put(year, updatedValue);
        }
        return sumTimeSeries;
    }
}
