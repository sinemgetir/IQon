package de.hu_berlin.ensureII.sre.generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Generator {

    public static void main(String[] args) {

        if (args.length < 1) {
            argumentError();
        } else {
            if (args[0].equals("random")) {

                RandomSentenceGenerator rsg = new RandomSentenceGenerator();
                String sre = rsg.generateSentence();

                StringBuilder contents = new StringBuilder();
                
                contents.append(sre + "\n");
                contents.append("concatenations: " + rsg.getNrOfConcats() + "\n");
                contents.append("kleene: " + rsg.getNrOfKleene() + "\n");
                contents.append("plusclosures: " + rsg.getNrOfPlusClos() + "\n");
                contents.append("choices: " + rsg.getNrOfChoices() + "\n");
                contents.append("actions: " + rsg.getNrOfActions() + "\n");
                contents.append("alphabet size:" + rsg.getActionAlphabetLength() + "\n");
                contents.append("length: " + sre.length());
                
                /* write to text file */
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                String formatDateTime = now.format(formatter);
                String fileName = formatDateTime + "_" + "randomSRE"
                        + "alphabetSize=" + rsg.getActionAlphabetLength()
                        + "size=" + sre.length();
                writeToFile(fileName, contents.toString());

            } else if (args[0].equals("param") && args.length >= 6) {
                
                generateParamSentence(args);

            } else {
                argumentError();
            }
        }

    }
    
    public static void generateParamSentence(String[] args) {
        ParamSentenceGenerator psg = new ParamSentenceGenerator();
        int nrOfConcat = 0;
        int nrOfKleene = 0;
        int nrOfPlusClos = 0;
        int nrOfChoice = 0;
        
        if(args[1].equals("-f") && args.length == 7) {
            String fileName = args[2];
            psg.setActionAlphabet(readActionAlphabet(fileName));
            
            nrOfConcat = Integer.parseInt(args[3]);
            nrOfKleene = Integer.parseInt(args[4]);
            nrOfPlusClos = Integer.parseInt(args[5]);
            nrOfChoice = Integer.parseInt(args[6]);
            
        }else if(args[1].equals("-f") == false){
            int alphabetSize = Integer.parseInt(args[1]);
            
            if(alphabetSize > 0 && alphabetSize < 26) {
                ArrayList<String> actionAlphabet = new ArrayList<>(Arrays.asList(
                                "abcdefghijklmnopqrstuvwxyz".split("")).subList(0, alphabetSize));
                psg.setActionAlphabet(actionAlphabet);
            }
            
            nrOfConcat = Integer.parseInt(args[2]);
            nrOfKleene = Integer.parseInt(args[3]);
            nrOfPlusClos = Integer.parseInt(args[4]);
            nrOfChoice = Integer.parseInt(args[5]);
            
        }else {
            argumentError();
        }

        psg.setNumberOfConcat(nrOfConcat);
        psg.setNumberOfKleene(nrOfKleene);
        psg.setNumberOfPlusClos(nrOfPlusClos);
        psg.setNumberOfChoice(nrOfChoice);

        String sre = psg.generateSentence();

        StringBuilder contents = new StringBuilder();
        
        contents.append(sre + "\n");
        contents.append("concatenations: " + nrOfConcat + "\n");
        contents.append("kleene: " + nrOfKleene + "\n");
        contents.append("plusclosures: " + nrOfPlusClos + "\n");
        contents.append("choices: " + nrOfChoice + "\n");
        contents.append("actions: " + psg.getNrOfActions() + "\n");
        contents.append("alphabet size:" + psg.getActionAlphabetLength() + "\n");
        contents.append("length: " + sre.length());
        
        /* write to text file */
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String formatDateTime = now.format(formatter);
        String fileName = formatDateTime + "_" + "paramSRE"
                + "-alphabetSize=" + psg.getActionAlphabetLength()
                + "-size=" + sre.length();
        writeToFile(fileName, contents.toString());
        
    }

    public static ArrayList<String> readActionAlphabet(String fileName) {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            ArrayList<String> actionAlphabet = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                if (isValid(line)) {
                    actionAlphabet.add(line);
                }
            }
            
            if(actionAlphabet.isEmpty()) {
                actionAlphabet = new ArrayList<>(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
            }

            return actionAlphabet;
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
        }
    }

    private static boolean isValid(String alphabetElement) {
        for (int i = 0; i < alphabetElement.length(); i++) {
            if (alphabetElement.matches("[a-z][a-z0-9]*") == false) {
                return false;
            }
        }
        return true;
    }
    
    private static void argumentError() {
        System.err.println("Need the following arguments: ");
        System.err.println(
                "(random)|(param ((-f alphabetFile)|(alphabetSize)) " + "{nrConcat nrKleene nrPlusClos nrChoice})");
        System.err.println("note: " + "-f alphabetFile - " + "should be a text file "
                + "where every line is interpreted as an element of the alphabet. "
                + "The elements must start with a letter and only consist of alphanumericals.\n"

                + "alphabetSize - " + "integer from 1-26 which specifies the alphabet in terms of "
                + "using the first alphabetSize letters of the latin alphabet");
        System.exit(-1);
    }
    
    private static void writeToFile(String fileName, String contents) {
        try {
            PrintWriter writer = new PrintWriter( fileName + ".txt", "UTF-8");
            writer.println(contents);
            writer.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }

}
