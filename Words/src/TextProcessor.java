import java.io.File;
import java.util.Scanner;

/**
 * Class loading text from a file and extracting specific
 * statistics such as words frequency.
 */
public class TextProcessor {
    
    /* TODO: Define a HashMap mapping each word to its frequency.
     */
    
    /**
     * Loads the text from a file
     */
    public void processLoad(String arg) throws Exception {
        File f = new File(arg);
        if (!f.exists()) {
            throw new Exception("Missing or invalid argument.");
        }
        
        System.out.printf("Loading text from '%s'\n", f.getAbsolutePath());

        /* TODO: Insert code for loading the text from the file into
         * the internal map. */

        int lineNo = 0;
        Scanner reader = new Scanner(f, "UTF-8");
        while(reader.hasNextLine()) {
            lineNo++;
            String line = reader.nextLine();
            System.out.println(line);
        }
        System.out.println(lineNo);
        reader.close();
    }
    
    /**
     * Prints the most frequent n words from the text.
     */
    public void processMost(int n) {
        System.out.printf("Printing most frequent %d words:\n", n);
        
        /* TODO: Insert code for iterating through the map
         * and print the most frequent n words.
         */
    }
    
    /**
     * Prints the lest frequent n words from the text.
     */
    public void processLeast(int n) {
        System.out.printf("Printing least frequent %d words:\n", n);

        /* TODO: Insert code for iterating through the map
         * and print the least frequent n words.
         */
    }
    
    /**
     * Prints the frequency in the text of the given word.
     */
    public void processSearch(String word) {
        System.out.printf("Searching frequency of word '%s'...\n", word);
        
        /* TODO: Insert code for looking up word in the map
         * and print its frequency value.
         */
    }
}
