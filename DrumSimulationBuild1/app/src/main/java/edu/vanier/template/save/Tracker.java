package edu.vanier.template.save;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tracker {
    
    private final List<Instance> instances = new ArrayList<>();
    
    private final File trackerFile;
    
    public Tracker(File trackerFile) throws IOException {
        this.trackerFile = trackerFile;
        this.trackerFile.createNewFile();
    }
    
    public void upload() throws IOException {
        try(FileWriter fw = new FileWriter(trackerFile)) {
            for(Instance instance : instances) {
                fw.append(instance.toString());
            }
            fw.flush();
        }
    }
    
    public void download() throws IOException {
        try(FileReader fr = new FileReader(trackerFile)) {
            while(true) {
                int fin = fr.read();
                if(fin == -1) {
                    break;
                }
                boolean cont = true;
                String deltaTime = "";
                while(cont) {
                    char next = (char)fr.read();
                    if(next == ' ') {
                        cont = false;
                    } else {
                        deltaTime += next;
                    }
                }
                cont = true;
                String i = "";
                while(cont) {
                    char next = (char)fr.read();
                    if(next == ' ') {
                        cont = false;
                    } else {
                        i += next;
                    }
                }
                cont = true;
                String j = "";
                while(cont) {
                    char next = (char)fr.read();
                    if(next == ' ') {
                        cont = false;
                    } else {
                        j += next;
                    }
                }
                cont = true;
                String position = "";
                while(cont) {
                    char next = (char)fr.read();
                    if(next == ' ') {
                        cont = false;
                    } else {
                        position += next;
                    }
                }
                cont = true;
                String velocity = "";
                while(cont) {
                    char next = (char)fr.read();
                    if(next == ' ') {
                        cont = false;
                    } else {
                        velocity += next;
                    }
                }
                cont = true;
                String vPrevious = "";
                while(cont) {
                    char next = (char)fr.read();
                    if(next == ' ') {
                        cont = false;
                    } else {
                        vPrevious += next;
                    }
                }
                Instance instance = new Instance(Double.parseDouble(deltaTime), Integer.parseInt(i), Integer.parseInt(j),
                        Double.parseDouble(position), Double.parseDouble(velocity), Double.parseDouble(vPrevious));
                instances.add(instance);
            }
        }
    }
    
    public void record(Instance instance) {
        instances.add(instance);
    }
    
    public Instance next() throws IndexOutOfBoundsException {
        return instances.remove(0);
    }
    
}