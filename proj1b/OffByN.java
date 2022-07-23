public class OffByN implements CharacterComparator{
    private int N;
    OffByN(int n) {
        N = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return (x - y) == N || (x - y ) == -N;
    }
}
