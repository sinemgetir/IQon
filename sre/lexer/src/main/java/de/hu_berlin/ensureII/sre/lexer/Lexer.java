package de.hu_berlin.ensureII.sre.lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {

    public static void main(String[] args) {

        if(args.length < 2) {
            argumentError();
        }
        
        String option = args[0];
        String fileName = args[1];
        String sre = "";
        SRE_Lexer lexer = new SRE_Lexer();
        
        switch(option){
            case "-f":
                sre = getSRE(fileName);
                try{
                    lexer.tokenize(sre);
                }catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "-d":
                File[] modelFiles = new File(fileName).listFiles();
                try{
                    for(File modelFile : modelFiles){
                        System.out.println("Scanning " + modelFile.getName());
                        sre = getSRE(modelFile.getAbsolutePath());
                        lexer.tokenize(sre);
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                argumentError();
        }
        
            
    }
    
    private static void argumentError() {
        System.err.println("Need the following arguments: ");
        System.err.println(
                "(-f filename | -d directory)");
        System.err.println("The SRE should be in the first line of the file.");
        System.exit(-1);
    }
    
    private static String getSRE(String fileName){
        BufferedReader br = null;
        FileReader fr = null;
        StringBuilder sre = new StringBuilder();

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            sre.append(br.readLine()); // sre is only in the first line
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return sre.toString();
    }

}
