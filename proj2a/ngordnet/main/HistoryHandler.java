package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap files;
    public HistoryHandler(NGramMap map) {
        this.files = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> words = q.words();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            TimeSeries animal = new TimeSeries();
            String w = words.get(i);
            TimeSeries frequency = files.weightHistory(w, startYear, endYear);
            if (frequency != null) {
                labels.add(w);
                for (int k : frequency.keySet()) {
                    animal.put(k, frequency.get(k));
                }
                lts.add(frequency);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
