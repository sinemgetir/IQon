package de.hu_berlin.ensureII.sre.lexer;

import static org.junit.Assert.fail;

import de.hu_berlin.ensureII.sre.generator.ParamSentenceGenerator;
import de.hu_berlin.ensureII.sre.lexer.ILexer.LexException;

public class LexerTest {

	@org.junit.Test
	public void test1() {
		
		SRE_Lexer lex = new SRE_Lexer();
		
		//
		String sre = "[1]";
		
		try {
			lex.tokenize(sre);
		}catch(Exception e) {
			fail("should not fail to tokenize");
		}
		
	}

	@org.junit.Test(expected = LexException.class)
	public void test2() throws LexException {
		
		SRE_Lexer lex = new SRE_Lexer();
		
		String sre = "a:/";
		
		lex.tokenize(sre);
	}
	
	@org.junit.Test
	public void random1() {
		
		SRE_Lexer lex = new SRE_Lexer();
		ParamSentenceGenerator psg = new ParamSentenceGenerator();
		psg.setNumberOfChoice(10);
		psg.setNumberOfConcat(10);
		psg.setNumberOfKleene(10);
		int nrOfTests = 1000;
		String sre = "";
		
		try {
			for(int i=0; i<nrOfTests; i++) {
				sre = psg.generateSentence();
				lex.tokenize(sre);
			}
		}catch(Exception e) {
			System.out.println("failed to tokenize:\n" + sre);
			e.printStackTrace();
			fail();
		}
		
	}
	
}
