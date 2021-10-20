import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Codification {

    private String[] separators = {"[", "]", "{", "}", "(", ")", ",", ";"};
    private String[] operators = {"+", "-", "*", "/", "%", "=", "==", "!=", "!", "<", "<=", ">=", ">"};
    private String[] reservedWords = {"if", "else", "while", "return", "void", "int", "char", "read", "write", "public", "static", "SimonaHalep", "stop"};
    private HashMap<String, Integer> codificationTable = new HashMap<>();
    private static int index;

    public Codification() {
        codificationTable.put("identifier", 0);
        codificationTable.put("constant", 1);
        int current = 2;
        for (int i = 0; i < reservedWords.length; i++) {
            codificationTable.put(reservedWords[i], current);
            current++;
        }
        for (int i = 0; i < operators.length; i++) {
            codificationTable.put(operators[i], current);
            current++;
        }
        for (int i = 0; i < separators.length; i++) {
            codificationTable.put(separators[i], current);
            current++;
        }
    }

    public Boolean isIdentifier(String token) {
        if (token.matches("^[a-zA-Z]([a-zA-Z]|[0-9]){0,7}$")) {
            return true;
        }
        return false;
    }

    public Boolean isConstant(String token) {
        if (token.matches("^(0|[+-]?[1-9][0-9]*)$"))
            return true;
        if (token.length() == 3 && token.charAt(0) == '\'' && token.charAt(2) == '\'')
            return true;
        if (token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"')
            return true;
        return false;
    }

    public Boolean isOperator(String token) {
        for (int i = 0; i < operators.length; i++) {
            if (token.equals(operators[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean isSeparator(String token) {
        for (int i = 0; i < separators.length; i++) {
            if (token.equals(separators[i])) {
                return true;
            }
        }
        return false;
    }

    public Boolean isReservedWord(String token) {
        for (int i = 0; i < reservedWords.length; i++) {
            if (token.equals(reservedWords[i])) {
                return true;
            }
        }
        return false;
    }

    public String getStringToken(String line) { //for string in quotes
        String token = "";
        int quoteCount = 0;
        while (index < line.length() && quoteCount < 2) {
            if (line.charAt(index) == '"') {
                quoteCount += 1;
            }
            token += line.charAt(index);
            index += 1;
        }
        index -= 1;
        return token;
    }

    public String getToken(String line) {
        String token = "";
        while (index < line.length()) {
            if (isSeparator("" + line.charAt(index)) || isOperator("" + line.charAt(index)) || line.charAt(index) == ' ') {
                index--;
                return token;
            } else {
                token += line.charAt(index);
                index++;
            }
        }
        return token;
    }

    public String getOperatorToken(String line) {
        String token = "";
        token += line.charAt(index);
        if (isOperator(token + line.charAt(index + 1))) {
            index++;
            return token + line.charAt(index);
        }
        return token;
    }

    public List<String> tokenGenerator(String line) {
        List<String> token = new ArrayList<>();
        index = 0;
        while (index < line.length()) {
            if (isOperator("" + line.charAt(index))) {
                token.add(getOperatorToken(line));
            } else if (isSeparator("" + line.charAt(index))) {
                token.add("" + line.charAt(index));
            } else if (line.charAt(index) == '"') {
                token.add(getStringToken(line));
            } else if (line.charAt(index) != ' ') {
                token.add(getToken(line));
            }
            index++;
        }
        return token;
    }

    public HashMap<String, Integer> getCodificationTable() {
        return codificationTable;
    }

    @Override
    public String toString() {
        String str = "Codification Table \n-------------------------\n";
        HashMap<Integer, String> hashMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : this.codificationTable.entrySet())
            hashMap.put(entry.getValue(), entry.getKey());
        for (int i = 0; i < separators.length + operators.length + reservedWords.length + 2; i++)
            if (i < 10)
                str += i + "     |     " + hashMap.get(i) + "\n";
            else
                str += i + "    |     " + hashMap.get(i) + "\n";
        str += "-------------------------\n";
        return str;
    }
}
