package Homework.Conditions4;

import Homework.Conditions4.Buffer;
import Homework.Conditions4.Consumer;
import Homework.Conditions4.Producer;
import Homework.Conditions4.ThreadStatisticCollector;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.LinkedList;
import java.util.List;

public class FixedPortions {

    private static final int M = 10;

    public static void main(String[] args) {

        Homework.Conditions4.ThreadStatisticCollector statistic = new ThreadStatisticCollector();

        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println(statistic);
                System.exit(1);
            }
        });

        Homework.Conditions4.Buffer buffer = new Buffer(M,statistic);
        List<Thread> workers = new LinkedList();

        workers.add(new Homework.Conditions4.Producer(buffer,1));
        workers.add(new Homework.Conditions4.Producer(buffer,1));

        workers.add(new Homework.Conditions4.Consumer(buffer,1));
        workers.add(new Homework.Conditions4.Consumer(buffer,1));
        workers.add(new Homework.Conditions4.Consumer(buffer,1));
        workers.add(new Homework.Conditions4.Consumer(buffer,1));
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


