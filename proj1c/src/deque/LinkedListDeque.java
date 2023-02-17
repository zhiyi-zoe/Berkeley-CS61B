package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {

    private Node sentinel;
    int size;
    public LinkedListDeque() {

        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

    }
    private class Node {
        private Node prev;
        private T item;
        private Node next;
        public Node(Node m, T i, Node n) {
            prev = m;
            item = i;
            next = n;
        }

    }

    public static void main(String[] args) {

        Deque<Integer> lld = new LinkedListDeque<>();

    }

    @Override
    public void addFirst(T x) {
        Node newFirst = new Node(sentinel, x, sentinel.next);
        Node originFirst = sentinel.next;
        sentinel.next = newFirst;
        originFirst.prev = newFirst;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node newLast = new Node(sentinel.prev, x, sentinel);
        Node originLast = sentinel.prev;
        originLast.next = newLast;
        sentinel.prev = newLast;
        size += 1;
    }

    @Override
    public List<T> toList() {

        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;

        while (p.item != null) {
            returnList.add(p.item);
            p = p.next;
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
        if (size > 0) {
            Node originFirst = sentinel.next;
            Node nowFirst = originFirst.next;
            sentinel.next = nowFirst;
            nowFirst.prev = sentinel;
            originFirst.prev = null;
            originFirst.next = null;
            size -= 1;
            return originFirst.item;
        } else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            Node originLast = sentinel.prev;
            Node nowLast = originLast.prev;
            sentinel.prev = nowLast;
            nowLast.next = sentinel;
            originLast.prev = null;
            originLast.next = null;
            size -= 1;
            return originLast.item;
        } else {
            return null;
        }
    }

    @Override
    public T get(int index) {
        Node now = sentinel.next;
        T result = null;
        while (index >= 0 && now.item != null) {
            result = now.item;
            now = now.next;
            index -= 1;
        }
        if (index >= 0) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public T getRecursive(int index) {
        Node update = sentinel.next;
        if (index < 0) {
            return null;
        } else {
            update = nextNode(index, update);
            if (update == null) {
                return null;
            }
            return update.item;
        }
    }

    public Node nextNode(int index, Node nowNode) {
        if (index == 0) {
            return nowNode;
        } else if (nowNode.item == null) {
            return null;
        } else {
            return nextNode(index - 1, nowNode.next);
        }
    }

    @Override
    public Iterator<T> iterator() {

        return new LinkedListIterator();
    }

    public class LinkedListIterator implements Iterator<T> {
        private Node now;
        public LinkedListIterator() {
            now = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            if (now.item != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T curr = now.item;
            now = now.next;
            return curr;
        }
    }

    boolean contains(T x) {
        for (int i = 0; i < size; i += 1) {
            if (this.get(i).equals(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkedListDeque oll) {
            // check LinkedLists are of the same size
            if (this.size != oll.size) {
                return false;
            }
            for (T x : this) {
                if (!oll.contains(x)) {
                    return false;
                }
            }
            return true;
        }

        if (other instanceof ArrayDeque oa) {
            // check LinkedLists are of the same size
            if (this.size != oa.size) {
                return false;
            }
            for (T x : this) {
                if (!oa.contains(x)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }

}
