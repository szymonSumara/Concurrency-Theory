package Ex4_2_2;

import java.util.LinkedList;
import java.util.List;

public class App {

    private static final int P = 1;
    private static final int K = 2;

    public static void main(String[] args) {

        Buffer buffer = new Buffer();
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


