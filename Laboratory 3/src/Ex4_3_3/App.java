package Ex4_3_3;


import java.util.LinkedList;
import java.util.List;


public class App {

    private static final int M = 1000;
    private static final int P = 3;
    private static final int K = 3;

    public static void main(String[] args) {
        Buffer buffer = new Buffer(M);

        List<Thread> workers = new LinkedList();

        for(int i = 0 ; i < P ;i++)
            workers.add(new Producer(buffer, M));
        for(int i = 0 ; i < K ;i++)
            workers.add(new Consumer(buffer, M));
        workers.forEach(Thread::start);
        workers.forEach(worker -> {
            try{
                worker.join();
            }catch(Exception e){

            }
        });

    }
}


