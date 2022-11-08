package domain.pif;

import domain.symbolTable.Position;

import java.util.ArrayList;
import java.util.List;

public class PIF {
    public List<PIFUnit> table;

    public PIF() {
        this.table = new ArrayList<>();
    }

    public void addElement(String value, Position symTablePos) {
        this.table.add(new PIFUnit(value, symTablePos));
    }

    @Override
    public String toString() {
        return "PIF{" +
                "table=" + table +
                '}';
    }
}
