package domain.symbolTable;

public class Node {
    public String value; // identifiers and constants always stored as a string
    public Integer index;
    public Node nextNode;

    public Node(String value, Integer index) {
        this.value = value;
        this.index = index;
        this.nextNode = null;
    }

    @Override
    public String toString() {
        return "\nNode (" +
                "value: '" + value + '\'' +
                ", index: " + index +
                ", nextNode: " + nextNode +
                ")";
    }
}
