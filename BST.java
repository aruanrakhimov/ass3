import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right, parent;
        private int size;

        public Node(K key, V val, int size, Node parent) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.parent = parent;
        }
    }

    public static class Entry<K, V> {
        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public void put(K key, V val) {
        root = put(root, key, val, null);
    }

    private Node put(Node x, K key, V val, Node parent) {
        if (x == null) {
            return new Node(key, val, 1, parent);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val, x);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val, x);
        } else {
            x.val = val; // update value if key already exists
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x.val;
            }
        }
        return null; // key not found
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        if (x != null) {
            x.size = 1 + size(x.left) + size(x.right);
        }
        return x;
    }

    private Node min(Node x) {
        if (x == null) return null;
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    public int size() {
        return size(root);
    }

    public Iterator<Entry<K, V>> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<Entry<K, V>> {
        private Node next;

        public BSTIterator() {
            next = root;
            if (next != null) {
                while (next.left != null) {
                    next = next.left;
                }
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public Entry<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node r = next;

            if (next.right != null) {
                next = next.right;
                while (next.left != null) {
                    next = next.left;
                }
            } else {
                while (true) {
                    if (next.parent == null) {
                        next = null;
                        break;
                    }
                    if (next.parent.left == next) {
                        next = next.parent;
                        break;
                    }
                    next = next.parent;
                }
            }

            return new Entry<>(r.key, r.val);
        }
    }
}
