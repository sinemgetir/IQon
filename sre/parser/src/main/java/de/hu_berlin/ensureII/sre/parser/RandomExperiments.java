package de.hu_berlin.ensureII.sre.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import de.hu_berlin.ensureII.sre.generator.ParamSentenceGenerator;
import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.SRETreeEdit;
import de.hu_berlin.ensureII.sre.parser.utils.CSVLogger;

public class RandomExperiments {
	
	static SREConfig cfg = SREConfig.getAll();

	public static void main(String[] args) throws IOException {
		List<String> searchedString;
        long timeToParse, timetoParse2;
        CSVLogger log = new CSVLogger("../resources/results/randomExperimentswithEdit.csv", "experimentsId","|SRE|",
				"alphLength","numberOfActions", "numberOfConcats|","numberOfKleene", "numberOfChoices", "stringlength", 
				"ExeT-Total+(ms)");
        int experimentId =0;
		
        int sreSizes[] = {
                100,200,500,
                1000,2000,5000,
                10000,20000,50000,
                100000,200000,500000,
                1000000,2000000,5000000,
                10000000};
        int sreSize, nrOfKleene, nrOfChoice, nrOfConcat;
        ParamSentenceGenerator psg = new ParamSentenceGenerator();
        SRE_Parser p;
        String sre;
        
        for(int h=0; h<5; h++) { // sizes of the models
            sreSize = sreSizes[h];
            nrOfKleene = (int) (sreSize * 0.1);
            nrOfConcat = (int) (sreSize * 0.65);
            nrOfChoice = (int) (sreSize * 0.25);
        
            for (int i = 1; i <= 5; i++) { // number of models

                psg.setNumberOfKleene(nrOfKleene);
                psg.setNumberOfConcat(nrOfConcat);
                psg.setNumberOfChoice(nrOfChoice);
                sre = psg.generateSentence();
                
                SRETreeEdit editor = new SRETreeEdit();

                for (int j = 1; j <= 10; j++) { //string size
                    searchedString = getRandomStringList(j);

                    for (int j2 = 1; j2 <= 5; j2++) { //sample size for the same model
                        System.out.println("modelSize: " + sreSize + " , modelNr: " + i + " , stringSize: " + j);
                        experimentId = Math.multiplyExact(Math.multiplyExact(i, j), j2);
                        p = new SRE_Parser(searchedString,cfg);
                        timeToParse = System.currentTimeMillis();
                        p.parse(sre);
                        timeToParse = System.currentTimeMillis() - timeToParse;
                        log.log(experimentId,sre.length(),psg.getActionAlphabetLength(),psg.getNrOfActions(),psg.getNrOfConcats(),
                                psg.getNrOfKleene(), psg.getNrOfChoices(), j,timeToParse);
                        timetoParse2 = System.currentTimeMillis();
                        editor.runRandomChange(p.sreTreeBuilder.getTree());
                        timetoParse2 = System.currentTimeMillis() - timetoParse2;
                        log.log(experimentId,sre.length(),psg.getActionAlphabetLength(),psg.getNrOfActions(),psg.getNrOfConcats(),
                                psg.getNrOfKleene(), psg.getNrOfChoices(), j,timetoParse2);
                    }

                }

            }
		
        }
		
		
		log.flush();
		log.close();
	
		System.out.println("Done");
		
//		for (int i = 0; i < 1; i++) {
//			/*try{
//				ModelRunner.parseModels(new File("../resources/models/random/result_randomSRE.csv"),"../resources/models/random/" );
//				}catch(Exception e){
//				e.printStackTrace();
//				
//				}*/
//			try{
//				ModelRunner.parseModels(new File("../resources/models/param/result_randomSRE1.csv"),"../resources/models/param/");
//				}catch(Exception e){
//				e.printStackTrace();
//				
//				}
//		
//		}
		

		
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
