import static java.lang.Thread.sleep;

public class TobaccoSmoker implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                Table.lock.lock();
                while (!Table.paper && !Table.match) {
                    try {
                        Table.tobaccoSmoker.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Tobacco smoker are making and smoking cigarette");
                Table.tobacco = false;
                Table.paper = false;
                Table.match = false;
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Table.barman.signal();
            } finally {
                Table.lock.unlock();
            }
        }
    }
}