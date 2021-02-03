package de.hu_berlin.ensureII.evolution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import de.hu_berlin.ensureII.evolution.specification.ChangeExplicitPA;
import de.hu_berlin.ensureII.evolution.specification.ChangePAandSRE;
import de.hu_berlin.ensureII.evolution.system.SystemChange;
import de.hu_berlin.ensureII.sre.model.DTMC;
import de.hu_berlin.ensureII.sre.model.conversion.Tra2Emf;
import de.hu_berlin.ensureII.sre.model.matrix.TransitionMatrix;
import de.hu_berlin.ensureII.sre.model.transformation.DTMC2SREBrzozowski;
import de.hu_berlin.ensureII.sre.model.transformation.StateElimination;
import de.hu_berlin.ensureII.sre.parser.SRE_Parser;
import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculator;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculatorUsingHistory;
import de.hu_berlin.ensureII.sre.parser.utils.CSVLogger;
import transitiongraph.TransitionGraph;
import transitiongraph.util.DTMCParser;

public class Experiments {

	public static void main(String args[]) throws IOException {
		//Logging
		CSVLogger log = new CSVLogger("../resources/results/externalComparison.csv","modelsize","storm-mc");
		String traFile, labFile, prop, tra2transform, searchAction;
		      
//		try (BufferedReader br = new BufferedReader(new FileReader("/home/sinem/Documents/storm/storm/build/bin/models/models.txt"))) {
	//	    String line;
		//    while ((line = br.readLine()) != null) {
		  //  
		    //	String [] elements = line.split(" ");
		String [] elements = {"teleAsist9.tra","teleAsist9.lab", "P=?[F\""+"s13s213s313s413s513s613s713s813s913"+"\"]" ,"teleAsistforBro9.tra" ,"a40393"};
		    	traFile = "/home/sinem/Documents/storm/storm/build/bin/models/"+elements[0];
		    	labFile = "/home/sinem/Documents/storm/storm/build/bin/models/"+elements[1];
		    	prop = elements[2];
		    	tra2transform = "resources/"+elements[3];
		    	searchAction = elements[4];
		    	List<String> search = Arrays.asList(new String[]{searchAction});
		    	
		    	System.out.println("Model file: "+elements[0]);
		    	Path path = FileSystems.getDefault().getPath(tra2transform);
		    	String s = String.join("\n", Files.readAllLines(path));
		    	DTMC dtmc = DTMCParser.parseMatrix(s);
				System.out.print("DTMC parsed");
			    SRETreeCalculator calc = new SRETreeCalculator(search);
	
			    long current =  System.currentTimeMillis();
			    DTMC2SREBrzozowski transformer = new DTMC2SREBrzozowski(dtmc,calc);
				System.out.println("Time to transform and inial mc: " +(System.currentTimeMillis()-current));

			    
				SRETree tree = transformer.getTree();
				System.out.println("ResultSRE: "+ transformer.getTransformed().getData());

				System.out.println("Running storm inial:");
		    	
				runStorm(traFile, labFile, prop);
		    	
				////Change part
				
				ChangePAandSRE allChange =  new ChangePAandSRE(dtmc,tree,transformer.getMap());
				//Tuple<Integer,Double> rates = new Tuple<Integer, Double> (29,0.009);
				//Tuple<Integer,Double> rates = new Tuple<Integer, Double> (40395,0.0009);
				Tuple<Integer,Double> rates = new Tuple<Integer, Double> (2,0.0009);

				long time = allChange.updateDistribution(0, Arrays.asList(rates));

				//long time = allChange.updateDistribution(14, Arrays.asList(rates));
				//long time = allChange.updateDistribution(20196, Arrays.asList(rates));

			    System.out.println("incremental time: "+time);
			    
				//runStorm("/home/sinem/Documents/storm/storm/build/bin/models/teleAsist2-2.tra", labFile, prop);


		    	
		    
		  //  }
		//}
		
	
	}
	
	
	public static void runStorm(String traFile, String labFile,String property) {
		//Storm set up
				String result = "";
				String result2 = "";
		Runtime runtime = Runtime.getRuntime();
		String pathToStorm = "/home/sinem/Documents/storm/storm/build/bin";

		
		try {
			Process process = runtime.exec(pathToStorm + "/storm --explicit " + traFile + " " + labFile + " " + "--prop " + property);
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
		
			while ((line = br.readLine()) != null) {
			//System.out.println(line);
			  if (line.contains("Time")) {
					  System.out.println(line);
				  }
			  if (line.contains("Result")) {
				  System.out.println(line);
				  result = line;
			  } 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
