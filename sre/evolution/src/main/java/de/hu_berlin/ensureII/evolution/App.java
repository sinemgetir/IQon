
package de.hu_berlin.ensureII.evolution;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import de.hu_berlin.ensureII.evolution.specification.ChangeSRE;
import de.hu_berlin.ensureII.sre.generator.ParamSentenceGenerator;
import de.hu_berlin.ensureII.sre.parser.SRE_Parser;
import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.SRETreeEdit;
import de.hu_berlin.ensureII.sre.parser.utils.CSVLogger;
/**
 * Random application
 *
 */
public class App
{
 static SREConfig cfg = SREConfig.getAll();
 public static void main(String[] args) throws IOException {
  List<String> searchedString;
        long timeToParse;
        CSVLogger log = new CSVLogger("../resources/results/randomExperimentsSREevwChangeRate.csv", "experimentsId","|SRE|",
    "alphLength","numberOfActions", "numberOfConcats|","numberOfKleene", "numberOfChoices", "evolution-id","stringlength", "visitedNodes",
    "ExeT-Total+(ms)");
        int experimentId =0;
  
        int sreSizes[] = {
        		1000,
        		10000,
                100000};
        int sreSize, nrOfKleene, nrOfChoice, nrOfConcat;
        ParamSentenceGenerator psg = new ParamSentenceGenerator();
        SRE_Parser p;
        String sre;
       
        for(int h=0; h<sreSizes.length; h++) { // sizes of the models
            sreSize = sreSizes[h];
            nrOfKleene = (int) (sreSize * 0.1);
            nrOfConcat = (int) (sreSize * 0.65);
            nrOfChoice = (int) (sreSize * 0.25);
       
            for (int i = 1; i <= 1; i++) { // number of models
                psg.setNumberOfKleene(nrOfKleene);
                psg.setNumberOfConcat(nrOfConcat);
                psg.setNumberOfChoice(nrOfChoice);
                sre = psg.generateSentence();
                for (int j = 1; j <= 1; j++) { //string size
                    searchedString = getRandomStringList(j);
                    for (int j2 = 1; j2 <= 1; j2++) { //sample size for the same model
                        System.out.println("modelSize: " + sreSize + " , modelNr: " + i + " , stringSize: " + j);
                        experimentId = Math.multiplyExact(Math.multiplyExact(i, j), j2);
                        p = new SRE_Parser(searchedString,cfg);
                        timeToParse = System.currentTimeMillis();
                        p.parse(sre);
                        timeToParse = System.currentTimeMillis() - timeToParse;
                        //log.log(experimentId,sre.length(),psg.getActionAlphabetLength(),psg.getNrOfActions(),psg.getNrOfConcats(),
                          //      psg.getNrOfKleene(), psg.getNrOfChoices(), 0,j, (psg.getNrOfKleene()+psg.getNrOfChoices()+psg.getNrOfConcats()),timeToParse);
                       
                        SRETree initialTree = p.sreTreeBuilder.getTree();
                        for (int k = 1; k <= 100; k+=3) {
                        	ChangeSRE toChange = new ChangeSRE(initialTree);
                        	long timeEv =0;
                        	int visitedNodesSum = 0;
                        	for (int l = 0; l < k; l++) {
                        		long time =  System.currentTimeMillis();
                            	int visitedNodes = applychange(toChange);
                            	time = System.currentTimeMillis() -time;
                            	
                            	if (visitedNodes !=0) {
                            		timeEv += time;
                            		visitedNodesSum +=visitedNodes;
                            	}

							}
                        	
                        	if (visitedNodesSum !=0) {
                        	log.log(experimentId,sre.length(),0,0,0,
                                    0, 0, k, j,visitedNodesSum, timeEv);
                        	
                           
                             long timeAll = System.currentTimeMillis();
                         	 int visitedAllNodes = applyAll(toChange);
                         	timeAll = System.currentTimeMillis() -timeAll;
                         	 log.log(experimentId,sre.length(),psg.getActionAlphabetLength(),psg.getNrOfActions(),psg.getNrOfConcats(),
                                     psg.getNrOfKleene(), psg.getNrOfChoices(), k, j,visitedAllNodes, timeAll);
                        	}
                        	
						}
                        
                       
                    }
                }
            }
  
        }
  
  
  log.flush();
  log.close();
 
  System.out.println("Done");
  
//  for (int i = 0; i < 1; i++) {
//   /*try{
//    ModelRunner.parseModels(new File("../resources/models/random/result_randomSRE.csv"),"../resources/models/random/" );
//    }catch(Exception e){
//    e.printStackTrace();
//    
//    }*/
//   try{
//    ModelRunner.parseModels(new File("../resources/models/param/result_randomSRE1.csv"),"../resources/models/param/");
//    }catch(Exception e){
//    e.printStackTrace();
//    
//    }
//  
//  }
  
  System.out.println("Done");
  
 }
 private static int applychange(ChangeSRE changeTree) {
	SRETree tree = changeTree.getSre();
	SRETreeEdit edit = new SRETreeEdit();
	
	edit.runRandomChange(tree);
	 
    return edit.getUpdateCalls();
  
 }
 
 private static int applyAll(ChangeSRE changeTree) {
	SRETree tree = changeTree.getSre();
	SRETreeEdit edit = new SRETreeEdit();
	edit.updateAll(tree.getRoot());
    
	return edit.getUpdateAllCalls();

 }
 
 private static int getRandomNode(int size) {
  // TODO Auto-generated method stub
  return RandomUtils.nextInt(5, size);
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