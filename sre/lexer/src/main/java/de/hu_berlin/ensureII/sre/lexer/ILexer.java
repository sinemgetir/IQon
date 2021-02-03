package de.hu_berlin.ensureII.sre.lexer;

import java.util.List;

import de.hu_berlin.ensureII.sre.grammar.Token;

public interface ILexer {

/*****************************************************************************
** Methods
*****************************************************************************/
    
	/**
	 * Perform lexical analysis on the string.
	 * @param input
	 * 			The string to tokenize.
	 * @return
	 * 			The list of tokens.
	 * @throws LexException
	 */
	public List<Token> tokenize(String input) throws LexException; 
	
/*****************************************************************************
** Exceptions
*****************************************************************************/
	
	public static final class LexException extends Exception{
		private static final long serialVersionUID = 1L;

		private String input;
		
		public LexException(String input) {
			this.input = input;
		}
		
		@Override
		public String toString() {
			return "Failed to tokenize " + this.input;
		}
	}
}
