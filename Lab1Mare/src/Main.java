import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Codification codification = new Codification();
        PIF pif = new PIF();
        SymTable symTable = new SymTable(26);
        FA faConstant = new FA();
        faConstant = reafFAfromfile("constant.in");
        FA faIdentifier = new FA();
        faIdentifier = reafFAfromfile("identifier.in");
        int lineNumber = 1;
        int ok = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ex3.txt"));
            String text = reader.readLine();
            while (text != null) {
                List<String> tokens = codification.tokenGenerator(text);
                for (int i = 0; i < tokens.size(); i++) {
                    String s = tokens.get(i);
                    if (codification.isSeparator(s) || codification.isReservedWord(s) || codification.isOperator(s)) {
                        pif.addToken(codification.getCodificationTable().get(s), -1);
                    } else if (faIdentifier.isAccepted(s)) {
                        pif.addToken(0, symTable.add(s));
                    } else if (codification.isConstant(s)) {
                        pif.addToken(1, symTable.add(s));
                    } else {
                        System.out.println("Lexical error: " + s + " at location " + lineNumber);
                        ok = 0;
                    }
                }
                text = reader.readLine();
                lineNumber++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (ok == 1) {
            System.out.println("Lexically correct!");
            BufferedWriter writer = new BufferedWriter(new FileWriter("pif.out"));
            BufferedWriter writer1 = new BufferedWriter(new FileWriter("ST.out"));
            writer.write(pif.toString());
            writer.newLine();
            writer.close();
            writer1.write(symTable.toString());
            writer1.newLine();
            writer1.close();
        }
        System.out.println(pif);
        System.out.println(symTable);
        System.out.println(codification);
    }

    public static FA reafFAfromfile(String file) {
        String[] Q = {};
        String[] E = {};
        HashMap<List<String>, List<String>> S = new HashMap<>();
        String q0 = "";
        String[] F = {};

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String text = reader.readLine();
            while (text != null) {
                text = text.replace(" ", "");
                String[] elements = text.split("=");
                String terms = elements[1].replace("{", "").replace("}", "");
                if (elements[0].equals("Q")) {
                    Q = terms.split(",");
                } else if (elements[0].equals("E")) {
                    E = terms.split(",");
                } else if (elements[0].equals("S")) {
                    String[] t = terms.replace("(", "|").split(",\\|");
                    for (int i = 0; i < t.length; i++) {
                        String[] r = t[i].split("->");
                        String[] left = r[0].replace("|", "").replace(")", "").split(",");
                        List<String> key = new ArrayList<>();
                        for (int j = 0; j < left.length; j++) {
                            key.add(left[j]);
                        }
                        if (S.containsKey(key)) {
                            S.get(key).add(r[1]);
                        } else {
                            List<String> value = new ArrayList<>();
                            value.add(r[1]);
                            S.put(key, value);
                        }
                    }
                } else if (elements[0].equals("q0"))
                    q0 = terms;
                else if (elements[0].equals("F"))
                    F = terms.split(",");
                else System.out.println("Invalid token!");
                text = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new FA(Q, E, S, q0, F);
    }
}