import java.io.*;
import java.util.*;

public class ProofChecker {

    private InputSource lines;		// source for steps in the proof
    private TheoremSet myTheorems;	// theorem repository
    public static boolean iAmDebugging = true;
    
    // The first command-line argument, if specified, is the name of
    // a file of theorems (name/expression pairs).
    // The second command-line argument, if specified, is the name of
    // a file from which to read steps in the proof.
    // If no theorem file name is specified, proof steps are read
    // from standard input.
    
    public ProofChecker (String [ ] args) {
        // Set up input of proof steps.
        if (args.length > 1) {
            lines = new InputSource (args[1]);
        } else {
            lines = new InputSource ( );
        }
        
        myTheorems = new TheoremSet ( );
        if (args.length > 0) {
            InputSource theoremsIn = new InputSource (args[0]);
            while (true) {
                // Read a line from the theorem file.
                String line = theoremsIn.readLine ( );
                if (line == null) {
                    return;
                }
                // Turn it into a theorem and put it into the theorem collection.
                Scanner thmScanner = new Scanner (line);
                String theoremName = thmScanner.next ( );
                Expression theorem = null;
                try {
                    theorem = new Expression (thmScanner.next ( ));
                    myTheorems.put (theoremName, theorem);
                } catch (Exception e) {
                    System.err.println ("*** bad theorem file: " + args[0]);
                    System.exit (1);
                }
            }
        }   
    }

    public static void main (String [ ] args) {
        if (args.length > 2) {
            System.err.println ("too many arguments");
            System.exit (1);
        }
        ProofChecker checker = new ProofChecker (args);
        Proof soFar = new Proof (checker.myTheorems);
            
        boolean done = false;
        while (!done) {
            System.out.print (soFar.nextLineNumber ( ) + "\t");
            try {
                String line = checker.lines.readLine ( );
                // no more lines in the file (or the user typed ctrl+d)
                if (line == null) {
                    System.err.println ("Not enough lines in the proof");
                    System.exit (1);
                }
                soFar.extendProof (line);
                done = soFar.isComplete ( );
            } catch (IllegalLineException e) {
                System.out.println (e.getMessage());
            } catch (IllegalInferenceException e) {
                System.out.println (e.getMessage());
            }
        }
    }
}