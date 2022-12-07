package edu.vanier.template.save;

import edu.vanier.template.drumshapes.Formable;
import java.io.File;
import java.io.IOException;

public class SaveEnvelope {

    private SaveDrum saveDrum;
    private Tracker tracker;
    
    private final File saveFolder;
    private static final String saveDirectory = System.getProperty("user.home") + "\\Drum Sim save folders\\";

    /**
     * Creates a SaveEnvelope that encompasses all the necessary saving mechanisms.
     * @param folderPath The name of the folder the end user chooses to save.
     */
    public SaveEnvelope(String folderPath) {
        saveFolder = new File(saveDirectory + folderPath);
    }

    /**
     * If a SaveEnvelope already exists, then this downloads the necessary saving mechanisms.
     * @throws IOException
     */
    public void download() throws IOException {
        if(!saveFolder.exists()) {
            throw new IOException("Please verify that the save folder exists in " + saveDirectory);
        }
        for(File file : saveFolder.listFiles()) {
            if(file.getName().equals("Save Drum.txt")) {
                saveDrum = new SaveDrum(file);
            }
            if(file.getName().equals("Tracker.txt")) {
                tracker = new Tracker(file);
            }
        }
        if(saveDrum == null) {
            throw new IOException(saveDirectory + "Save Drum.txt does not exist.");
        }
        if(tracker == null) {
            throw new IOException(saveDirectory + "Tracker.txt does not exist.");
        }
        saveDrum.download();
        tracker.download();
    }

    /**
     * Creates all the necessary saving mechanisms for this SaveEnvelope.
     * @throws IOException
     */
    public void create() throws IOException {
        if(!saveFolder.mkdir()) {
            throw new IOException("Save folder of the same name already exists.");
        }
        saveDrum = new SaveDrum(new File(saveFolder.getAbsolutePath() + "\\Save Drum.txt"));
        tracker = new Tracker(new File(saveFolder.getAbsoluteFile() + "\\Tracker.txt"));
    }

    /**
     * Uploads the necessary saving mechanisms for this SaveEnvelope.
     * @param formable The current formable.
     * @throws IOException
     */
    public void upload(Formable formable) throws IOException {
        saveDrum.upload(formable);
        tracker.upload();
        for(File file : saveFolder.listFiles()) {
            file.setWritable(false);
        }
    }

    public static void createDirectory() {
        File dir = new File(saveDirectory);
        dir.mkdir();
    }

    public SaveDrum getSaveDrum() {
        return saveDrum;
    }
    
    public Tracker getTracker() {
        return tracker;
    }

}
