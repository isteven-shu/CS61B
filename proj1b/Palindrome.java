public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); ++i) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        return isPalindromeHelper(d);
    }

    private boolean isPalindromeHelper(Deque<Character> d) {
        if (d.size() == 1 || d.size() == 0) {
            return true;
        }
        if (d.removeFirst() == d.removeLast()) {
            return isPalindromeHelper(d);
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        return isPalindromeHelper(d, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> d, CharacterComparator cc) {
        if (d.size() == 1 || d.size() == 0) {
            return true;
        }
        if (cc.equalChars(d.removeFirst(), d.removeLast())) {
            return isPalindromeHelper(d, cc);
        }
        return false;
    }
}
