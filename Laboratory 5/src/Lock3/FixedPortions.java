package Lock3;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FixedPortions {

    private static final int M = 10;

    public static void main(String[] args) {
        ThreadStatisticCollector statistic = new ThreadStatisticCollector();

        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println(statistic);
                System.exit(1);
            }
        });

        Buffer buffer = new Buffer(M,statistic);
        List<Thread> workers = new LinkedList();

        workers.add(new Producer(buffer,1));
        workers.add(new Producer(buffer,1));

        workers.add(new Consumer(buffer,1));
        workers.add(new Consumer(buffer,1));
        workers.add(new Consumer(buffer,1));
        workers.add(new Consumer(buffer,1));
        workers.add(new Consumer(buffer,10));

        workers.forEach(Thread::start);
        workers.forEach(worker -> {
            try{
                worker.join();
            }catch(Exception e){

            }
        });

    }
}


