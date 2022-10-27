package edu.vanier.template.linear;

public class Matrix {
    
    private final double[][] entries;
    
    public Matrix(double[][] entries) {
        this.entries = entries;
    }
    
    public double[] act(double... vector) {
        double[] newV = new double[entries.length];
        for(int i = 0; i < entries.length; i++) {
            newV[i] = 0;
            for(int j = 0; j < vector.length; j++) {
                newV[i] += entries[i][j]*vector[j];
            }
        }
        return newV;
    }
    
    public double[][] getEntries() {
        return entries;
    }
}