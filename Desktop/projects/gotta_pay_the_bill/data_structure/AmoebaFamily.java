import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/* An AmoebaFamily is a tree, where nodes are Amoebas, each of which can have
   any number of children. */
public class AmoebaFamily implements Iterable<AmoebaFamily.Amoeba> {

    /* ROOT is the root amoeba of this AmoebaFamily */
    private Amoeba root = null;

    /* Creates an AmoebaFamily, where the first Amoeba's name is NAME. */
    public AmoebaFamily(String name) {
        root = new Amoeba(name, null);
    }

    /* Adds a new Amoeba with CHILDNAME to this AmoebaFamily as the youngest
       child of the Amoeba named PARENTNAME. This AmoebaFamily must contain an
       Amoeba named PARENTNAME. */
    public void addChild(String parentName, String childName) {
        if (root != null) {
            root.addChildHelper(parentName, childName);
        }
    }

    /* Prints the name of all Amoebas in this AmoebaFamily in preorder, with
       the ROOT Amoeba printed first. Each Amoeba should be indented four spaces
       more than its parent. */

    public void print() {
        if (root == null) {
            System.out.println("Empty Tree");
        } else {
            root.printHelper(0);
        }

    }

    /* Returns the length of the longest name in this AmoebaFamily. */
    public int longestNameLength() {
        if (root != null) {
            return root.longestNameLengthHelper();
        }
        return 0;
    }

    /* Returns the longest name in this AmoebaFamily. */
    public String longestName() {
        if (root != null) {
            return root.longestNameHelper();
        }
        return "Empty Tree";
    }

    /* Returns an Iterator for this AmoebaFamily. */
    public Iterator<Amoeba> iterator() {
        return new AmoebaBFSIterator();
    }

    /* Creates a new AmoebaFamily and prints it out. */
    public static void main(String[] args) {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");
        System.out.println("Here's the family:");
        /*
        AmoebaFamily.AmoebaDFSIterator f = family.new AmoebaDFSIterator();
        while (f.hasNext()) {
            System.out.println(f.next());
        }
        */

        //family.print();
        AmoebaFamily.AmoebaBFSIterator f2 = family.new AmoebaBFSIterator();
        while (f2.hasNext()) {
            System.out.println(f2.next());
        }
        AmoebaFamily testing = new AmoebaFamily("1");
        testing.addChild("1", "2");
        testing.addChild("1", "3");
        testing.print();
        AmoebaFamily.AmoebaBFSIterator f3 = testing.new AmoebaBFSIterator();
        while (f3.hasNext()) {
            System.out.println(f3.next());
        }








    }

    /* An Amoeba is a node of an AmoebaFamily. */
    public static class Amoeba {

        private String name;
        private Amoeba parent;
        private ArrayList<Amoeba> children;

        public Amoeba(String name, Amoeba parent) {
            this.name = name;
            this.parent = parent;
            this.children = new ArrayList<Amoeba>();
        }

        public String toString() {
            return name;
        }

        public Amoeba getParent() {
            return parent;
        }

        public ArrayList<Amoeba> getChildren() {
            return children;
        }

        /* Adds child with name CHILDNAME to an Amoeba with name PARENTNAME. */
        public void addChildHelper(String parentName, String childName) {
            if (name.equals(parentName)) {
                Amoeba child = new Amoeba(childName, this);
                children.add(child);
            } else {
                for (Amoeba a : children) {
                    a.addChildHelper(parentName, childName);
                }
            }
        }

        public void printHelper(int l) {
            if (this.children != null) {
                String space = "";
                for (int i = 0; i < l; i++) {
                    space = space + "    ";
                }
                System.out.println(space + this.name);
            }
            for (Amoeba c: this.children) {
                c.printHelper(l + 1);
            }
        }

        /* Returns the length of the longest name between this Amoeba and its
           children. */
        public int longestNameLengthHelper() {
            int maxLengthSeen = name.length();
            for (Amoeba a : children) {
                maxLengthSeen = Math.max(maxLengthSeen,
                                         a.longestNameLengthHelper());
            }
            return maxLengthSeen;
        }
        // return the longest name
        public String longestNameHelper() {
            String maxNameSeen = name;
            for (Amoeba c : children) {
                String childMaxName = c.longestNameHelper();
                if (maxNameSeen.length() < childMaxName.length()) {
                    maxNameSeen = childMaxName;
                }
            }
            return maxNameSeen;
        }


    }

    /* An Iterator class for the AmoebaFamily, running a DFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaDFSIterator implements Iterator<Amoeba> {
        private Stack<Amoeba> fringe = new Stack<Amoeba>();

        /* AmoebaDFSIterator constructor. Sets up all of the initial information
           for the AmoebaDFSIterator. */

        public AmoebaDFSIterator() {
            if (root != null) {
                fringe.push(root);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("tree ran out of elements");
            }
            Amoeba node = fringe.pop();
            for (Amoeba c: node.children) {
                fringe.add(c);
            }
            return node;
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* An Iterator class for the AmoebaFamily, running a BFS traversal on the
       AmoebaFamily. Complete enumeration of a family of N Amoebas should take
       O(N) operations. */
    public class AmoebaBFSIterator implements Iterator<Amoeba> {

        private Queue<Amoeba> fringe = new LinkedList<Amoeba>();



        /* AmoebaBFSIterator constructor. Sets up all of the initial information
           for the AmoebaBFSIterator. */

        public AmoebaBFSIterator() {
            if (root != null) {
                fringe.add(root);
            } else {
                throw new UnsupportedOperationException();
            }
        }


        /* Returns true if there is a next element to return. */
        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        /* Returns the next element. */
        public Amoeba next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more element in the tree");
            }
            Amoeba node = fringe.poll();
            for (Amoeba c: node.children) {
                fringe.add(c);
            }
            return node;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
