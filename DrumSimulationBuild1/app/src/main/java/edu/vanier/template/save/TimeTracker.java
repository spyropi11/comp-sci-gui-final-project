package edu.vanier.template.save;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimeTracker {
    
    private final File timeFile;
    private String time = "";
    private int initialCounter;
    
    public TimeTracker(File timeFile) {
        this.timeFile = timeFile;
    }
    
    public void trackTime(int counter, double deltaTime) {
        time += (counter - initialCounter) + "#" + deltaTime + " ";
    }
    
    public double updateTime() {
        //TODO
        return 0;
    }
    
    public void download() throws IOException {
        try(FileReader fr = new FileReader(timeFile)) {
            boolean cont = true;
            while(cont) {
                int i = fr.read();
                if(i == -1) {
                    cont = false;
                } else {
                    time += (char)i;
                }
            }
        }
    }
    
    public void upload() throws IOException {
        try(FileWriter fw = new FileWriter(timeFile)) {
            fw.append(time).flush();
        }
    }
    
    public void startCount(int counter) {
        initialCounter = counter;
    }
    
}