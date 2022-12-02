package edu.vanier.template.save;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vanier.template.drumshapes.Formable;
import java.io.File;
import java.io.IOException;

public class SaveEnvelope {
    
    private SaveDrum saveDrum;
    private SavedSim savedSim;
    private TimeTracker timeTracker;
    
    private final File saveFolder;
    
    /**
     * Creates a SaveEnvelope that encompasses all the necessary saving mechanisms.
     * @param folderPath The name of the folder the end user chooses to save.
     *                   Remind the end user that all save folders appear in System.getProperty("user.home") + "\\Drum Sim save folders"
     */
    public SaveEnvelope(String folderPath) {
        saveFolder = new File(folderPath);
        saveFolder.mkdir();
    }
    
    /**
     * If a SaveEnvelope already exists, then this downloads the necessary saving mechanisms.
     * @throws IOException 
     */
    public void download() throws IOException {
        for(File file : saveFolder.listFiles()) {
            if(file.getName().equals("Save Drum.txt")) {
                saveDrum = new SaveDrum(file);
            }
            if(file.getName().equals("Saved Sim.csv")) {
                savedSim = new SavedSim(file);
            }
            if(file.getName().equals("Time Tracker.txt")) {
                timeTracker = new TimeTracker(file);
            }
        }
        if(saveDrum == null) {
            throw new IOException("Save Drum does not exist.");
        }
        if(savedSim == null) {
            throw new IOException("Saved Sim does not exist.");
        }
        if(timeTracker == null) {
            throw new IOException("Time Tracker does not exist.");
        }
        saveDrum.download();
        savedSim.download();
        timeTracker.download();
    }
    
    /**
     * Creates all the necessary saving mechanisms for this SaveEnvelope.
     * @throws IOException 
     */
    public void create() throws IOException {
        saveDrum = new SaveDrum(new File(saveFolder, "Save Drum.txt"));
        savedSim = new SavedSim(new File(saveFolder, "Saved Sim.csv"));
        timeTracker = new TimeTracker(new File(saveFolder, "Time Tracker.txt"));
    }
    
    /**
     * Uploads the necessary saving mechanisms for this SaveEnvelope.
     * @param formable The current formable.
     * @throws IOException
     * @throws CsvDataTypeMismatchException
     * @throws CsvRequiredFieldEmptyException 
     */
    public void upload(Formable formable) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        saveDrum.upload(formable);
        savedSim.upload();
        timeTracker.upload();
    }
    
    public static void createDirectory() {
        File dir = new File(System.getProperty("user.home") + "\\Drum Sim save folders");
        dir.mkdir();
    }
    
    public SaveDrum getSaveDrum() {
        return saveDrum;
    }
    
    public SavedSim getSavedSim() {
        return savedSim;
    }
    
    public TimeTracker getTimeTracker() {
        return timeTracker;
    }
    
}