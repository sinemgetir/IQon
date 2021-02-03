package de.hu_berlin.ensureII.sre.parser;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.hu_berlin.ensureII.sre.generator.ParamSentenceGenerator;
import de.hu_berlin.ensureII.sre.parser.attributes.SREConfig;
import de.hu_berlin.ensureII.sre.parser.sretree.calculator.SRETreeCalculatorUsingHistory;

public class ParserTest {
	
	String testModelDir = "res/models/";
	List<String> searchedString = Arrays.asList("a","b"); 
	
	SREConfig cfg = SREConfig.getExact();
	
	List<String> randomModels;
	
	String readFile(String fileName) throws IOException {
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
	
	public void generateRandomModels() {
	    randomModels = new ArrayList<String>();
	    
	    ParamSentenceGenerator psg = new ParamSentenceGenerator();
        psg.setNumberOfChoice(25);
        psg.setNumberOfConcat(65);
        psg.setNumberOfKleene(10);
        
        for(int i=0; i<5; i++) {
            randomModels.add(psg.generateSentence());
        }
	    
	}
	
	@Test
	public void test1() {
		String sre = "(a:b):(i*0.87:(a:n))*0.55";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}

	@Test
	public void test2() {
		String sre = "a*0.67";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}

	@Test
	public void test3() {
		String sre = "a";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test4() {
		String sre = "a:b";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test5() {
		String sre = "a#0.53";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test6() {
		String sre = "(a[34]+b[113])";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test7() {
		String sre = "(a[400]+(a:b)[23])";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test8() {
		String sre = "((a[400]+(a:b)[23])[432]+t*0.89[123])";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test9() {
		String sre = "((a[400]+(a[34]+b#0.87[999])[23])[897]+t*0.89[123])";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test10() {
		String sre = "(((a[400]+(a[34]+(b#0.87)[999])[23])[1]+t*0.89[123])[456]+ijkl[78])";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
	public void test11(){
		String sre = "((((((((((((((((((((((((((r#0.51842594[642]+(w[294]+((((((v:i#0.77861834))[488]+y#0.5520888*0.9682639[623])#0.14364183[594]+f[851]))[262]+(p:n*0.89201415[97]+n[436]):q#0.7332092[416])*0.21304876*0.8600285[691]):h[139]):k))[657]+y[623]):n#0.7586863:o)[998]+i[288])#0.3597551*0.6080831:r*0.5690173:g*0.19539237#0.31370085#0.6564261:i*0.96924144)#0.48989534*0.7390348*0.73883784:i[591]+o[517])[182]+(r[871]+a[150])[112]))#0.1498158)#0.53249353*0.35600948)):u#0.19123155:j:n*0.43818796*0.26349568[941]+d[146]))[782]+l[158]):y[942]+w[653])[721]+i[213])*0.9604512)#0.14919728*0.39032108*0.06770897):h#0.5127315[824]+n[566])[250]+w*0.5365627[661]):g:n:l#0.4455709[510]+(s)[244])))#0.44795233*0.051758945*0.78954643)";
		SRE_Parser p = new SRE_Parser(searchedString, cfg);
		
		p.parse(sre);
	}
	
	@Test
    public void test12() {
        String sre = "(a[34]+b[113]+c[467]+d[234])";
        SRE_Parser p = new SRE_Parser(searchedString, cfg);
        
        p.parse(sre);
    }
	
	@org.junit.Test
    public void random() {
	    
	    generateRandomModels();
	    
	    SRE_Parser p = new SRE_Parser(searchedString, cfg);
	    String sre = new String();
        
        try {
            for(int i=0; i<randomModels.size(); i++) {
                sre = randomModels.get(i);
                p.parse(sre);
            }
        }catch(Exception e) {
            System.out.println("failed to parse:\n" + sre);
            e.printStackTrace();
            fail();
        }
        
    }
	
	@org.junit.Test
    public void randomWithPastNodes() {
	    
	    generateRandomModels();
	    SRETreeCalculatorUsingHistory calc = new SRETreeCalculatorUsingHistory(searchedString);
        SRE_Parser p = new SRE_Parser(searchedString, cfg, calc);
        String sre = new String();
        
        try {
            for(int i=0; i<randomModels.size(); i++) {
                /*calc.lookup = 0;
                calc.hitrate = 0;
                calc.falseHit = 0;
                */
                p.sreTreeBuilder.getClass();
                sre = randomModels.get(i);
                p.parse(sre);
                //System.out.println(calc.lookup + " " + calc.hitrate + " " + calc.falseHit);
            }
        }catch(Exception e) {
            System.out.println("failed to parse:\n" + sre);
            e.printStackTrace();
            fail();
        }
        
    }
	
	@org.junit.Test
    public void randomParallel() {
	    
	    generateRandomModels();
        
        SRE_Parser p = new SRE_TwoPassParser(searchedString, cfg);
        String sre = new String();
        
        try {
            for(int i=0; i<randomModels.size(); i++) {
                sre = randomModels.get(i);
                p.parse(sre);
            }
        }catch(Exception e) {
            System.out.println("failed to parse:\n" + sre);
            e.printStackTrace();
            fail();
        }
        
    }
	
}
