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
    
    public double updateTime(int counter) {
        String count = "";
        boolean cont = true;
        while(cont) {
            char next = time.charAt(0);
            time = time.substring(1);
            if(next == '#') {
                cont = false;
            } else {
                count += next;
            }
        }
        String deltaTime = "";
        cont = true;
        while(cont) {
            char next = time.charAt(0);
            time = time.substring(1);
            if(next == ' ') {
                cont = false;
            } else {
                deltaTime += next;
            }
        }
        if(counter != Integer.parseInt(count)) {
            throw new NullPointerException("Current counter does not match with Timer Tracker's counter");
        }
        return Double.parseDouble(deltaTime);
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