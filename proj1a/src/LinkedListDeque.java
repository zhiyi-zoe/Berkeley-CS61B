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
        public Node prev;
        public T item;
        public Node next;
        public Node (Node m, T i, Node n) {
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
        Node NewFirst = new Node(sentinel, x, sentinel.next);
        Node OriginFirst = sentinel.next;
        sentinel.next = NewFirst;
        OriginFirst.prev = NewFirst;
    }

    @Override
    public void addLast(T x) {
        Node NewLast = new Node(sentinel.prev, x, sentinel);
        Node OriginLast = sentinel.prev;
        OriginLast.next = NewLast;
        sentinel.prev = NewLast;
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
        Node First = sentinel.next;
        if (First.item == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        Node First = sentinel.next;
        int result = 0;
        while (First.item != null) {
            result += 1;
            First = First.next;
        }
        return result;
    }

    @Override
    public T removeFirst() {
        Node OriginFirst = sentinel.next;
        Node NowFirst = OriginFirst.next;
        sentinel.next = NowFirst;
        NowFirst.prev = sentinel;
        OriginFirst.prev = null;
        OriginFirst.next = null;

        if (sentinel.next == null && sentinel.prev == null) {
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
        }
        return OriginFirst.item;
    }

    @Override
    public T removeLast() {
        Node OriginLast = sentinel.prev;
        Node NowLast = OriginLast.prev;
        sentinel.prev = NowLast;
        NowLast.next = sentinel;
        OriginLast.prev = null;
        OriginLast.next = null;

        if (sentinel.next == null && sentinel.prev == null) {
            sentinel.prev = sentinel;
            sentinel.next = sentinel;
        }
        return OriginLast.item;
    }

    @Override
    public T get(int index) {
        Node Now = sentinel.next;
        T result = null;
        while (index >= 0 && Now.item != null) {
            result = Now.item;
            Now = Now.next;
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
        return result;
    }
}
