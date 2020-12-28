//
public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        // a new tree
        RBTreeNode rbt = null;
        if (r == null) {
            return null;
        } else if (r.getItemCount() == 1) {
            rbt = new RBTreeNode(true, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)),
                    buildRedBlackTree(r.getChildAt(1)));
        } else if (r.getItemCount() == 2) {
            rbt = new RBTreeNode(true, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)), null);
            rbt.right = new RBTreeNode(false, r.getItemAt(1), buildRedBlackTree(r.getChildAt(1)),
                    buildRedBlackTree(r.getChildAt(2)));
        } else {
            rbt = new RBTreeNode(true, r.getItemAt(1));
            rbt.right = new RBTreeNode(false,  r.getItemAt(2), buildRedBlackTree(r.getChildAt(2)),
                    buildRedBlackTree(r.getChildAt(3)));
            rbt.left = new RBTreeNode(false, r.getItemAt(0), buildRedBlackTree(r.getChildAt(0)),
                    buildRedBlackTree(r.getChildAt(1)));
        }
        return rbt;

    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        if (node.left == null) {
            return this.root;
        } else {
            RBTreeNode<T> newNode = new RBTreeNode(false, node.item, node.left.right, node.right);
            node = new RBTreeNode(node.isBlack, node.left.item, node.left.left, newNode);
            return node;

        }
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        if (node.right == null) {
            return this.root;
        } else {
            RBTreeNode<T> newNode = new RBTreeNode(false, node.item, node.left, node.right.left);
            node = new RBTreeNode(node.isBlack, node.right.item, newNode, node.right.right);
            return node;

        }
    }

    /* Insert ITEM into the red black tree, rotating
       it accordingly afterwards. */
    void insert(T item) {
        this.root = insertHelper(root, item);
        this.root.isBlack = true;
    }
    // I received help from Daniel Sun.  He walked me through the method.
    // We did not share codes though
    public RBTreeNode insertHelper(RBTreeNode<T> t, T item) {
        if (t == null) {
            return new RBTreeNode(false, item, null, null);
        // normal BST insertion first
        } else if (item.compareTo(t.item) < 0) {
            t.left = insertHelper(t.left, item);
        } else if (item.compareTo(t.item) > 0) {
            t.right = insertHelper(t.right, item);
        } else {
            return t;
        }
        //Go from C to A,
        //case 2C
        if (!isRed(t.left) && isRed(t.right)) {
            t = rotateLeft(t);
        }
        //case 2B
        if (isRed(t.left) && isRed(t.left.left)) {
            t = rotateRight(t);
        }
        //case 2A:
        if (isRed(t.left) && isRed(t.right)) {
            flipColors(t);
        }

        return t;
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

}