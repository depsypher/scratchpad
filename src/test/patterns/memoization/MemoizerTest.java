package patterns.memoization;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Demonstrates usage of the Memoizer
 *
 * @author rayvanderborght
 */
public class MemoizerTest {

    @Test
    public void testMemoizer() throws Exception {
        ExpensiveOperation op = new Memoizer<ExpensiveOperation>().build(new BigExpensiveOperation());

        long start = System.currentTimeMillis();
        int total = 0;
        for (int i = 0; i < 100; i++) {
            int input = i % 10;
            System.out.println(i + ", " + input + ", " + total);
            total += op.doExpensiveOperation(input);
        }
        assertEquals(550, total);
        System.out.println("time=" + (System.currentTimeMillis() - start));
    }
}
