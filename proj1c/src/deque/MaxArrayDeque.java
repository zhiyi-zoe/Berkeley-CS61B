package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (size == 0) {
            return null;
        }
        int maxIndex = 0;
        for (int i = 0; i < size; i++) {
            if (comparator.compare(this.get(maxIndex), this.get(i)) <= 0) {
                maxIndex = i;
            }
        }
        return this.get(maxIndex);
    }
}
