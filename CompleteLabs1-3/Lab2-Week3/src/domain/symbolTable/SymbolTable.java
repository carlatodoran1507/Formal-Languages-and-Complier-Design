package domain.symbolTable;

import domain.symbolTable.HashTable;
import domain.symbolTable.Position;

public class SymbolTable {
    HashTable hashTable;

    public SymbolTable(Integer size) {
        this.hashTable = new HashTable(size); // prime number
    }

    public Position add(String value) {
        return hashTable.addElement(value);
    }

    public Position search(String value) {
        return hashTable.searchElement(value);
    }

    @Override
    public String toString() {
        return "SymbolTable {" +
                " HashTable = " + hashTable +
                '}';
    }
}
