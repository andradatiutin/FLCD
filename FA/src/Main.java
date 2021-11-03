import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static FA fa = new FA();

    public static void main(String[] args) {
        while (true) {
            menu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose an option: ");
            int option = scanner.nextInt();
            if (option == 1) {
                fa = reafFAfromfile();
            } else if (option == 2) {
                printFA(scanner);
            } else if (option == 3) {
                System.out.println(fa.isDFA());
            } else if (option == 4) {
                checkSequence(scanner);
            } else {
                System.exit(0);
            }
        }
    }

    public static void menu() {
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("1. Read the elements of a FA.");
        System.out.println("2. Display the elements of a FA.");
        System.out.println("3. Check DFA.");
        System.out.println("4. Check accepted sequence.");
    }

    public static FA reafFAfromfile() {
        String[] Q = {};
        String[] E = {};
        HashMap<List<String>, List<String>> S = new HashMap<>();
        String q0 = "";
        String[] F = {};

        try {
            BufferedReader reader = new BufferedReader(new FileReader("fa.in"));
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
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new FA(Q, E, S, q0, F);
    }

    public static void printFA(Scanner scanner) {
        while (true) {
            menuElements();
            int option = scanner.nextInt();
            if (option == 0) {
                break;
            } else if (option == 1) {
                System.out.println(fa.printQ());
            } else if (option == 2) {
                System.out.println(fa.printE());
            } else if (option == 3) {
                System.out.println(fa.printS());
            } else if (option == 4) {
                System.out.println(fa.printF());
            }
        }
    }

    public static void menuElements() {
        System.out.println();
        System.out.println("0. Exit");
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print transitions");
        System.out.println("4. Print final states");
    }

    public static void checkSequence(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Input sequence: ");
        String sequence = scanner.nextLine();
        System.out.println(fa.isAccepted(sequence));
    }
}
