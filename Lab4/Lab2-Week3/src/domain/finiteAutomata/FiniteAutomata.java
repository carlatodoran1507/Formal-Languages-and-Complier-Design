package domain.finiteAutomata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    private List<String> setOfStates;
    private List<String> alphabet;
    private final List<Transition> transitionsList;
    private List<String> finalStates;
    private String initialState;
    private final String fileName;

    public FiniteAutomata(String fileName) {
        this.fileName = fileName;
        this.setOfStates = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitionsList = new ArrayList<>();
        this.finalStates = new ArrayList<>();
    }

    public List<String> getSetOfStates() {
        return setOfStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<Transition> getTransitionsList() {
        return transitionsList;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public String getInitialState() {
        return initialState;
    }

    public void readFromFile() throws FileNotFoundException {
        File file = new File(this.fileName);
        Scanner scanner = new Scanner(file);

        String setOfStates = scanner.nextLine();
        this.setOfStates = Arrays.asList(setOfStates.split(","));
        String alphabet = scanner.nextLine();
        this.alphabet = Arrays.asList(alphabet.split(","));
        String transition = "";

        while (true) {
            transition = scanner.nextLine();
            if (transition.equals("FIN")) {
                break;
            }

            List<String> transitions = Arrays.asList(transition.split(","));
            Transition model = new Transition();
            model.setFromState(transitions.get(0));
            model.setSymbol(transitions.get(1));
            List<String> endStates = new ArrayList<>();
            for (int i = 2; i < transitions.size(); i++) {
                endStates.add(transitions.get(i));
            }
            model.setToStates(endStates);
            this.transitionsList.add(model);
        }

        String finalStates = scanner.nextLine();
        this.finalStates = Arrays.asList(finalStates.split(","));
        this.initialState = scanner.nextLine();

        scanner.close();
    }

    public boolean isDFA() {
        for (Transition transition: transitionsList) {
            if (transition.getToStates().size() > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean verifySequence(String seq) {
        String currentState = this.initialState;
        String[] sequence = seq.split("");
        for (String character : sequence) {
            String nextState = nextState(currentState, character);
            if (nextState.equals("No next state")) {
                return false;
            }
            currentState = nextState;
        }
        // verify that last state is a final state
        return this.finalStates.contains(currentState);
    }

    private String nextState(String startState, String symbol) {
        for (Transition transition: transitionsList) {
            if (transition.getFromState().equals(startState) && transition.getSymbol().equals(symbol))
                if (transition.getToStates().size() == 1)
                    return transition.getToStates().get(0);
        }
        return "No next state";
    }
}