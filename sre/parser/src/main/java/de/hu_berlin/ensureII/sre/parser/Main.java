package de.hu_berlin.ensureII.sre.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hu_berlin.ensureII.sre.generator.ParamSentenceGenerator;
import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculatorUsingHistory;

public class Main {

    static List<String> randomModels;
    
    public static void main(String[] args) {
        
        long millis;
        generateRandomModels();
        
        List<String> searchedString = new ArrayList<String>();
        SREConfig cfg = new SREConfig();
        String sre;
        
        searchedString.add("a");
        searchedString.add("b");
        searchedString.add("c");
        searchedString.add("d");
        
        SRE_Parser parserNormal = new SRE_Parser(searchedString, cfg);
        
        SRETreeCalculatorUsingHistory calc = new SRETreeCalculatorUsingHistory(searchedString);
        SRE_Parser parserHistory = new SRE_Parser(searchedString, cfg, calc);
        SRE_TwoPassParser parserParallel = new SRE_TwoPassParser(searchedString, cfg);
        
        ParamSentenceGenerator psg = new ParamSentenceGenerator();
        psg.setNumberOfChoice(25);
        psg.setNumberOfConcat(65);
        psg.setNumberOfKleene(10);
        
        String sre1 = psg.generateSentence();
        String sre2 = psg.generateSentence();
        String sre3 = "(a[5]+b[5])";//"(a[34]+b[113]+c[24]+d[67])";
        String sre4 = "(((a[34]+b[113]):((c:d)*0.3):e:f):a)";
        
        SRETreeNode rootnode = parserNormal.parse(sre3);
        SRETree tree1 = parserNormal.sreTreeBuilder.getTree();
        //tree1.getRoot().printToDot("../resources/tree1.dot");
        
       // System.out.println(tree1.getNodes());
        System.out.print(tree1.getRoot().getData());
        System.out.print(rootnode.getData());
        
        
        parserNormal.parse(sre4);
        SRETree tree2 = parserNormal.sreTreeBuilder.getTree();
        tree2.getRoot().printToDot("../resources/tree2.dot");
        System.out.println(tree2.getNodes());
        
        System.out.println(tree2.getRoot());
        System.out.println(tree2.getNodes().get(10).getParent());
        System.out.println(tree2.getNodes().get(10).getParent().getId());
        System.out.print(tree2.getRoot().getData());



        
        
     //  System.out.println(sre4);
      //  parserNormal.currentAST().printToDot("../resources/test.dot");
        
        
        /*for(int i=0; i<5; i++) {
            if(i%2 == 0) {
                sre3 = sre1;
            }else {
                sre3 = sre2;
            }
            millis = System.currentTimeMillis();
            calc.falseHit = 0;
            calc.hitrate = 0;
            parserHistory.parse(sre3);
            System.out.println("hit: " + calc.hitrate + " collision: " + calc.falseHit);
            System.out.println(i + ":History parsing took " + (System.currentTimeMillis() - millis) + "ms.");
        }*/
        
        /*
        millis = System.currentTimeMillis();
        for(int i=0; i<randomModels.size(); i++) {      
            parserNormal.sreTreeBuilder.getClass();
            sre = randomModels.get(i);
            parserNormal.parse(sre);
        }
        System.out.println("Normal parsing took " + (System.currentTimeMillis() - millis) + "ms.");
        
        millis = System.currentTimeMillis();
        for(int i=0; i<randomModels.size(); i++) {
            parserHistory.sreTreeBuilder.getClass();
            sre = randomModels.get(i);
            parserHistory.parse(sre);            
        }
        System.out.println("History parsing took " + (System.currentTimeMillis() - millis) + "ms.");
        
        millis = System.currentTimeMillis();
        for(int i=0; i<randomModels.size(); i++) {
            parserParallel.sreTreeBuilder.getClass();
            sre = randomModels.get(i);
            parserParallel.parse(sre);            
        }
        System.out.println("Parallel parsing took " + (System.currentTimeMillis() - millis) + "ms.");
        
        System.out.println(parserNormal.getCurrentAST().getData());
        System.out.println(parserHistory.getCurrentAST().getData());
        System.out.println(parserParallel.getCurrentAST().getData());
        
        parserNormal.getCurrentAST().printToDot("../resources/test.dot");
        */
    }
    
    public static void generateRandomModels() {
        randomModels = new ArrayList<String>();
        
        ParamSentenceGenerator psg = new ParamSentenceGenerator();
        psg.setNumberOfChoice(25);
        psg.setNumberOfConcat(65);
        psg.setNumberOfKleene(10);
        
        for(int i=0; i<1; i++) {
            randomModels.add(psg.generateSentence());
        }
        
    }

    static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
}
