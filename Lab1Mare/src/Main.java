import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Codification codification = new Codification();
        PIF pif = new PIF();
        SymTable symTable = new SymTable(26);
        int lineNumber = 1;
        int ok = 1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("FLCD/Lab1Mare/ex1.txt"));
            String text = reader.readLine();
            while (text != null) {
                List<String> tokens = codification.tokenGenerator(text);
                for (int i = 0; i < tokens.size(); i++) {
                    String s = tokens.get(i);
                    if (codification.isSeparator(s) || codification.isReservedWord(s) || codification.isOperator(s)) {
                        pif.addToken(codification.getCodificationTable().get(s), -1);
                    } else if (codification.isIdentifier(s)) {
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
        if(ok==1){
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
}