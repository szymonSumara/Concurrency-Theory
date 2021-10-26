package Ex4_2_3;

import java.util.LinkedList;
import java.util.List;

public class App {

    private static final int P = 1;
    private static final int K = 2;

    public static void main(String[] args) {

        ReadingRoom buffer = new ReadingRoom();
        List<Thread> workers = new LinkedList();

        for(int i = 0 ; i < P ;i++)
            workers.add(new Writer(buffer));
        for(int i = 0 ; i < K ;i++)
            workers.add(new Reader(buffer));
        workers.forEach(Thread::start);
        workers.forEach(worker -> {
            try{
                worker.join();
            }catch(Exception e){

            }
        });

    }
}


