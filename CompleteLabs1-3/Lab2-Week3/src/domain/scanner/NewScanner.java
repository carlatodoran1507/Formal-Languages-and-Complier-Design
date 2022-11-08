package domain.scanner;

import domain.pif.PIF;
import domain.symbolTable.Position;
import domain.symbolTable.SymbolTable;
import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewScanner {
    private final String fileName;
    private List<String> reservedWords;
    private List<String> separators;
    private List<String> operators;
    private final List<String> reservedTokens;
    public PIF pif;
    private String STOutput;
    private String PIFOutput;
    public SymbolTable symbolTable;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public NewScanner(String fileName, SymbolTable symbolTable) {
        this.fileName = fileName;
        this.symbolTable = symbolTable;
        this.pif = new PIF();
        readReservedTokens();
        this.reservedTokens = Stream.of(this.separators, this.operators, this.reservedWords)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        STOutput = "";
        PIFOutput = "";
    }

    public void readReservedTokens() {
        try {
            File file = new File("src/input/token.in");
            Scanner reader = new Scanner(file);
            String separators = reader.nextLine();
            this.separators = Arrays.stream(separators.split("_")).toList();
            String operators = reader.nextLine();
            this.operators = Arrays.stream(operators.split(",")).toList();
            String reservedWords = reader.nextLine();
            this.reservedWords = Arrays.stream(reservedWords.split(",")).toList();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred" + e);
            e.printStackTrace();
        }
    }

    public void runScanner() throws Exception {
        File file = new File(this.fileName);
        Scanner reader = new Scanner(file);
        int currentLine = 1;
        while (reader.hasNextLine()) {
            String line = reader.nextLine().strip();
            List<String> tokens = this.getLineTokens(line);
            for (String token : tokens) {
                if (this.reservedTokens.contains(token)) {
                    this.pif.addElement(token, new Position(-1, -1));
                    PIFOutput = PIFOutput.concat(new Position(-1, -1) + " " + token + "\n");

                } else if (this.isIdentifier(token)) {
                    Position position = this.symbolTable.search(token);
                    if (position.hashTableIndex == -1) {
                        position = this.symbolTable.add(token);
                        STOutput = STOutput.concat(position + " " + token + "\n");
                    }

                    this.pif.addElement("IDENTIFIER", position);
                    PIFOutput = PIFOutput.concat(position + " IDENTIFIER" + "\n");

                } else if (this.isConstant(token)) {
                    Position position = this.symbolTable.search(token);
                    if (position.hashTableIndex == -1) {
                        position = this.symbolTable.add(token);
                        STOutput = STOutput.concat(position + " " + token + "\n");
                    }

                    this.pif.addElement("CONSTANT", position);
                    PIFOutput = PIFOutput.concat(position + " CONSTANT" + "\n");

                } else {
//                    System.out.println(ANSI_RED + "Unknown token " + token + " at line " + currentLine + ANSI_RESET);
                    throw new Exception("Unknown token " + token + " at line " + currentLine);
                }
            }
            System.out.println(line);
            currentLine += 1;
        }
        reader.close();
        writeToFile();
    }

    private boolean isIdentifier(String value) {
        return value.matches("^[a-zA-Z]([a-zA-Z]|[0-9])*$");
    }

    private boolean isConstant(String value) {
        boolean isBoolean = value.equals("true") || value.equals("false");
        boolean isNumber = value.matches("^0$|^(([+\\-])?[1-9](0|[0-9])*)$");
        boolean isString = value.matches("^([\"'])([ a-zA-Z0-9])*[\"']$");
        return isNumber || isString || isBoolean;
    }

    private boolean isPartOfOperator(char character) {
        for (String operator : this.operators)
            if (operator.contains("" + character))
                return true;
        return false;
    }

    private boolean isEscapedQuote(String line, Integer index) {
        return (index != 0) && line.charAt(index - 1) == '\\';
    }


    private Pair<String, Integer> getStringToken(String line, Integer index, char quote) {
        String token = "";
        int quoteCount = 0;

        while (index < line.length() && quoteCount < 2) {
            if (line.charAt(index) == quote && !isEscapedQuote(line, index)) {
                quoteCount += 1;
            }
            token += line.charAt(index);
            index += 1;
        }
        return new Pair<>(token, index);
    }

    private Pair<String, Integer> getOperatorToken(String line, Integer index) {
        String token = "";
        while (index < line.length() && this.isPartOfOperator(line.charAt(index))) {
            token += line.charAt(index);
            index += 1;
        }
        return new Pair<>(token, index);
    }

    private List<String> getLineTokens(String line) {
        List<String> tokens = new ArrayList<>();
        String token = "";
        int index = 0;
        while (index < line.length()) {
            if (line.charAt(index) == '"' || line.charAt(index) == '\'') {
                if (!token.equals("")) {
                    tokens.add(token);
                }
                Pair<String, Integer> res = this.getStringToken(line, index, line.charAt(index));
                token = res.getKey();
                index = res.getValue();
                tokens.add(token);
                token = "";
            } else if (this.isPartOfOperator(line.charAt(index))) {
                if (!token.equals("")) {
                    tokens.add(token);
                }
                Pair<String, Integer> res = this.getOperatorToken(line, index);
                token = res.getKey();
                index = res.getValue();
                tokens.add(token);
                token = "";
            } else if (this.separators.contains("" + line.charAt(index))) {
                if (!token.equals("")) {
                    tokens.add(token);
                }
                token = "" + line.charAt(index);
                index += 1;
                tokens.add(token);
                token = "";
            } else {
                token += line.charAt(index);
                index += 1;
            }
        }
        if (!token.equals("")) {
            tokens.add(token);
        }
        return tokens;
    }

    private void writeToFile() throws IOException {
        BufferedWriter STWriter = new BufferedWriter(
                new FileWriter("D:/Lab2-Week3/output/ST.out", false));
        BufferedWriter PIFWriter = new BufferedWriter(
                new FileWriter("D:/Lab2-Week3/output/PIF.out", false));
        STWriter.write(STOutput);
        PIFWriter.write(PIFOutput);
        STWriter.close();
        PIFWriter.close();
    }

    @Override
    public String toString() {
        return "NewScanner {" +
                " fileName= '" + fileName + '\'' +
                "\n reservedWords= " + reservedWords +
                "\n separators= " + separators +
                "\n operators= " + operators +
                "\n reservedTokens= " + reservedTokens +
                "\n pif= " + pif +
                "\n symbolTable= " + symbolTable +
                '}';
    }
}
