import domain.finiteAutomata.FiniteAutomata;
import domain.scanner.NewScanner;
import domain.symbolTable.SymbolTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
//        NewScanner scanner = new NewScanner("src/input/p2.txt", new SymbolTable(29));
//        scanner.runScanner();
//        System.out.println(scanner);

        FiniteAutomata FA = new FiniteAutomata("D:/Lab2-Week3/src/input/FA.in");
        FA.readFromFile();

        while (true) {
            optionsMenu();
            String command = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter option: ");
            command = reader.readLine();

            switch (command) {
                case "1":
                    System.out.println("States: ");
                    System.out.println(FA.getSetOfStates());
                    System.out.println("\n");
                    break;
                case "2":
                    System.out.println("Alphabet: ");
                    System.out.println(FA.getAlphabet());
                    System.out.println("\n");
                    break;
                case "3":
                    System.out.println("Transitions: ");
                    System.out.println(FA.getTransitionsList());
                    System.out.println("\n");
                    break;
                case "4":
                    System.out.println("Initial state: ");
                    System.out.println(FA.getInitialState());
                    System.out.println("\n");
                    break;
                case "5":
                    System.out.println("Final states: ");
                    System.out.println(FA.getFinalStates());
                    System.out.println("\n");
                    break;
                case "6":
                    String message = FA.isDFA() ? "is a DFA" : "is NOT a DFA";
                    System.out.println("The introduced FA " + message);
                    System.out.println("\n");
                    break;
                case "7":
                    if(!FA.isDFA()){
                        System.out.println("FA needs to be a DFA.");
                        break;
                    }
                    BufferedReader secondReader =  new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Enter sequence: ");
                    String sequence = secondReader.readLine();
                    String messageSequence = FA.verifySequence(sequence) ? "Sequence is accepted" : "Sequence is not accepted";
                    System.out.println(messageSequence);
                    System.out.println("\n");
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.err.println("Invalid option");
                    break;
            }
        }
    }

    private static void optionsMenu() {
        System.out.println("1 - Print states");
        System.out.println("2 - Print alphabet");
        System.out.println("3 - Print transitions");
        System.out.println("4 - Print initial state");
        System.out.println("5 - Print final states");
        System.out.println("6 - Verify if is DFA");
        System.out.println("7 - Verify if a sequence is accepted by FA");
        System.out.println("0 - Exit \n");
    }
}