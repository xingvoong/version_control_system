package gitlet;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Calendar;

public class Commit implements  Serializable {
    String commitID; // hash + .ser address for current commit
    String message; //String message
    String timestamp;
    String prevCommit; //hashcode + .ser address for previous commit
    String hash; // hashcode for commit
    HashMap<String, byte[]> files; //map of filenames to blobs


    // Initial commit
    public Commit() {
        this.message = "initial commit";
        this.hash = Utils.sha1(message);
        this.commitID = Utils.sha1("initial commit") + ".ser"; // + reference to the file
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                format(Calendar.getInstance().getTime());
        this.files = new HashMap<>();
    }

    public Commit(String message, HashMap<String, byte[]> newFiles, String ref) {
        this.message = message;
        this.prevCommit = ref;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").
                format(Calendar.getInstance().getTime());
        this.files = newFiles;
        String fileRef = message;
        int h = newFiles.values().hashCode();
        String id = Utils.sha1(message) + Integer.toString(h);
        this.hash = id;
        this.commitID = id + ".ser";
    }

    public void printCommit() {
        System.out.println("Commit " + this.hash);
        System.out.println(this.timestamp);
        System.out.println(this.message);
    }
}


