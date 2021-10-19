package Prepare;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class App {

    private static final int M = 1000;
    private static final int P = 1;
    private static final int K = 2;

    public static void main(String[] args) {
        Buffor buffor = new Buffor(M);

        List<Thread> workers = new LinkedList();

        for(int i = 0 ; i < P ;i++)
            workers.add(new Producer(buffor, M));
        for(int i = 0 ; i < K ;i++)
            workers.add(new Consumer(buffor, M));
        workers.forEach(Thread::start);
        workers.forEach(worker -> {
            try{
                worker.join();
            }catch(Exception e){

            }
        });

    }
}


