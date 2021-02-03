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
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.ISRETreeCalculator;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculator;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculatorUsingHistory;
import de.hu_berlin.ensureII.sre.parser.utils.CSVLogger;
import transitiongraph.TransitionGraph;
import transitiongraph.util.DTMCParser;

public class Experiment {

	public static void main(String args[]) throws IOException {
		//Logging
		CSVLogger log = new CSVLogger("../resources/results/externalComparison.csv","modelsize","storm-mc");
		
		
		//prop
		//"P=? [F \"done\"]"
		//"T=? [F<1 \"done\"]"
		// Tra file 
		String traFile = "/home/sinem/Documents/storm/storm/build/bin/models/teleAsist9.tra";
		String labFile = "/home/sinem/Documents/storm/storm/build/bin/models/teleAsist9.lab";
		//String property = "P=?[F\"s6\"]";
		//String timebound1Ctmc = "P=?[F<1\"done\"]";;
		
		//transformation
		 File f = new File(traFile);
	        TransitionGraph tg = Tra2Emf.tra2emf(f);
	        //TransitionGraph tg = EmfStorage.loadFromXMI("src/main/resources/xmi/leader/leader3_4.xmi");
	        
	        List<String> search = Arrays.asList(new String[]{"a40134"});
			String property = "P=?[F\""+"s10s26s39s410s510s69s710s810s910"+ "\"]";


//	        ISRETreeCalculator calc = new SRETreeCalculator(search);
//	        //root.calculateNodeData(calc);
//	        StateElimination se = new StateElimination(tg,calc);
//	        se.run();
//	     
			 //List<String> search = Arrays.asList(new String[]{"a4"});
		        //a615 --> s15s24s34s44s54
		   
		        
		        Path path = FileSystems.getDefault().getPath("../resources/models/teleAsistforBro9-2.tra");
				String s = String.join("\n", Files.readAllLines(path));
				DTMC dtmc = DTMCParser.parseMatrix(s);
				System.out.print("parsed");
		        SRETreeCalculator calc = new SRETreeCalculator(search);
		        
		        
		       long current =  System.currentTimeMillis();
		        
				DTMC2SREBrzozowski transformer = new DTMC2SREBrzozowski(dtmc,calc);
				
				System.out.println("Time to transform and inial mc: " +(System.currentTimeMillis()-current));
				SRETreeNode root = transformer.getTransformed();
				
//				 try{
//				        PrintWriter pw = new PrintWriter("../resources/models/teleAsistforBro2.sre");
//				        StringBuilder sb = new StringBuilder();
//				        System.out.println(root);
//				        sb.append(root.toString());
//				        pw.write(sb.toString());
//				        pw.close();
//				    }catch(Exception e) {
//				        e.printStackTrace();
//				    }   
				 
				SRETree tree = transformer.getTree();
			
				System.out.println("Running storm inial:");
				runStorm(traFile,labFile,property);
				
			
			 ChangePAandSRE allChange =  new ChangePAandSRE(dtmc,tree,transformer.getMap());
			 
			 long timeForSREedit =  allChange.addTask(19683, "newTask", "oldTask", 26757);
			//long timeForSREedit =  allChange.addTask(9, "newTask", "oldTask", 17);
			 
		 System.out.println("Time for SRE edit: "+ timeForSREedit);
//			 
//			allChange.pa.exportToTra("/home/sinem/Documents/storm/storm/build/bin/models/teleAsist9-ev1.tra");
//			
//	      // Equivalent models 
//	      TransitionMatrix matrix = new TransitionMatrix(traFile);    
//	      SRETreeNode root = se.getSRE();
//		SRETree tree = null ;
        //set up system

//		SystemChange all = new SystemChange(tree,matrix);
//		
//		for (int i = 0; i < 2; i++) {
//			int stateid= RandomUtils.nextInt(5, matrix.getNrOfStates());
//			
//			all.addTask(stateid, "s12", stateid+2);
//			all.getPa().toString();
//			all.getSre().toString();
//		}

		//TODO trim and transform result
		//result = result.substring(result.indexOf(":") + 1);
		//System.out.println(new File(".").getAbsoluteFile());
//	    SREConfig cfg = new SREConfig();
//	    SRE_Parser parserNormal = new SRE_Parser(search, cfg);
//	    
//	    SRETreeCalculatorUsingHistory calc = new SRETreeCalculatorUsingHistory(search);
//	    
//	    
//		BufferedReader br = new BufferedReader(new FileReader("../resources/models/teleAsist5sre.txt"));
//		    try {
//		        StringBuilder sb = new StringBuilder();
//		        String line = br.readLine();
//		        SRETreeNode rootnode = parserNormal.parse(line);
//		        
//		    } finally {
//		        br.close();
//		    }
//		
//	   
//        SRETree tree = parserNormal.sreTreeBuilder.getTree();
        
	      // Equivalent models 
//	    TransitionMatrix matrix = new TransitionMatrix(traFile);    
        //set up system

	//	SystemChange all = new SystemChange(tree,matrix);
//		
//		ChangeExplicitPA Changepa = new ChangeExplicitPA(matrix);
//		
//		Changepa.addTask(1, "ax", "ay", 20195);
//		TransitionMatrix pa = Changepa.getPa();

		DTMC pa = allChange.pa; 
		pa.exportToTra("/home/sinem/Documents/storm/storm/build/bin/models/teleAsistforBro9-version1.tra");
		String traFile2 = "/home/sinem/Documents/storm/storm/build/bin/models/pa1.tra";
		String labFile2 = "/home/sinem/Documents/storm/storm/build/bin/models/pa1.lab";
		
		
		System.out.println("Running Storm for change:");
		runStorm(traFile2,labFile2,property);
//		
//		try {
//			Process process = runtime.exec(pathToStorm + "/storm --explicit " + traFile + " " + labFile + " " + "--prop " + property);
//			InputStream is = process.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader br = new BufferedReader(isr);
//			String line;
//		
//			while ((line = br.readLine()) != null) {
//				//System.out.println(line);
//			  if (line.contains("Time")) {
//					  System.out.println(line);
//				  }
//			  if (line.contains("Result")) {
//				  System.out.println(line);
//				  result = line;
//			  } 
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		

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
			System.out.println(line);
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
