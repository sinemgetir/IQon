package de.hu_berlin.ensureII.sre.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;

public class Parser {

    public static void main(String[] args) {

        if (args.length < 2) {
            argumentError();
        }

        String option = args[0];
        String fileName = args[1];
        String sre = "";
        List<String> searchedString = new ArrayList<String>();
        SRE_Parser p;
        SREConfig cfg = SREConfig.getAll();

        for (int i = 2; i < args.length; i++) {
            searchedString.add(args[i]);
        }

        switch (option) {
        case "-f":
            sre = getSRE(fileName);
            p = new SRE_Parser(searchedString, cfg);
            p.parse(sre);

            break;
        case "-d":
            File[] modelFiles = new File(fileName).listFiles();
            for (File modelFile : modelFiles) {
                System.out.println("Parsing " + modelFile.getName());
                sre = getSRE(modelFile.getAbsolutePath());
                p = new SRE_Parser(searchedString, cfg);
                p.parse(sre);
            }
            break;
        default:
            argumentError();
        }
    }

    private static void argumentError() {
        System.err.println("Need the following arguments: ");
        System.err.println("(-f filename | -d directory) [searchedString1 searchedString2 ...]");
        System.err.println("The SRE should be in the first line of the file.");
        System.exit(-1);
    }

    private static String getSRE(String fileName) {
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
