import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymTable {
    private HashMap<Integer, List<String>> elements;
    private int m;

    public SymTable(int m) {
        this.m = m;
        this.elements = new HashMap<>();
        for (int i = 0; i < m; i++) {
            this.elements.put(i, new ArrayList<>());
        }
    }

    public int hashFunction(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += s.charAt(i);
        }
        return sum % this.m;
    }

    public int search(String s) {
        int position = hashFunction(s);
        List<String> linkedList = this.elements.get(position);
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public int add(String s) {
        int position = hashFunction(s);
        if (search(s) != -1) {
            return position;
        }
        if (this.elements.containsKey(position)) {
            this.elements.get(position).add(s);
        }
        return position;
    }

    @Override
    public String toString() {
        String str = "Symbol Table \n-------------------------\n";
        for (Map.Entry<Integer, List<String>> entry : this.elements.entrySet()) {
            if (entry.getKey() < 10) {
                str += entry.getKey() + "     |     " + entry.getValue() + "\n";
            } else {
                str += entry.getKey() + "    |     " + entry.getValue() + "\n";
            }
        }
        str += "-------------------------\n";
        return str;
    }
}
