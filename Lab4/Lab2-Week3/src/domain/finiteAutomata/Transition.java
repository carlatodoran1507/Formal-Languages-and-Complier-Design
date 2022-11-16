package domain.finiteAutomata;

import java.util.List;

public class Transition {
    private String fromState;
    private String symbol;
    private List<String> toStates;

    public Transition() {}

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setToStates(List<String> toStates) {
        this.toStates = toStates;
    }

    public String getFromState() {
        return fromState;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<String> getToStates() {
        return toStates;
    }

    @Override
    public String toString() {
        return "O(" + fromState + "," + symbol + ") = " + toStates;
    }
}