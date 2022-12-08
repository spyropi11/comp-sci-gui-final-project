package edu.vanier.template.save;

import edu.vanier.template.drumshapes.ConstructFormable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveHandler {
    
    private static final String saveDirectory = System.getProperty("user.home") + "\\Drum Sim\\";
    
    private Tracker tracker;
    
    private final String fileName;
    
    public SaveHandler(String pathName) {
        fileName = saveDirectory + pathName + ".ser";
    }
    
    public void createTracker(ConstructFormable form) {
        tracker = new Tracker(form);
    }
    
    public void upload() throws IOException {
        try(FileOutputStream fileOut = new FileOutputStream(fileName); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(tracker);
        }
    }
    
    public void download() throws IOException, ClassNotFoundException {
        try(FileInputStream fileIn = new FileInputStream(fileName); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            tracker = (Tracker) in.readObject();
        }
    }
    
    public static void createDirectory() {
        File dir = new File(saveDirectory);
        dir.mkdir();
    }
    
    public Tracker getTracker() {
        return tracker;
    }
    
}