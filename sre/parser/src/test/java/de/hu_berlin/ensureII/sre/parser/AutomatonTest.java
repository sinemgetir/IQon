package de.hu_berlin.ensureII.sre.parser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.hu_berlin.ensureII.sre.grammar.Symbol;
import de.hu_berlin.ensureII.sre.grammar.Token;
import de.hu_berlin.ensureII.sre.parser.reducer.SRE_Reducer;

public class AutomatonTest {

	@Test
	public void test() {
		SRE_Reducer reducer = new SRE_Reducer();
		List<Symbol> handle = new ArrayList<Symbol>();
		
		handle.add(new Token("action","a"));
		
		try{
		    reducer.getReductionRule(handle);
		}catch(Exception e) {
		    e.printStackTrace();
		}
	}

}
