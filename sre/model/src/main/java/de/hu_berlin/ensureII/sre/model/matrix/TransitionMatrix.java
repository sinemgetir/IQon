package de.hu_berlin.ensureII.sre.model.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class TransitionMatrix {

    public static void main(String[] args) {
        
        TransitionMatrix matrix = new TransitionMatrix("src/main/resources/tra/leader/leader3_2.tra");
        matrix.print();
        matrix.addState();
        /*ArrayList<Double> prob = new ArrayList<Double>(Arrays.asList
                (0.0, 0.2, 0.2, 0.6)
                );
        matrix.updateDistribution(26, prob);*/
        //matrix.removeTransition(0, 2);
        matrix.addTransition(0, 9, 1.0);
        matrix.exportToTra("foo.tra");
    }
    
/*****************************************************************************
** Constructors
*****************************************************************************/

    public TransitionMatrix(String traFile) {
        
        matrix = new ArrayList<ArrayList<Double>>();
        
        try {
            File f = new File(traFile);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            
            //ignore first line, model type for the storm model checker
            line = br.readLine();
            nrOfRows = 0;
            
            //count number of states 
            while ((line = br.readLine()) != null) {
                if(Integer.parseInt(line.split(" ")[0]) >= nrOfRows) {
                    nrOfRows++;
                }
             }  
            nrOfColumns = nrOfRows;
            br.close();
            
            br = new BufferedReader(new FileReader(f));
            
            for(int i=0; i<nrOfRows; i++) {
                ArrayList<Double> row = new ArrayList<Double>();
                for(int j=0; j<nrOfColumns; j++) {
                    row.add(0.0);
                }
                matrix.add(row);
            }
            
            int source, target;
            double probability;
            //ignore first line, model type for the storm model checker
            line = br.readLine();
            while ((line = br.readLine()) != null) {
               source = Integer.parseInt(line.split(" ")[0]);
               target = Integer.parseInt(line.split(" ")[1]);
               probability = Double.parseDouble(line.split(" ")[2]);
               
               matrix.get(source).set(target, probability);
               
            }
            br.close();
         } catch(Exception e) {
            e.printStackTrace();
         }
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
/*****************************************************************************
** Edit operations
*****************************************************************************/
    
    public void addState() {
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
        ArrayList<Double> row;
        
        for(int i=0; i<nrOfRows; i++) {
            row = matrix.get(i);
            row.add(0.0);
        }
        
        row = new ArrayList<Double>();
        for(int j=0; j<nrOfColumns; j++) {
            row.add(0.0);
        }
        row.add(1.0); //self loop 1.0
        matrix.add(row);
        
        nrOfRows++;
        nrOfColumns++;
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    public void updateDistribution(int state, ArrayList<Double> probabilities) {
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
        ArrayList<Double> row = matrix.get(state);
        
        for(int j=0; j<nrOfColumns; j++) {
            if(j < probabilities.size()) {
                row.set(j, probabilities.get(j));
            }else {
                row.set(j, 0.0);
            }
        }
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    public void addTransition(int src, int dst, double prob) {
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
        ArrayList<Double> row = matrix.get(src);
        Double oldRemainder = 1.0 - row.get(dst);
        Double newRemainder = 1.0 - prob;
        Double newProb;
        
        for(int j=0; j<nrOfColumns; j++) {
            if(j==dst) {
                row.set(dst, prob);
            }else {
                newProb = (row.get(j) / oldRemainder) * newRemainder;
                row.set(j, newProb);
            }
        }
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
    public void removeTransition(int src, int dst) {
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
        ArrayList<Double> row = matrix.get(src);
        
        if(Math.abs(1.0 - row.get(dst)) <= PRECISION) {
            row.set(src, 1.0);
        }else {
            Double remainder = 1.0 - row.get(dst);
            Double updatedProb;
            for(int j=0; j<nrOfColumns; j++) {
                updatedProb = row.get(j) / remainder;
                row.set(j, updatedProb);
            }
        }
        
        row.set(dst, 0.0);
        
        /****************************************************************************/
        assert(Invariant());
        /****************************************************************************/
        
    }
    
/*****************************************************************************
** Fields
*****************************************************************************/
    
    private int nrOfRows;
    
    private int nrOfColumns;
    
    private ArrayList<ArrayList<Double>> matrix;
    
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
   
    public int getNrOfStates() {
        return nrOfRows;
    }
    
    public double getTransitionProb(int src, int dst) {
        return matrix.get(src).get(dst);
    }
    
/*****************************************************************************
** Invariants
*****************************************************************************/
    
    public boolean Invariant() {
        assert(correctNrOfRowsAndColumns());
        assert(isQuadratic());
        assert(isStochastic());
        
        return true;
    }
    
    public boolean correctNrOfRowsAndColumns() {
        
        boolean correctNrOfRows = nrOfRows == matrix.size();
        boolean correctNrOfColumns = true;
        
        for(int i=0; i<nrOfRows; i++) {
            correctNrOfColumns = correctNrOfColumns && (nrOfColumns == matrix.get(i).size());
        }
        
        return correctNrOfRows && correctNrOfColumns;
    }
    
    public boolean isQuadratic() {
        return nrOfRows == nrOfColumns;
    }
    
    private static final double PRECISION = 0.00001;
    
    public boolean isStochastic() {
        
        double rowSum;
        ArrayList<Double> row;
        
        for(int i=0; i<nrOfRows; i++) {
            rowSum = 0.0;
            row = matrix.get(i);
            for(int j=0; j<nrOfColumns; j++) {
                if(row.get(j) < 0.0 || row.get(j) > 1.0) return false;
                rowSum = rowSum + row.get(j);
            }
            if(Math.abs(rowSum - 1.0) > PRECISION) return false;
        }
        
        return true;
    }
    
/*****************************************************************************
** Export
*****************************************************************************/
    
    public void print() {
        for(int i=0;i<nrOfRows;i++){
            ArrayList<Double> r=matrix.get(i);
            for(int j=0;j<nrOfColumns;j++){
                System.out.print(r.get(j) + " ");
            }
            System.out.println();
        }
    }
    
    public void exportToTra(String fileName) {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("dtmc" + "\n"); //for the storm model checker
        
        for(int i=0; i<nrOfRows; i++) {
            ArrayList<Double> row = matrix.get(i);
            for(int j=0; j<nrOfColumns; j++) {
                if(row.get(j) > 0) {
                    sb.append(i + " " + j + " " + row.get(j));
                    sb.append("\n");
                }
            }
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(sb.toString());
            writer.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
