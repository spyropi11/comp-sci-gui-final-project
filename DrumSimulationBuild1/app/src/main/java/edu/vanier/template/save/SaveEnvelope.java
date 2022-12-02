package edu.vanier.template.save;

import java.io.File;
import java.io.IOException;

public class SaveEnvelope {
    
    public SaveDrum saveDrum;
    public SavedSim savedSim;
    public TimeTracker timeTracker;
    
    private final File saveFolder;
    
    public SaveEnvelope(String folderPath) throws IOException {
        saveFolder = new File(folderPath);
        for(File file : saveFolder.listFiles()) {
            if(file.getName().equals("Save Drum")) {
                saveDrum = new SaveDrum(file);
            }
            if(file.getName().equals("Saved Sim")) {
                savedSim = new SavedSim(file);
            }
            if(file.getName().equals("Time Tracker")) {
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
    }
    
}