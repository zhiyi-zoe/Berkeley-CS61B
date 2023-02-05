import org.apache.bcel.generic.PUSH;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    public LinkedListDeque() {

        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

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
    }

    @Override
    public void addLast(T x) {
        Node newLast = new Node(sentinel.prev, x, sentinel);
        Node originLast = sentinel.prev;
        originLast.next = newLast;
        sentinel.prev = newLast;
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
        Node first = sentinel.next;
        return first.item == null;
    }

    @Override
    public int size() {
        Node first = sentinel.next;
        int result = 0;
        while (first.item != null) {
            result += 1;
            first = first.next;
        }
        return result;
    }

    @Override
    public T removeFirst() {
        Node originFirst = sentinel.next;
        Node nowFirst = originFirst.next;
        sentinel.next = nowFirst;
        nowFirst.prev = sentinel;
        originFirst.prev = null;
        originFirst.next = null;

        if (sentinel.next == null && sentinel.prev == null) {
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
        }
        return originFirst.item;
    }

    @Override
    public T removeLast() {
        Node originLast = sentinel.prev;
        Node nowLast = originLast.prev;
        sentinel.prev = nowLast;
        nowLast.next = sentinel;
        originLast.prev = null;
        originLast.next = null;

        if (sentinel.next == null && sentinel.prev == null) {
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
        }
        return originLast.item;
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
        /*
        Node p = sentinel;
        T result = null;
        while (index >= 0) {
            sentinel = sentinel.next;
            result = sentinel.item;
            index -= 1;
            if (result == null) {
                return null;
            }
        }
        sentinel = p;
        return result; */
        Node update = sentinel.next;
        if (index < 0) {
            return null;
        } else {
            while (index >= 0) {
                index -= 1;
                update = nextNode(index, update);
                if (update == null) {
                    return null;
                }
            }
            return update.item;
        }
    }

    public Node nextNode(int index, Node nowNode) {
        if (index == -1) {
            return nowNode;
        } else if (nowNode.item == null) {
            return null;
        } else {
            return nowNode.next;
        }
    }
}
