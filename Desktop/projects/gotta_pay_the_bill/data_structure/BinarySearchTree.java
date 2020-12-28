public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. */
    public BinarySearchTree() {
        //call the constructor of the Binary Tree
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        //Call the constructor with the root as an argument
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        return containsHelper(root, key);
    }

    public boolean containsHelper(TreeNode t, T key) {
        if (t == null) {
            return false;
        } else if (t.item.equals(key)) {
            return true;
        } else if (key.compareTo(t.item) < 0) {
            return containsHelper(t.left, key);
        } else {
            return containsHelper(t.right, key);
        }

    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        if (!contains(key)) {
            root = addHelper(root, key);
        }
    }
    public TreeNode addHelper(TreeNode t, T key) {
        if (t == null) {
            return new TreeNode(key);
        } else if (key.compareTo(t.item) < 0) {
            //need to reconstruct the tree
            t.left  = addHelper(t.left, key);
            return t;
        } else {
            t.right =  addHelper(t.right, key);
            return t;
        }
    }

    /* Deletes a node from the BST. */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (((Comparable<T>) curr.item).compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
