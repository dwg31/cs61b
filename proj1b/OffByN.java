import static java.lang.Math.abs;

/**
 * A class that implements the method which returns true if the two input characters are
 * off by (int)N
 *
 * @author Dawei Gu
 */

public class OffByN implements CharacterComparator{

    private int N;

    public OffByN(int n) {
        N = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return abs(x - y) == N;
    }
}
