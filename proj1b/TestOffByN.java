import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offByFive = new OffByN(5);
    static CharacterComparator offByOne = new OffByN(1);

    @Test
    public void testEqualChars() {
        assertFalse(offByOne.equalChars('a', 'c'));
        assertTrue(offByOne.equalChars('d', 'c'));
        assertTrue(offByOne.equalChars('a', 'b'));

        assertFalse(offByFive.equalChars('a', 'c'));
        assertTrue(offByFive.equalChars('f', 'a'));
        assertTrue(offByFive.equalChars('o', 't'));
    }
}
