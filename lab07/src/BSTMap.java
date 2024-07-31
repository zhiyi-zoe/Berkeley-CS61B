import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    public BSTMap() {
    }

    private class BSTNode {
        private K item;
        private V val;         // associated data
        private BSTNode left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree
        public BSTNode(K item, V val, int size) {
            this.item = item;
            this.val = val;
            this.size = size;
        }
    }
    private BSTNode root;
    public int compareRoots(BSTMap<K, V> other) {
        /* We are able to safely invoke `compareTo` on `n1.item` because we
         * know that `K` extends `Comparable<K>`, so `K` is a `Comparable`, and
         *`Comparable`s must implement `compareTo`. */
        return this.root.item.compareTo(other.root.item);
    }
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode x, K key, V val) {
        if (x == null) {
            return new BSTNode(key, val, 1);
        }
        int cmp = key.compareTo(x.item);
        if (cmp < 0) {
            x.left  = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode root, K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.item);
        if (cmp < 0) {
            return get(root.left, key);
        } else if (cmp > 0) {
            return get(root.right, key);
        } else {
            return root.val;
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        if (this.root != null) {
            return get(key) != null || root.item == key;
        }
        return get(key) != null;
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(BSTNode x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        System.out.println(this.root.item);
    }
}
