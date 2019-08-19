package gitlet;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.ArrayList;

public class Branch implements Serializable {
    String name;
    String ref;
    String splitPoint;
    private static String wd = System.getProperty("user.dir") + "/";
    ArrayList<Commit> commitChain = new ArrayList<>();

    public Branch() {
        Commit initial = new Commit();
        this.name = "master";
        this.ref = initial.commitID;
        commitChain.add(initial);
        serialize(initial, this.ref);
    }
    public Branch(String name, String ref, ArrayList<Commit> commitChain) {
        this.name = name;
        this.ref = ref;
        ArrayList<Commit> newChain = new ArrayList<>();
        for (Commit c: commitChain) {
            newChain.add(c);
        }
        this.commitChain = newChain;
    }

    public void newCommit(String message, StagingArea sa) {
        Commit prevC = (Commit) deSerialize(wd + ".gitlet/" + this.ref);
        if (prevC.message.equals("initial commit")) {
            sa.allCommits.add(prevC);
        }
        HashMap<String, byte[]> newFiles = new HashMap<>();
        if (sa.stagedFiles.isEmpty() && sa.removedFiles.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }

        for (String key : sa.stagedFiles.keySet()) {
            newFiles.put(key, sa.stagedFiles.get(key));
        }

        for (String key: prevC.files.keySet()) {
            if (!sa.removedFiles.contains(key) && !newFiles.containsKey(key)) {
                newFiles.put(key, prevC.files.get(key));
            }
        }
        for (String key: sa.removedFiles) {
            newFiles.put(key, null);
        }

        Commit newC = new Commit(message, newFiles, Main.head.ref);
        commitChain.add(0, newC);
        Main.head.ref = newC.commitID;
        String fileName = newC.commitID;
        sa.allCommits.add(newC);
        sa.trackedFiles = newC.files;
        serialize(newC, fileName);
    }

    public static void serialize(Object obj, String path) {
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(wd + ".gitlet/" + path);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(obj);
            out.close();
            file.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public static Object deSerialize(String fileName) {
        Object o = null;
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream obj = new ObjectInputStream(file);
            o = (Object) obj.readObject();
            file.close();
            obj.close();
            return o;
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

}
