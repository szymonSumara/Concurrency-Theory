import activeObject.Proxy;
import basic.ThreadStatisticCollector;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ProcessStarvation {

    private static final int P = 3;
    private static final int K = 1;
    private static final int M = 100;
    private static final int C = 100;


    public static void main(String[] args) {

        Proxy buffer =  new Proxy(200);
        List<Thread> workers = new LinkedList();
        WorkersFactory workersFactory = new WorkersFactory();
        workersFactory
                .setAsyncBuffer(buffer)
                .setOffsideOperationNumber(1000000)
                .setPortionSize(1);
        try{
            workers.add(workersFactory.createProducer());
            workers.add(workersFactory.createProducer());
            workers.add(workersFactory.createConsumer());
            workers.add(workersFactory.createConsumer());
            workers.add(workersFactory.createConsumer());
            workers.add(workersFactory.createConsumer());
            workers.add(workersFactory
                            .setPortionSize(10)
                            .createConsumer()
                        );
        }catch(Exception e){
            System.out.println(e);
        }


        workers.forEach(Thread::start);
        try
        {
            Thread.sleep(10000);
            buffer.stop();
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

        try {
            Consumer.SaveInfo();
        }catch (IOException e){

        }

        System.out.println(ThreadStatisticCollector.instance);

    }
}
