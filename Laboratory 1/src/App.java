import java.util.LinkedList;
import java.util.List;

public class App {

    private static final int workersNumber = 100;
    private static final int operationNumber = 1000;

    public static void main(String[] args){

        Counter counter = new Counter(0);


        List<Worker> workers = new LinkedList<>();

        for(int i = 1 ; i <  workersNumber ; i++)
            workers.add(new Worker(operationNumber,counter));

        workers.forEach(Worker::start);
        workers.forEach(
                worker -> {
                    try{
                        worker.join();
                    }catch(InterruptedException e){
                        System.out.println(e.getMessage());
                    }});


        System.out.print("Thread stopped, Counter value:" + counter.getValue() + '\n');
    }
}


