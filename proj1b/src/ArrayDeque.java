import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    public ArrayDeque() {
        items = (T[]) new Object[2 * 2 * 2];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }
    private int size;
    private int nextFirst;
    private T[] items;
    private int nextLast;
    public static void main(String[] args) {

        Deque<Integer> ad = new ArrayDeque<>();

    }

    public void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (nextFirst == items.length - 1) {
            System.arraycopy(items, 0, a, 0, size);
            nextFirst = capacity - 1;
            nextLast = size;
        } else {
            System.arraycopy(items, 0, a, 0, nextLast);
            System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 + size, size - nextFirst - 1);
            nextFirst += 1;
        }
        items = a;
    }
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int first = 0;
        int returnSize = size;
        if (nextFirst != items.length - 1) {
            first = nextFirst + 1;
        }

        while (returnSize > 0) {
            returnList.add(items[first]);
            returnSize -= 1;
            if (first == items.length - 1) {
                first = 0;
            } else {
                first += 1;
            }
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {

        return size == 0;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public T removeFirst() {
        T result = null;
        if (size > 0) {
            if (nextFirst == items.length - 1) {
                result = items[0];
                items[0] = null;
                nextFirst = 0;
            } else {
                result = items[nextFirst + 1];
                items[nextFirst + 1] = null;
                nextFirst += 1;
            }
            size -= 1;
        }

        if (!usageFactor()) {
            resizingDown(size);
        }
        return result;
    }

    @Override
    public T removeLast() {
        T result = null;
        if (size > 0) {
            if (nextLast == 0) {
                result = items[items.length - 1];
                items[items.length - 1] = null;
                nextLast = items.length - 1;
            } else {
                result = items[nextLast - 1];
                items[nextLast - 1] = null;
                nextLast -= 1;
            }
            size -= 1;
        }
        if (!usageFactor()) {
            resizingDown(size);
        }
        return result;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }
        int realIndex = nextFirst + 1 + index;
        if (realIndex >= items.length) {
            realIndex -= items.length;
        }

        return items[realIndex];
    }

    public boolean usageFactor() {
        int judgeLength = 2 * 2 * 2 * 2;
        double result = ((double) size) / items.length;
        return items.length < judgeLength || result >= 0.25;
    }

    public void resizingDown(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (nextFirst == items.length - 1) {
            System.arraycopy(items, 0, a, 0, size);
            nextFirst = capacity - 1;
            nextLast = 0;
        } else if (nextFirst > nextLast && nextLast != 0) {
            System.arraycopy(items, 0, a, 0, nextLast);
            System.arraycopy(items, nextFirst + 1, a, nextLast, size - nextLast);
            nextFirst = nextLast - 1;
        } else {
            System.arraycopy(items, nextFirst + 1, a, 0, size);
            nextLast = 0;
            nextFirst = capacity - 1;
        }
        items = a;
    }
}
