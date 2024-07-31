package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;  // array of linked-list symbol tables
    // You should probably define some more!
    private static final int capacity = 16;
    private double factor = 0.75;
    private int n;               // number of key-value pairs
    private int m;               // hash table size

    /** Constructors */
    public MyHashMap() {
        this(capacity);
    }

    public MyHashMap(int initialCapacity) {
        this.n = 0;
        this.m = initialCapacity;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.n = 0;
        this.m = initialCapacity;
        this.factor = loadFactor;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {

        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {

        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private void resize(int newSize) {
        MyHashMap<K, V> temp = new MyHashMap<K, V>(newSize);
        for (int i = 0; i < m; i++) {
            Iterator bi = buckets[i].iterator();
            while (bi.hasNext()) {
                Node item = (Node) bi.next();
                temp.put(item.key, item.value);
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.buckets = temp.buckets;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (n >= factor * m) {
            resize(2 * m);
        }

        Node newItem = new Node(key, value);
        int i = Math.abs(key.hashCode()) % buckets.length;
        if (!this.containsKey(key)) {
            buckets[i].add(newItem);
            n += 1;
        } else {
            Iterator bucketI = buckets[i].iterator();
            boolean judge = false;
            while (bucketI.hasNext() && !judge) {
                Node nodeJ = (Node) bucketI.next();
                if (nodeJ.key.equals(key)) {
                    nodeJ.value = value;
                    judge = true;
                }
            }
        }
    }

    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = Math.abs(key.hashCode()) % buckets.length;
        Iterator bucketI = buckets[i].iterator();
        V va = null;
        while (bucketI.hasNext() && va == null) {
            Node nodeJ = (Node) bucketI.next();
            if (nodeJ.key.equals(key)) {
                va = nodeJ.value;
            }
        }
        return va;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument to containsKey() is null");
        boolean judge = false;
        int i = Math.abs(key.hashCode()) % buckets.length;
        Iterator bucketI = buckets[i].iterator();
        while (bucketI.hasNext() && !judge) {
            Node nodeJ = (Node) bucketI.next();
            if (nodeJ.key.equals(key)) {
                judge = true;
            }
        }
        return judge;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void clear() {
        for (int i = 0; i < m; i++) {
            this.buckets[i] = createBucket();
        }
        this.n = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

}
