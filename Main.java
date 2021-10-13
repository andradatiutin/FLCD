public class Main {

    public static void main(String[] args) {
        SymTable symTable = new SymTable(12);
        symTable.add("1");
        symTable.add("variable");
        symTable.add("elbairav");
        symTable.add("Simona");
        symTable.add("Halep");
        symTable.add("1");
        System.out.println(symTable);
        System.out.println(symTable.search("Simona"));
        System.out.println(symTable.search("Halep"));
    }
}
