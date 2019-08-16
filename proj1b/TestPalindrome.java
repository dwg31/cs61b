import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("RaceCar"));
        assertTrue(palindrome.isPalindrome("WOW"));
        assertFalse(palindrome.isPalindrome("pneumonoultramicroscopicsilicovolcanoconiosis"));
    }

    @Test
    public void testIsPalindromeOff() {
        OffByOne one = new OffByOne();
        OffByN five = new OffByN(5);
        OffByN zero = new OffByN(0);

        assertTrue(palindrome.isPalindrome("flake", one));
        assertTrue(palindrome.isPalindrome("", one));
        assertTrue(palindrome.isPalindrome("a", one));
        assertTrue(palindrome.isPalindrome("abccb", one));
        assertFalse(palindrome.isPalindrome("lol", one));
        assertFalse(palindrome.isPalindrome("pneumonoultramicroscopicsilicovolcanoconiosis", one));

        assertTrue(palindrome.isPalindrome("bidding", five));
        assertTrue(palindrome.isPalindrome("", five));
        assertTrue(palindrome.isPalindrome("a", five));
        assertTrue(palindrome.isPalindrome("linking", five));
        assertTrue(palindrome.isPalindrome("unzip", five));
        assertFalse(palindrome.isPalindrome("pneumonoultramicroscopicsilicovolcanoconiosis", five));
    }
}
//Uncomment this class once you've created your Palindrome class.