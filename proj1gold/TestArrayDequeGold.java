import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void test() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();

        String log = "\n";

        boolean flag = true;
        while (flag) {
            Integer x = StdRandom.uniform(1000);
            switch (x % 4) {
                case 0 :
                    studentDeque.addFirst(x);
                    solutionDeque.addFirst(x);
                    log += "addFirst(" + x + ")\n";
                    break;
                case 1 :
                    studentDeque.addLast(x);
                    solutionDeque.addLast(x);
                    log += "addLast(" + x + ")\n";
                    break;
                case 2 :
                    if (!solutionDeque.isEmpty()) {
                        Integer n1 = studentDeque.removeFirst();
                        Integer n2 = solutionDeque.removeFirst();
                        log += "removeFirst()" + ": " + n1 + "\n";
                        assertEquals(log, n1, n2);
                    }
                    break;
                case 3 :
                    if (!solutionDeque.isEmpty()) {
                        Integer n1 = studentDeque.removeLast();
                        Integer n2 = solutionDeque.removeLast();
                        log += "removeLast()" + ": " + n1 + "\n";
                        assertEquals(log, n1, n2);
                        break;
                    }
            }
        }
    }
}
