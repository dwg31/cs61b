import static java.lang.Math.abs;

/**
 * A class that implements the method which returns true if the two input characters are
 * different exactly by one.
 *
 * @author Dawei Gu
 */



public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        return abs(x - y) == 1;
    }
}