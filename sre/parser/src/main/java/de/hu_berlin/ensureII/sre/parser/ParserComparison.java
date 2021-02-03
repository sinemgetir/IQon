package de.hu_berlin.ensureII.sre.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import de.hu_berlin.ensureII.sre.generator.ParamSentenceGenerator;
import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculatorUsingHistory;
import de.hu_berlin.ensureII.sre.parser.utils.CSVLogger;

public class ParserComparison {

    static SREConfig cfg = SREConfig.getAll();

    public static void main(String[] args) throws IOException {
        List<String> searchedString;
        long timeToParseNormal, timeToParseWithHashMap;
        CSVLogger log = new CSVLogger("../resources/results/parserComparison.csv", "experimentsId","|SRE|",
                "alphLength","numberOfActions", "numberOfConcats|","numberOfKleene", "numberOfChoices", "stringlength", 
                "NORMAL_ExeT-Total+(ms)", "HASHMAP_ExeT-Total+(ms)");
        int experimentId =0;
        
        int sreSizes[] = {
                100,200,500,
                1000,2000,5000,
                10000,20000,50000,
                100000};
        int sreSize, nrOfKleene, nrOfChoice, nrOfConcat;
        ParamSentenceGenerator psg = new ParamSentenceGenerator();
        SRE_Parser parser;
        SRETreeCalculatorUsingHistory calc;
        String sre;
        
        for(int h=0; h<sreSizes.length; h++) { // sizes of the models
            sreSize = sreSizes[h];
            nrOfKleene = (int) (sreSize * 0.1);
            nrOfConcat = (int) (sreSize * 0.65);
            nrOfChoice = (int) (sreSize * 0.25);
        
            for (int i = 1; i <= 3; i++) { // number of models

                psg.setNumberOfKleene(nrOfKleene);
                psg.setNumberOfConcat(nrOfConcat);
                psg.setNumberOfChoice(nrOfChoice);
                sre = psg.generateSentence();

                for (int j = 1; j <= 5; j++) { //string size
                    searchedString = getRandomStringList(j);

                    for (int j2 = 1; j2 <= 3; j2++) { //sample size for the same model
                        System.out.println("modelSize: " + sreSize + " , modelNr: " + i + " , stringSize: " + j);
                        experimentId = Math.multiplyExact(Math.multiplyExact(i, j), j2);
                        parser = new SRE_Parser(searchedString,cfg);
                        
                        timeToParseNormal = System.currentTimeMillis();
                        parser.parse(sre);
                        timeToParseNormal = System.currentTimeMillis() - timeToParseNormal;
                        
                        calc = new SRETreeCalculatorUsingHistory(searchedString);
                        parser = new SRE_Parser(searchedString, cfg, calc);
                        
                        timeToParseWithHashMap = System.currentTimeMillis();
                        parser.parse(sre);
                        timeToParseWithHashMap = System.currentTimeMillis() - timeToParseWithHashMap;
                        
                        log.log(experimentId,sre.length(),psg.getActionAlphabetLength(),psg.getNrOfActions(),psg.getNrOfConcats(),
                                psg.getNrOfKleene(), psg.getNrOfChoices(), j,timeToParseNormal,timeToParseWithHashMap);
                    }

                }

            }
        
        }
        
        
        log.flush();
        log.close();
    
        System.out.println("Done");
        
    }

    private static List<String> getRandomStringList(int j) {
        List<String> searchedString = new ArrayList<String>();
        
            for (int i = 1; i <=j; i++) {
            String s = RandomStringUtils.randomAlphabetic(i);
            searchedString.add(s);
        }
        
        return searchedString;
    }
    
}
