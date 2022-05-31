import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Program {
    
    private static void processHelp() {
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("?|help:\n  Prints this help message.");
        System.out.println("load {file_name}:\n  Loads the words from the give file.");
        System.out.println("most {N}:\n  List the most N frequent words.");
        System.out.println("least {N}:\n  List the least N frequent words.");
        System.out.println("search {word}:\n  Prints the frequency of the given word.");
        System.out.println("quit|exit:\n  Quit the program.");
        System.out.println();
    }
    
    public static void main(String[] args) {
        boolean done = false;
        System.out.println("Welcome to the world of WORDS!");
        Scanner console = new Scanner(System.in);
        TextProcessor textProcessor = new TextProcessor();
        
        do {
            System.out.print("Command (? for help)> ");
            String[] line = console.nextLine().split(" ");
            String command = line[0];
            String arg = line.length > 1 ? line[1] : "";
            
            try {
                switch(command.toLowerCase()) {
                case "?":
                case "help":
                    processHelp();
                    break;
                case "load":
                    textProcessor.processLoad(arg);
                    break;
                case "most":
                    textProcessor.processMost(Integer.parseInt(arg));
                    break;
                case "least":
                    textProcessor.processLeast(Integer.parseInt(arg));
                    break;
                case "search":
                    textProcessor.processSearch(arg);
                    break;
                case "quit":
                case "exit":
                    done = true;
                    break;
                case "":
                    break;
                default:
                    throw new Exception("Command not supported.");
                }
            } catch (Exception e) {
                System.out.println("##ERROR##: " + e.getMessage());
            }
        } while(!done);
        
        console.close();
        System.out.println("Goodbye!");
    }

}
