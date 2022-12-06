package edu.vanier.template.save;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class TimeTracker {
    
    private final File timeFile;
    private String time = "";
    private int initialCounter;
    
    private HashMap<Integer, Double> track = new HashMap<>();
    
    public TimeTracker(File timeFile) {
        this.timeFile = timeFile;
    }
    
    public void trackTime(int counter, double deltaTime) {
        time += (counter - initialCounter) + "#" + deltaTime + " ";
    }
    
    public double updateTime(int counter) {
        return track.get(counter);
    }
    
    public void download() throws IOException {
        try(FileReader fr = new FileReader(timeFile)) {
            boolean more = true;
            while(more) {
                String count = "";
                while(true) {
                    int nextInt = fr.read();
                    if(nextInt == -1) {
                        more = false;
                        break;
                    }
                    char next = (char)nextInt;
                    if(next == '#') {
                        break;
                    } else {
                        count += next;
                    }
                }
                if(more) {
                    String t = "";
                    while(true) {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            break;
                        } else {
                            t += next;
                        }
                    }
                    track.put(Integer.parseInt(count), Double.parseDouble(t));
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