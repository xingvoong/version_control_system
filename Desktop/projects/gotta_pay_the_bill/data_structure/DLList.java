 /**
  * A DLList is a list of integers. Like SLList, it also hides the terrible
  * truth of the nakedness within, but with a few additional optimizations.
  */
public class DLList<BleepBlorp> {
    private class Node {
        public Node prev;
        public BleepBlorp item;
        public Node next;

        public Node(BleepBlorp i, Node p, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private Node sentinel;
    private int size;

    /** Creates an empty DLList. */
    public DLList() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /** Returns a DLList consisting of the given values. */
    public static <BleepBlorp> DLList of(BleepBlorp... values) {
        DLList<BleepBlorp> list = new DLList<>();
        for (BleepBlorp value : values) {
            list.addLast(value);
        }
        return list;
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    /** Adds item to the front of the list. */
    public void addFirst(BleepBlorp item) {
        Node n = new Node(item, sentinel, sentinel.next);
        n.next.prev = n;
        n.prev.next = n;
        size += 1;
    }

    /** Adds item to the back of the list. */
    public void addLast(BleepBlorp item) {
        Node n = new Node(item, sentinel.prev, sentinel);
        n.next.prev = n;
        n.prev.next = n;
        size += 1;
    }

    /** Adds item to the list at the specified index. */
    public void add(int index, BleepBlorp item) {

        if (index == 0) {
            this.addFirst(item);
            return;
        } else if (index == size()) {
            this.addLast(item);
            return;
        } else {
            Node n = new Node(item, null, sentinel.next);
            while (index > 0) {
                n.next = n.next.next;
                index--;
            }
            n.prev = n.next.prev;
            n.prev.next = n;
            n.next.prev = n;
        }
        size++;

    }

    /** Remove the first instance of item from this list. */
    public void remove(BleepBlorp item) {

        Node p = sentinel.next;
        if (sentinel.next.item.equals(item)) {

            sentinel.next.next.prev = sentinel.next.prev;
            sentinel.next = sentinel.next.next;

            //sentinel.next.prev = null;
            //sentinel.next.next = null;
            size--;
        } else {
            while (p != null && !p.item.equals(item)) {
                p = p.next;
            }
            if (p.item.equals(item)) {
                p.prev.next = p.next;
                p.next.prev = p.prev;
                size--;
            }
        }

    }

    @Override
    public String toString() {
        String result = "";
        Node p = sentinel.next;
        boolean first = true;
        while (p != sentinel) {
            if (first) {
                result += p.item.toString();
                first = false;
            } else {
                result += " " + p.item.toString();
            }
            p = p.next;
        }
        return result;
    }

    /** Returns whether this and the given list or object are equal. */
    public boolean equals(Object o) {
        DLList other = (DLList) o;
        if (size() != other.size()) {
            return false;
        }
        Node op = other.sentinel.next;
        for (Node p = sentinel.next; p != sentinel; p = p.next) {
            if (!(p.item.equals(op.item))) {
                return false;
            }
            op = op.next;
        }
        return true;
    }
}
