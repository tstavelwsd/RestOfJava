import java.util.Scanner;

public class Program {
    
    private static void processHelp() {
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("?|help:\n  Prints this help message.");
        System.out.println("add:\n  Add a word definition to the database.");
        System.out.println("list:\n  List all words in the database.");
        System.out.println("search:\n  Search the definition of a given word.");
        System.out.println("quit|exit:\n  Quit the program.");
        System.out.println();
    }
    
    private static void processAdd(Scanner console) {
        System.out.print("Enter word:> ");
        String word = console.nextLine();
        System.out.print("Enter definition:> ");
        String definition = console.nextLine();
        word = word.replaceAll(",|:|;|\\.", "");
        System.out.println(word);
    }

    public static void main(String[] args) {
        boolean done = false;
        System.out.println("Welcome to the world of WORDS!");
        Scanner console = new Scanner(System.in);
        
        do {
            System.out.print("Command (? for help)> ");
            String command = console.nextLine();
            switch(command.toLowerCase()) {
            case "?":
            case "help":
                processHelp();
                break;
            case "add":
                processAdd(console);
                break;
            case "list":
                break;
            case "search":
                break;
            case "quit":
            case "exit":
                done = true;
                break;
            case "":
                break;
            default:
                System.out.printf("Command '%s' is not supported.\n", command);
            }
        } while(!done);
        
        System.out.println("Goodbye!");
    }

}
