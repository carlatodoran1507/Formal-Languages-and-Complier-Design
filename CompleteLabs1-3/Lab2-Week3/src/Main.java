import domain.scanner.NewScanner;
import domain.symbolTable.SymbolTable;

public class Main {
    public static void main(String[] args) throws Exception {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Enter file name: ");
//        String fileName = reader.readLine();

        NewScanner scanner = new NewScanner("src/input/p3.txt", new SymbolTable(29));
        scanner.runScanner();
        System.out.println(scanner);


//        Position cat = symbolTable.search("cat");
//        System.out.println("\"cat\" does not exist: " + (cat.hashTableIndex == -1) + cat);
//        cat = symbolTable.add("cat");
//        System.out.println("\"cat\" exists in the symbol table on pos: " + cat.hashTableIndex + ", " + cat.linkedListIndex);
//
//        symbolTable.add("car");
//        symbolTable.add("rac");
//        Position car = symbolTable.search("car");
//        Position rac = symbolTable.search("rac");
//
//        symbolTable.add("carla");
//
//        // Collision resolution
//        System.out.println("\n\"car\" exists in the symbol table on pos: " + car.hashTableIndex + ", " + car.linkedListIndex);
//        System.out.println("\"rac\" exists in the symbol table on pos: " + rac.hashTableIndex + ", " + rac.linkedListIndex);
//
////        PIF pif = new PIF();
////        pif.addElement("new_val", new Position(-1,-1));
////        pif.addElement("second_val", new Position(-1,1));
    }
}