package de.hu_berlin.ensureII.sre.lexer;

import java.util.ArrayList;
import java.util.List;

import de.hu_berlin.ensureII.sre.grammar.SRE_Grammar;
import de.hu_berlin.ensureII.sre.grammar.Token;

public class SRE_Lexer implements ILexer {

    /**
     * A Lexer for tokenizing stochastic regular expressions
     * 
     */

/*****************************************************************************
** Implement ILexer
*****************************************************************************/

    @Override
    public List<Token> tokenize(String input) throws LexException {
        
        /****************************************************************************/
        if(input == null){
            throw new IllegalArgumentException("Input must be non null.");
        }
        /****************************************************************************/

        ArrayList<Token>tokens = new ArrayList<Token>();
        toAnalyse = input;
        currentLexPos = 0;

        try {
            while (currentLexPos < toAnalyse.length()) {
                tokens.add(getNextToken());
            }
        } catch (LexException e) {
            throw e;
        }

        tokens.add(SRE_Grammar.eofToken);
        
        /****************************************************************************/
        assert(tokensEqualString(tokens, input));
        /****************************************************************************/

        return tokens;
    }

/*****************************************************************************
** Match characters of string until a token can be created
*****************************************************************************/
    
    /**
     * Remove the first characters of the input until a token can't be matched
     * anymore.
     * 
     * @return The first token from tokenizing the string.
     * @throws LexException
     */
    private Token getNextToken() throws LexException {

        /****************************************************************************/
        assert(currentLexPos < toAnalyse.length());
        /****************************************************************************/
        
        StringBuilder readbuf = new StringBuilder();
        Token token;

        char c = consumeCurrentChar();
        readbuf.append(c);

        switch (c) {
        case '(':
            token = SRE_Grammar.lparenToken;
            break;
        case ')':
            token = SRE_Grammar.rparenToken;
            break;
        case '[':
            token = SRE_Grammar.lbracketToken;
            break;
        case ']':
            token = SRE_Grammar.rbracketToken;
            break;
        case ':':
            token = SRE_Grammar.concatToken;
            break;
        case '*':
            token = SRE_Grammar.kleeneToken;
            break;
        case '#':
            token = SRE_Grammar.plusclosToken;
            break;
        case '+':
            token = SRE_Grammar.choiceToken;
            break;
        case '0':
            // 0 and 1 could be weight or choice
        case '1':
            c = toAnalyse.charAt(currentLexPos);
            if (c == '.') {
                readbuf.append(consumeCurrentChar());
                token = matchProbability(readbuf);
            } else {
                token = matchWeight(readbuf);
            }
            break;
        default:
            if (Character.isLetter(c)) { // token starting with a letter must be an action
                token = matchAction(readbuf);
            } else if (Character.isDigit(c)) {
                token = matchWeight(readbuf);
            } else {
                throw new LexException(readbuf.toString());
            }
        }
        
        /****************************************************************************/
        assert(token.value().equals
                (toAnalyse.substring(currentLexPos - token.value().length(), currentLexPos)));
        /****************************************************************************/
        
        return token;
    }

    /**
     * Match alpha-numerical characters as long as possible and create a token of
     * type action.
     * 
     * @param readbuf
     * @return
     */
    private Token matchAction(StringBuilder readbuf) {

        /****************************************************************************/
        assert (readbuf != null);
        assert (readbuf.toString().matches(SRE_Grammar.actionRegEx));
        /****************************************************************************/

        while (currentLexPos < toAnalyse.length() && Character.isLetterOrDigit(toAnalyse.charAt(currentLexPos))) {
            readbuf.append(consumeCurrentChar());
        }
        Token t = new Token("action", readbuf.toString());

        /****************************************************************************/
        assert (t.value().matches(SRE_Grammar.actionRegEx));
        /****************************************************************************/

        return t;
    }

    /**
     * Match digits until we find ']' and create a token of type weight.
     * 
     * @param readbuf
     *            The substring we matched till now which should be a digit.
     * @return A token of type weight.
     */
    private Token matchWeight(StringBuilder readbuf) {

        /****************************************************************************/
        assert (readbuf != null);
        assert (readbuf.toString().matches(SRE_Grammar.weightRegEx)) : readbuf.toString();
        /****************************************************************************/

        while (currentLexPos < toAnalyse.length() && Character.isDigit(toAnalyse.charAt(currentLexPos))) {
            readbuf.append(consumeCurrentChar());
        }
        Token t = new Token("weight", readbuf.toString());

        /****************************************************************************/
        assert (t.value().matches(SRE_Grammar.weightRegEx));
        /****************************************************************************/

        return t;
    }

    /**
     * Match digits as long as possible and create a token of type probability.
     * 
     * @param readbuf
     *            The substring we already matched which should be "0." or "1".
     * @return A token of probability.
     */
    private Token matchProbability(StringBuilder readbuf) {

        /****************************************************************************/
        assert (readbuf != null);
        assert (readbuf.toString().equals("0.") || readbuf.toString().equals("1."));
        /****************************************************************************/

        while (currentLexPos < toAnalyse.length() && Character.isDigit(toAnalyse.charAt(currentLexPos))) {
            readbuf.append(consumeCurrentChar());
        }
        Token t = new Token("probability", readbuf.toString());

        /****************************************************************************/
        assert (t.value().matches(SRE_Grammar.probabilityRegEx));
        /****************************************************************************/

        return t;
    }

    private char consumeCurrentChar() {
        
        /****************************************************************************/
        assert(currentLexPos < toAnalyse.length());
        /****************************************************************************/
        
        char c = toAnalyse.charAt(currentLexPos++);
        
        /****************************************************************************/
        assert(c == toAnalyse.charAt(currentLexPos - 1));
        /****************************************************************************/

        return c;
    }
    
/*****************************************************************************
** Attributes
*****************************************************************************/

    private String toAnalyse;
    
    private int currentLexPos;
    
/*****************************************************************************
** Assertions
*****************************************************************************/
    
    private boolean tokensEqualString(List<Token> tokens, String string) {
        String tokenString = "";
        
        for(Token token : tokens) {
            tokenString += token.value();
        }
        
        return tokenString.equals(string);
    }
    
}
