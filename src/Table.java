import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    static boolean tobacco = false;
    static boolean paper = false;
    static boolean match = false;
    static final Lock lock = new ReentrantLock();
    static final Condition barman = lock.newCondition();
    static final Condition tobaccoSmoker = lock.newCondition();
    static final Condition paperSmoker = lock.newCondition();
    static final Condition matchSmoker = lock.newCondition();
    public static void main(String[] args) {
        new Thread(new Barman()).start();
        new Thread(new TobaccoSmoker()).start();
        new Thread(new PaperSmoker()).start();
        new Thread(new MatchSmoker()).start();
    }
}