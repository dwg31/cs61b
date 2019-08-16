/**
 * A class that checks if a word is a palindrome or off-by-N palindrome
 * @author Dawei Gu
 */


public class Palindrome {

    /* Given a String, wordToDeque should return a Deque where
     * the characters appear in the same order as in the String.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> characters = new ArrayDeque<>();
        int wordLength = word.length();
        for (int i = 0; i < wordLength; i += 1) {
            characters.addLast(word.charAt(i));
        }
        return characters;
    }

    /* Helper method for isPalindrome() */
    private boolean compareChar(Deque<Character> characters) {
        if (characters.size() == 0 || characters.size() == 1) {
            return true;
        }

        if (characters.removeFirst() == characters.removeLast()) {
            return compareChar(characters);
        } else {
            return false;
        }
    }

    /* Return true if the given word is a palindrome, and false otherwise.
     */
    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            Deque<Character> characters = wordToDeque(word);
            return compareChar(characters);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            Deque<Character> characters = wordToDeque(word);
            while (characters.size() >= 2) {
                char first = characters.removeFirst();
                char last =  characters.removeLast();
                if (!cc.equalChars(first, last)) {
                    return false;
                }
            }
            return true;
        }
    }
}
