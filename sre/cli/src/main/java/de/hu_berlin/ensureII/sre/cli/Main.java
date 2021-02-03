package de.hu_berlin.ensureII.sre.cli;

import de.hu_berlin.ensureII.sre.generator.Generator;
import de.hu_berlin.ensureII.sre.lexer.Lexer;
import de.hu_berlin.ensureII.sre.parser.Parser;

public class Main {

    public static void main(String[] args) {
        
        if(args.length < 1) {
            showUsageInfo();
            System.exit(-1);
        }
        
        long exect = System.nanoTime();
        
        String[] subArgs = new String[args.length - 1];
        for(int i=1; i<args.length; i++) {
            subArgs[i-1] = args[i];
        }
        
        switch(args[0]) {
        case "generator":
            Generator.main(subArgs);
            break;
        case "lexer":
            Lexer.main(subArgs);
            break;
        case "parser":
            Parser.main(subArgs);
            break;
        default:
            showUsageInfo();
        }
        
        exect = System.nanoTime() - exect;
        System.out.println("Took " + exect/1000000 + "ms.");
    }
    
    private static void showUsageInfo() {
        System.out.println("first argument should be 'parser', 'generator' or 'lexer'.");
    }
}
