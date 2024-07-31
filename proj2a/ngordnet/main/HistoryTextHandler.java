package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap files;
    public HistoryTextHandler(NGramMap map) {
        this.files = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> words = q.words();

        String response = "";
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            TimeSeries frequency = files.weightHistory(w, startYear, endYear);
            if (frequency != null) {
                response += w + ": {";
                int j = 0;
                int length = frequency.keySet().size();
                for (int k : frequency.keySet()) {
                    response += Integer.toString(k);
                    response += "=";
                    response += Double.toString(frequency.get(k));
                    j += 1;
                    if (j < length) {
                        response += ", ";
                    } else {
                        response += "}\n";
                    }
                }
            }
        }

        return response;
    }
}
