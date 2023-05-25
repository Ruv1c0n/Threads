import java.util.Random;
import static java.lang.Thread.sleep;

public class Barman implements Runnable{
    @Override
    public void run() {
        try {
            Table.lock.lock();
            while ((Table.tobacco && Table.paper) || (Table.tobacco && Table.match) || (Table.match && Table.paper)){
                Table.barman.await();
            }
            int i = new Random().nextInt(3);
            switch (i) {
                case 0 -> {
                    System.out.println("Barman is pushing paper and matches");
                    Table.tobacco = false;
                    Table.paper = true;
                    Table.match = true;
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Table.tobaccoSmoker.signal();
                }
                case 1 -> {
                    System.out.println("Barman is pushing tobacco and matches");
                    Table.tobacco = true;
                    Table.paper = false;
                    Table.match = true;
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Table.paperSmoker.signal();
                }
                default -> {
                    System.out.println("Barman is pushing tobacco and paper");
                    Table.tobacco = true;
                    Table.paper = true;
                    Table.match = false;
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Table.matchSmoker.signal();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally{
            Table.lock.unlock();
        }
    }
}