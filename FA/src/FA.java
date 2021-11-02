import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FA {

    private String[] Q = {};
    private String[] E = {};
    private HashMap<List<String>, List<String>> S;
    private String q0;
    private String[] F = {};

    public FA() {
        this.q0 = "";
        S = new HashMap<>();
    }

    public FA(String[] q, String[] e, HashMap<List<String>, List<String>> s, String q0, String[] f) {
        Q = q;
        E = e;
        S = s;
        this.q0 = q0;
        F = f;
    }

    public String printQ() {
        String s = "Set of states: Q = {";
        if (this.Q.length == 0) {
            s += "}";
        }
        for (int i = 0; i < Q.length; i++) {
            if (i == this.Q.length - 1) {
                s += this.Q[i] + "}\n";
            } else {
                s += this.Q[i] + ", ";
            }
        }
        return s;
    }

    public String printE() {
        String s = "Alphabet: E = {";
        if (this.E.length == 0) {
            s += "}";
        }
        for (int i = 0; i < E.length; i++) {
            if (i == this.E.length - 1) {
                s += this.E[i] + "}\n";
            } else {
                s += this.E[i] + ", ";
            }
        }
        return s;
    }

    public String printS() {
        String s = "Transitions: = {";
        if (this.S.size() == 0) {
            s += "}";
        }
        int cnt = S.size();
        for (Map.Entry<List<String>, List<String>> entry : S.entrySet()) {
            String str = "(" + entry.getKey().get(0) + ", " + entry.getKey().get(1) + ") -> ";
            cnt -= 1;
            for (int i = 0; i < entry.getValue().size(); i++)
                if (i == entry.getValue().size() - 1 && cnt == 0)
                    s += str + entry.getValue().get(i) + "}\n";
                else
                    s += str + entry.getValue().get(i) + ", ";
        }
        return s;
    }

    public String printF() {
        String s = "Set of final states: F = {";
        if (this.F.length == 0) {
            s += "}";
        }
        for (int i = 0; i < F.length; i++) {
            if (i == this.F.length - 1) {
                s += this.F[i] + "}\n";
            } else {
                s += this.F[i] + ", ";
            }
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "Q = ( {";
        for (int i = 0; i < Q.length; i++)
            if (i == Q.length - 1)
                s += Q[i] + "}, {";
            else
                s += Q[i] + ", ";
        for (int j = 0; j < E.length; j++)
            if (j == E.length - 1)
                s += E[j] + "}, {";
            else
                s += E[j] + ", ";
        int cnt = S.size();
        for (Map.Entry<List<String>, List<String>> entry : S.entrySet()) {
            String str = "(" + entry.getKey().get(0) + ", " + entry.getKey().get(1) + ") -> ";
            cnt -= 1;
            for (int i = 0; i < entry.getValue().size(); i++)
                if (i == entry.getValue().size() - 1 && cnt == 0)
                    s += str + entry.getValue().get(i) + "";
                else
                    s += str + entry.getValue().get(i) + ", ";
        }
        s += "}, " + q0 + ", {";
        for (int i = 0; i < F.length; i++)
            if (i == F.length - 1)
                s += F[i] + "} )";
            else
                s += F[i] + ", ";
        return s;
    }
}
