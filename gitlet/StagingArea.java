package gitlet;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;

public class StagingArea implements Serializable {
    private static String wd = System.getProperty("user.dir") + "/";

    HashMap<String, byte[]> stagedFiles;
    HashSet<String> removedFiles;
    HashMap<String, byte[]> trackedFiles;
    HashSet<String> untrackedFiles;
    LinkedHashMap<String, Branch> branches;
    HashSet<Commit> allCommits;
    String error = "There is an untracked file in the way;"
            + " delete it or add it first.";

    public StagingArea() {
        stagedFiles = new HashMap<>();
        removedFiles = new HashSet<>();
        trackedFiles = new HashMap<>();
        branches = new LinkedHashMap<>();
        allCommits = new HashSet<>();
        untrackedFiles = new HashSet<String>(Utils.plainFilenamesIn(wd));
    }

    //add <fileName, blobs> to hash map
    public void addFile(String filename) {
        // make a file that represent the path where gitlet is running
        File file = new File(wd + filename);
        removedFiles.remove(filename);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        if (Arrays.equals(trackedFiles.get(filename), Utils.readContents(file))) {
            return;
        }
        stagedFiles.put(filename, Utils.readContents(file));
        untrackedFiles.remove(filename);
    }

    public void clearStagingArea() {
        stagedFiles = new HashMap<>();
        removedFiles = new HashSet<>();
    }

    public String[] getName() {
        String[] fileNames  = new String[stagedFiles.size()];
        int i = 0;
        for (String key : stagedFiles.keySet()) {
            fileNames[i] = key;
            i++;
        }
        return fileNames;
    }

    public int getHashofBlobs() {
        //return the hash code values of the blobs
        return stagedFiles.values().hashCode();
    }

    // Unstaging the file
    // 2 cases:
        // is file in last commit?
        // is file in the map?
    public void removeFile(String fileName) {
        File toDelete = new File(wd + fileName);
        if (!stagedFiles.containsKey(fileName) && !trackedFiles.containsKey(fileName)) {
            System.out.println("No reason to remove the file.");
            return;
        } else if (trackedFiles.containsKey(fileName)) {
            Utils.restrictedDelete(toDelete);
            stagedFiles.remove(fileName);
            removedFiles.add(fileName);
        } else {
            if (!stagedFiles.containsKey(fileName)) {
                System.out.println("File does not exist.");
                return;
            } else {
                stagedFiles.remove(fileName);
            }
        }
    }

    public HashMap<String, Branch> getBranches() {
        return branches;
    }

    public void addBranch(String branchName, Branch head) {
        if (branches.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
        } else {
            Branch newBranch = new Branch(branchName, head.ref, head.commitChain);
            newBranch.splitPoint = head.ref;
            head.splitPoint = head.ref;
            branches.put(branchName, newBranch);
        }
    }

    public void rmBranch(String branchName, Branch head) {
        if (head.name.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        } else if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        } else {
            branches.remove(branchName);
        }
    }

    public void merge(Branch head, Branch b) {
        if (!stagedFiles.isEmpty() || !removedFiles.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            return;
        }
        if (b == null) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        if (head == b) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }
        if (b.ref.equals(b.splitPoint)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        } else if (head.ref.equals(head.splitPoint)) {
            System.out.println("Current branch is fast-forwarded.");
            head.ref = b.ref;
            return;
        } else {
            Commit splitPoint = deSerializeCommit(head.splitPoint);
            Commit current = deSerializeCommit(head.ref);
            Commit branch = deSerializeCommit(b.ref);
            HashMap<String, byte[]> splitPointFiles = splitPoint.files;
            HashMap<String, byte[]> headFiles = current.files;
            HashMap<String, byte[]> branchFiles = branch.files;
            HashSet<String> allFiles = new HashSet<>();
            allFiles.addAll(headFiles.keySet());
            allFiles.addAll(branchFiles.keySet());
            if (headFiles.equals(branchFiles)) {
                System.out.println("No changes added to the commit.");
                return;
            }
            boolean conflict = false;
            for (String key: allFiles) {
                byte[] splitPointblob = splitPointFiles.get(key);
                byte[] headBlob = headFiles.get(key);
                byte[] branchBlob = branchFiles.get(key);
                if (headBlob == null && branchBlob != null) {
                    File f = new File(wd + key);
                    if (f.exists()) {
                        byte[] untracked = Utils.readContents(f);
                        if (!Arrays.equals(untracked, branchBlob)) {
                            System.out.println(error);
                            System.exit(0);
                        }
                    }
                }
                if (splitPointblob == null &&  headBlob == null && branchBlob != null) {
                    Checkout.checkout1(key, b);
                    addFile(key);
                } else if (splitPointblob != null && Arrays.equals(headBlob, splitPointblob)
                        && branchBlob == null) {
                    removeFile(key);
                    untrackedFiles.add(key);
                } else if (!Arrays.equals(splitPointblob, branchBlob)
                        && Arrays.equals(headBlob, splitPointblob)) {
                    Checkout.checkout1(key, b);
                    addFile(key);
                } else if (!Arrays.equals(headBlob, splitPointblob)
                        && Arrays.equals(splitPointblob, branchBlob)) {
                    continue;
                } else {
                    conflict = true;
                    byte[] newContents = getCombined(headBlob, branchBlob);
                    File file = new File(wd + key);
                    Utils.writeContents(file, newContents);
                }
            }
            if (conflict) {
                System.out.println("Encountered a merge conflict.");
            } else {
                head.ref = b.ref;
                head.newCommit("Merged " + head.name + " with " + b.name + ".", this);
                clearStagingArea();
            }

        }
    }

    static Commit deSerializeCommit(String ref) {
        try { // Reading the object from a file
            FileInputStream file = new FileInputStream(wd + ".gitlet/" + ref);
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

    static byte[] getCombined(byte[] headBlob, byte[] branchBlob) {
        String h = "<<<<<<< HEAD";
        String e = "=======";
        String g = ">>>>>>>";
        String contents1 = "";
        String contents2 = "";
        if (headBlob != null) {
            contents1 = new String(headBlob);
        }
        if (branchBlob != null) {
            contents2 = new String(branchBlob);
        }
        String newLine = System.lineSeparator();
        String combined = h + newLine + contents1
                + e + newLine + contents2 + g + newLine;
        byte[] newContents = combined.getBytes();
        return newContents;
    }

}
