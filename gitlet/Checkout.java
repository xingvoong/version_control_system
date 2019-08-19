package gitlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Arrays;

public class Checkout {
    /* Takes the version of the file as it exists in the head commit,
    the front of the current branch, and puts it in the working directory,
    overwriting the version of the file that’s already there if there is one.
    The new version of the file is not staged.*/
    static String wd = System.getProperty("user.dir"); //working directory

    public static void checkout1(String fileName, Branch head) {
        String commitID = head.ref; //sets hash to the commitID of the last commit
        Commit lastCommit = deSerializeCommit(commitID);
        HashMap<String, byte[]> lastCommitFiles = lastCommit.files;
        File wdFile = new File(wd + "/" + fileName);
        if (!lastCommitFiles.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
        } else {
            byte[] blob = lastCommitFiles.get(fileName);
            if (blob == null && wdFile.exists()) {
                Utils.restrictedDelete(wdFile);
            } else {
                Utils.writeContents(wdFile, blob);
            }
        }
    }

    /* Takes the version of the file as it exists in the commit with the given id,
    and puts it in the working directory, overwriting the version of the file
    that’s already there if there is one. The new version of the file is not staged.*/
    public static void checkout2(String fileName, String hash, StagingArea sa) {
        if (hash.length() < 40) {
            for (Commit object : sa.allCommits) {
                if (object.hash.startsWith(hash)) {
                    hash = object.hash;
                }
            }
        }
        File check = new File(wd + "/.gitlet/" + hash + ".ser");
        if (!check.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit thisCommit = deSerializeCommit(hash + ".ser");
        HashMap<String, byte[]> thisCommitFiles = thisCommit.files;
        if (!thisCommitFiles.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        } else {
            byte[] blob = thisCommitFiles.get(fileName);
            File wdFile = new File(wd + "/" + fileName);
            if (blob == null) {
                Utils.restrictedDelete(wdFile);
            } else {
                Utils.writeContents(wdFile, blob);
            }
        }
        sa.trackedFiles = thisCommitFiles;
    }

    /* Takes all files in the commit at the head of the given branch, and puts them
    in the working directory, overwriting the versions of the files that are
    already there if they exist. Also, at the end of this command, the given branch
    will now be considered the current branch (HEAD). Any files that are tracked
    in the current branch but are not present in the checked-out branch are deleted.
    The staging area is cleared, unless the checked-out branch is the current branch */
    public static void checkout3(Branch branch, Branch head, StagingArea sa) {
        String branchID = branch.ref; //sets hash to the commitID of the last commit
        String headID = head.ref;
        Commit branchCommit = deSerializeCommit(branchID);
        Commit headCommit = deSerializeCommit(headID);
        HashMap<String, byte[]> branchFiles = branchCommit.files;
        HashMap<String, byte[]> headFiles = headCommit.files;

        for (String fileName: branchFiles.keySet()) {
            File wdFile = new File(wd + "/" + fileName);
            if (!wdFile.exists()) {
                continue;
            } else {
                byte[] wdBlob = Utils.readContents(wdFile);
                if (!headFiles.containsKey(fileName)
                        && !Arrays.equals(wdBlob, branchFiles.get(fileName))) {
                    System.out.println("There is an untracked file in the way; "
                            + "delete it or add it first.");
                    System.exit(0);
                }
            }
        }

        for (String fileName: headFiles.keySet()) {
            if (!branchFiles.containsKey(fileName)) {
                File wdFile = new File(wd + "/" + fileName);
                if (wdFile.exists()) {
                    Utils.restrictedDelete(wdFile);
                }
            }
        }
        for (String fileName: branchFiles.keySet()) {
            byte[] blob = branchFiles.get(fileName);
            File wdFile = new File(wd + "/" + fileName);
            if (blob == null) {
                Utils.restrictedDelete(wdFile);
            } else {
                Utils.writeContents(wdFile, blob);
            }
        }
        sa.trackedFiles = branchFiles;
    }

    public static void reset(String hash, StagingArea sa, Branch head) {
        if (hash.length() < 40) {
            for (Commit object : sa.allCommits) {
                if (object.hash.startsWith(hash)) {
                    hash = object.hash;
                }
            }
        }
        File check = new File(wd + "/.gitlet/" + hash + ".ser");
        if (!check.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit thisCommit = deSerializeCommit(hash + ".ser");

        for (String fileName : thisCommit.files.keySet()) {
            if (!sa.trackedFiles.containsKey(fileName)) {
                File wdFile = new File(wd + "/" + fileName);
                if (wdFile.exists()) {
                    byte[] wdBlob = Utils.readContents(wdFile);
                    if (!Arrays.equals(wdBlob,
                            thisCommit.files.get(fileName))) {
                        System.out.println("There is an untracked file in the way;"
                                + " delete it or add it first.");
                        return;
                    }
                }
            }
            Checkout.checkout2(fileName, hash, sa);
        }
    }

    public static Commit deSerializeCommit(String ref) {
        try { // Reading the object from a file
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
}
