package domain.pif;

import domain.symbolTable.Position;

public class PIFUnit {
    public String value;
    public Position symTablePos;

    public PIFUnit(String value, Position symTablePos) {
        this.value = value;
        this.symTablePos = symTablePos;
    }

    @Override
    public String toString() {
        return "PIFUnit{" +
                "value='" + value + '\'' +
                ", symTablePos=" + symTablePos +
                '}';
    }
}
