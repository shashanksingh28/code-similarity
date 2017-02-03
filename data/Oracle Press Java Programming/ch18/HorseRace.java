
import java.util.*;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

public class HorseRace {

    private final int NUMBER_OF_HORSES = 12;
    private final static int INIT_PARTIES = 1;
    private final static Phaser manager = new Phaser(INIT_PARTIES);

    public static void main(String[] args) {
        Thread raceMonitor = new Thread(new RaceMonitor());
        raceMonitor.setDaemon(true);
        raceMonitor.start();
        new HorseRace().manageRace();
    }

    public void manageRace() {
        ArrayList<Horse> horseArray = new ArrayList<Horse>();
        for (int i = 0; i < NUMBER_OF_HORSES; i++) {
            horseArray.add(new Horse());
        }
        runRace(horseArray);
    }

    private void runRace(Iterable<Horse> team) {
        log("Assign all horses, then start race");
        for (final Horse horse : team) {
            final String dev = horse.toString();
            log("assign " + dev + " to the race");
            manager.register();
            new Thread() {

                @Override
                public void run() {
                    try {
                        Thread.sleep((new Random()).nextInt(1000));
                    } catch (InterruptedException ex) {
                    }
                    log(dev + ", please await all horses");
                    manager.arriveAndAwaitAdvance();
                    horse.run();
                }
            }.start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        log("All arrived at starting gate, start race");
        manager.arriveAndDeregister();
    }

    private static void log(String msg) {
        System.out.println(msg);
    }

    private static class Horse implements Runnable {

        private final static AtomicInteger idSource = new AtomicInteger();
        private final int id = idSource.incrementAndGet();

        @Override
        public void run() {
            log(toString() + ": running");
        }

        @Override
        public String toString() {
            return "horse #" + id;
        }
    }

    private static class RaceMonitor implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println("Number of horses ready to run: "
                        + HorseRace.manager.getArrivedParties());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}