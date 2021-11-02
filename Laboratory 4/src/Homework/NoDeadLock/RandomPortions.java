package Homework.NoDeadLock;

import Homework.NoDeadLock.Buffer;
import Homework.NoDeadLock.Consumer;
import Homework.NoDeadLock.Producer;
import Homework.NoDeadLock.ThreadStatisticCollector;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.LinkedList;
import java.util.List;

public class RandomPortions {

    private static final int P = 2;
    private static final int K = 3;
    private static final int M = 100;

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

        for(int i = 0 ; i < P ;i++)
            workers.add(new Producer(buffer));
        for(int i = 0 ; i < K ;i++)
            workers.add(new Consumer(buffer));


        workers.forEach(Thread::start);
        workers.forEach(worker -> {
            try{
                worker.join();
            }catch(Exception e){

            }
        });

    }
}


