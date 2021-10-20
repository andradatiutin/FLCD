import java.util.ArrayList;
import java.util.List;

public class PIF {

    private List<List<Integer>> tokens;

    public PIF() {
        this.tokens = new ArrayList<>();
    }

    public void addToken(int code, int ST_pos) {
        List<Integer> token = new ArrayList<>(2);
        token.add(code);
        token.add(ST_pos);
        this.tokens.add(token);
    }

    @Override
    public String toString() {
        String str = "PIF \n-------------------------\n";
        for (int i = 0; i < tokens.size(); i++) {
            if (this.tokens.get(i).get(0) < 10) {
                str += this.tokens.get(i).get(0) + "     |     " + this.tokens.get(i).get(1) + "\n";
            } else {
                str += this.tokens.get(i).get(0) + "    |     " + this.tokens.get(i).get(1) + "\n";
            }
        }
        str += "-------------------------\n";
        return str;
    }
}
