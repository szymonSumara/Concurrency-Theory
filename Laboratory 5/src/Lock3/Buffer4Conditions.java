package Lock3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer4Conditions implements IBuffer{


    int maxPortionSize;
    private int actualLength = 0;
    private final int maxLength;
    private boolean withStatistic;
    ThreadStatisticCollector statisticCollector;

    List<Integer> data = new LinkedList<Integer>();
    ReentrantLock dataLock = new ReentrantLock();


    Condition firstProducer = dataLock.newCondition();
    Condition otherProducers = dataLock.newCondition();
    boolean isFirstProducerWaiting = false;

    Condition firstConsumer = dataLock.newCondition();
    Condition otherConsumers = dataLock.newCondition();
    boolean isFirstConsumerWaiting = false;

    Buffer4Conditions(int size){
        this.maxLength = size*2;
        this.actualLength = 0;
    }

    Buffer4Conditions(int size, ThreadStatisticCollector statisticCollector){
        this.maxLength = size*2;
        this.actualLength = 0;
        this.withStatistic = true;
        this.statisticCollector = statisticCollector;
    }

    public void put( int id , int size ) throws InterruptedException{
        dataLock.lockInterruptibly();
        try{

            while(isFirstProducerWaiting) {
                if(withStatistic) this.statisticCollector.noteWait(id);
                this.otherProducers.await();
            }

            this.isFirstProducerWaiting = true;

            while(maxLength - actualLength < size) {
                if(withStatistic) this.statisticCollector.noteWait(id);
                this.firstProducer.await();
            }
            while(size>0){
                this.data.add(size);
                this.actualLength++;
                --size;
            }


            if(withStatistic) this.statisticCollector.noteAction(id);
            this.isFirstProducerWaiting = false;
            this.firstConsumer.signal();
            this.otherProducers.signal();


        }finally{
            dataLock.unlock();
        }
    }

    public List<Integer> get(int id, int size) throws InterruptedException{
        dataLock.lockInterruptibly();
        try{
            while(isFirstConsumerWaiting){
                if(withStatistic) this.statisticCollector.noteWait(id);
                this.otherConsumers.await();
            }
            this.isFirstConsumerWaiting = true;

            while( actualLength < size){
                if(withStatistic) this.statisticCollector.noteWait(id);
                this.firstConsumer.await();
            }


            List<Integer> dataToReturn  = new LinkedList<>();

            while(size --> 0){
                dataToReturn.add(this.data.remove(0));
                this.actualLength--;
            }


            if(withStatistic) this.statisticCollector.noteAction(id);
            this.isFirstConsumerWaiting = false;
            this.firstProducer.signal();
            this.otherConsumers.signal();

        }finally {
            dataLock.unlock();
        }



        return data;
    }
}
