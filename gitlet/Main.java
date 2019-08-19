package gitlet;
import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.LinkedHashMap;

/* Driver class, the tiny stupid version-control system.*/
public class Main implements Serializable {

    /* Usage: java gitlet.Main ARGS, where ARGS contains
       <COMMAND> <OPERAND> ....
       exp:
        java gitlet.Main commit [message]
    */

    static Branch head;
    static StagingArea sa;
    private static String saPath = "/helper/sa.ser";
    private static String headPath = "/helper/head.ser";
    static String wd = System.getProperty("user.dir") + "/";
    private static File gitletDir = new File(wd + ".gitlet");

    public static void serialize(Object obj, String path) {
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(wd + ".gitlet" + path);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(obj);
            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    static void serializeBoth() {
        serialize(sa, saPath);
        serialize(head, headPath);
    }

    public static Commit deSerializeCommit(String ref) {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(wd + "/.gitlet/" + ref);
            ObjectInputStream inFile = new ObjectInputStream(file);

            // Method for deserialization of object
            Commit toReturn = (Commit) inFile.readObject();
            file.close();
            inFile.close();
            return toReturn;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
            return null;
        }
    }

    public static void deSerializeAll() {
        try {
            // Reading the object from a file
            FileInputStream fileSa = new FileInputStream(wd + ".gitlet/helper/sa.ser");
            ObjectInputStream inSa = new ObjectInputStream(fileSa);
            FileInputStream fileHead = new FileInputStream(wd + ".gitlet/helper/head.ser");
            ObjectInputStream inHead = new ObjectInputStream(fileHead);

            // Method for deserialization of object
            sa = (StagingArea) inSa.readObject();
            head = (Branch) inHead.readObject();
            head = sa.branches.get(head.name);
            inSa.close();
            fileSa.close();
            inHead.close();
            fileHead.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    public static void init() {
        File theDir = new File(wd + ".gitlet");
        File theHelper = new File(wd + ".gitlet/helper");
        if (!theDir.exists()) {
            theDir.mkdir();
            theHelper.mkdir();
            sa = new StagingArea();
            head = new Branch();
            sa.branches.put("master", head);
            serialize(sa, saPath);
            serialize(head, headPath);
        } else {
            System.out.println("A gitlet version-control system already "
                    + "exists in the current directory.");
            System.exit(0);
        }
    }
    public static void log() {
        String commitId = head.ref;
        while (commitId != null) {
            System.out.println("===");
            Commit c = deSerializeCommit(commitId);
            c.printCommit();
            System.out.println();
            commitId = c.prevCommit;
        }
    }

    public static void globalLog() {
        HashSet<Commit> c = sa.allCommits;
        for (Commit commit: c) {
            System.out.println("===");
            commit.printCommit();
            System.out.println();
        }
    }

    public static void find(String message) {
        HashSet<Commit> allCommits = sa.allCommits;
        HashSet<Commit> findCommits = new HashSet<>();
        for (Commit c: allCommits) {
            if (c.message.equals(message)) {
                findCommits.add(c);
            }
        }

        if (findCommits.size() == 0) {
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }
        for (Commit c: findCommits) {
            System.out.println(c.hash);
        }
    }

    public static void status() {
        LinkedHashMap<String, Branch> branches = sa.branches;
        HashMap<String, byte[]> stagedFiles = sa.stagedFiles;
        HashSet<String> removedFiles = sa.removedFiles;
        HashMap<String, byte[]> trackedFiles = sa.trackedFiles;
        HashSet<String> untrackedFiles = sa.untrackedFiles;
        System.out.println("=== Branches ===");
        for (String b: branches.keySet()) {
            if (b.equals(head.name)) {
                System.out.println("*" + b);
            } else {
                System.out.println(b);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (String s: stagedFiles.keySet()) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String r: removedFiles) {
            System.out.println(r);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public static void main(String... args) {
        try {
            String command = args[0];
            if (command.equals("init")) {
                init();
            } else if (!gitletDir.exists()) {
                System.out.println("Must initialize gitlet first");
                System.exit(0);
            } else {
                deSerializeAll();
                if (command.equals("add")) {
                    sa.addFile(args[1]);
                } else if (command.equals("commit")) {
                    if (args[1].equals("")) {
                        throw new IndexOutOfBoundsException();
                    }
                    head.newCommit(args[1], sa);
                    sa.clearStagingArea();
                } else if (command.equals("rm")) {
                    sa.removeFile(args[1]);
                } else if (command.equals("log")) {
                    log();
                } else if (command.equals("global-log")) {
                    globalLog();
                } else if (command.equals("find")) {
                    find(args[1]);
                } else if (command.equals("status")) {
                    status();
                } else if (command.equals("checkout")) {
                    if (args[1].equals("--")) {
                        Checkout.checkout1(args[2], head);
                    } else if (args.length == 2) {
                        if (sa.branches.get(args[1]) == null) {
                            System.out.println("No such branch exists.");
                            System.exit(0);
                        } else if (args[1].equals(head.name)) {
                            System.out.println("No need to checkout the current branch.");
                            System.exit(0);
                        } else {
                            Checkout.checkout3(sa.branches.get(args[1]), head, sa);
                            head = sa.branches.get(args[1]);
                            sa.clearStagingArea();
                        }
                    } else if ((args.length == 4) && (args[2].equals("--"))) {
                        Checkout.checkout2(args[3], args[1], sa);
                        serializeBoth();
                    } else {
                        throw new IndexOutOfBoundsException();
                    }
                } else if (command.equals("branch")) {
                    sa.addBranch(args[1], head);
                } else if (command.equals("rm-branch")) {
                    sa.rmBranch(args[1], head);
                } else if (command.equals("reset")) {
                    Checkout.reset(args[1], sa, head);
                    head.ref = args[1] + ".ser";
                    sa.clearStagingArea();
                } else if (command.equals("merge")) {
                    Branch branch = sa.branches.get(args[1]);
                    sa.merge(head, branch);
                } else {
                    System.out.println("No command with that name exists.");
                    System.exit(0);
                }
                serializeBoth();
            }
        } catch (IndexOutOfBoundsException e) {
            int sizeOfArgs = args.length;
            if (sizeOfArgs == 0) {
                System.out.println("Please enter a command.");
            } else if (args[0].equals("add")) {
                System.out.println("Please enter a valid filename");
            } else if (args[0].equals("commit")) {
                System.out.println("Please enter a commit message.");
            } else {
                System.out.println("Incorrect operands.");
            }
            System.exit(0);
        }
    }
}
