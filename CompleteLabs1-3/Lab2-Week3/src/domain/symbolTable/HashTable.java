package domain.symbolTable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class HashTable {
    private final Integer size;
    private final Node[] elements;

    public HashTable(Integer size) {
        this.size = size;
        elements = new Node[size];
    }

    private int hashFunction(String value) {
        int sum = 0;
        for (char character: value.toCharArray()) {
            sum += (int) character;
        }
        return sum % this.size;
    }

    public Position addElement(String value) {
        Integer hashValue = this.hashFunction(value);
        if (this.elements[hashValue] == null) {
            this.elements[hashValue] = new Node(value, 0);
            return new Position(hashValue, 0);
        }
        /// else
        Node lastNode = this.elements[hashValue];
        while (lastNode.nextNode != null) {
            lastNode = lastNode.nextNode;
        }
        Node newNode = new Node(value, lastNode.index + 1);
        lastNode.nextNode = newNode;
        return new Position(hashValue, newNode.index);
    }

    public Position searchElement(String value) {
        Integer hashValue = this.hashFunction(value);
        Node currentNode = this.elements[hashValue];
        if (currentNode != null) {
            while (currentNode != null) {
                if (currentNode.value.equals(value)) {
                    return new Position(hashValue, currentNode.index);
                }
                currentNode = currentNode.nextNode;
            }
        }
        return new Position(-1, -1);
    }

    @Override
    public String toString() {
        Object[] notNullElements = Arrays.stream(elements).filter(Objects::nonNull).toArray();
        return Arrays.toString(notNullElements);
    }
}
