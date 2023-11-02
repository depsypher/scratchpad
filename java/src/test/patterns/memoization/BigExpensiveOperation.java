package patterns.memoization;

public class BigExpensiveOperation implements ExpensiveOperation {

    @Override
    public int doExpensiveOperation(int i) {
        System.out.println("Invoking BigExpensiveOperation.doExpensiveOperation with " + i);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i + 1;
    }
}