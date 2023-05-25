import java.util.Random;
import static java.lang.Thread.sleep;

public class DinningPhilosophers {
public static final class Fork {}

public static class Philosopher implements Runnable {
            private final Fork LeftFork;
            private final Fork RightFork;

            public Philosopher(Fork leftFork, Fork rightFork) {
                this.LeftFork = leftFork;
                this.RightFork = rightFork;
            }

            private static void pause() {
                try {
                    sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " think");
                    pause();
                    synchronized (LeftFork) {
                        System.out.println(Thread.currentThread().getName() + " take left fork");
                        synchronized (RightFork) {
                            System.out.println(Thread.currentThread().getName() + " take right fork");
                            System.out.println(Thread.currentThread().getName() + " eat");
                            pause();
                            System.out.println(Thread.currentThread().getName() + " put down the forks");
                        }
                    }
                }
            }
        }

public static class Dinner {
            public static void main(String[] args) {
                Philosopher[] philosophers = new Philosopher[5];
                Fork[] forks = new Fork[5];
                for (int forkNum = 0; forkNum < 5; forkNum++) {
                    forks[forkNum] = new Fork();
                }
                for (int philosopherNum = 0; philosopherNum < 5; philosopherNum++) {
                    Fork leftFork = forks[philosopherNum], rightFork;
                    if ((philosopherNum - 1) >= 0) {
                        rightFork = forks[philosopherNum - 1];
                    }
                    else {
                        rightFork = forks[4];
                    }
                    philosophers[philosopherNum] = new Philosopher(leftFork, rightFork);
                    new Thread(philosophers[philosopherNum], "Philosopher " + (philosopherNum + 1)).start();
                }
            }
        }
}
