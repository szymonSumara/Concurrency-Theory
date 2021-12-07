package Lock3;

import java.util.LinkedList;
import java.util.List;

public class ExperimentStep {

    private IBuffer buffer;
    private List<Thread> workers;
    private ThreadStatisticCollector statistic;

    public ExperimentStep(IBuffer buffer, int consumersNumber, int producersNumber){

        this.buffer =  new Buffer3Lock(100,statistic);
       this.workers = new LinkedList();

        for(int i = 0 ; i < consumersNumber ;i++)
            workers.add(new Producer(buffer, i));
        for(int i = 0 ; i < producersNumber ;i++)
            workers.add(new Consumer(buffer, i));

        this.statistic = new ThreadStatisticCollector();
    }

    void  start(){
        workers.forEach(Thread::start);

        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        for(Thread worker : workers){

            try{
                worker.interrupt();
                worker.join();
            }catch(Exception e){
                System.out.println("Exeption" + e);
            }
        }
    }


}
