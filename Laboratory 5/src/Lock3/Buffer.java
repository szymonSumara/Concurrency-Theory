package Lock3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {


    int maxPortionSize;
    private int actualLength = 0;
    private final int maxLength;
    private boolean withStatistic;
    ThreadStatisticCollector statisticCollector;

    List<Integer> data = new LinkedList<Integer>();
    ReentrantLock dataLock = new ReentrantLock();
    ReentrantLock producerLock = new ReentrantLock();
    ReentrantLock consumerLock = new ReentrantLock();

    Condition producer = dataLock.newCondition();
    Condition consumer = dataLock.newCondition();

    Buffer(int size){
        this.maxLength = size*2;
        this.actualLength = 0;
    }

    Buffer(int size, ThreadStatisticCollector statisticCollector){
        this.maxLength = size*2;
        this.actualLength = 0;
        this.withStatistic = true;
        this.statisticCollector = statisticCollector;
    }

    void toBuffer( int id , int size ){
        producerLock.lock();
        try{
            dataLock.lock();
            try {

                while (maxLength - actualLength < size) {
                    if (withStatistic) this.statisticCollector.noteWait(id);
                    this.producer.await();
                }
                while (size > 0) {
                    this.data.add(size);
                    this.actualLength++;
                    --size;
                }

                System.out.println("Buffer now contain:" + this.actualLength);

                if (withStatistic) this.statisticCollector.noteAction(id);
                this.consumer.signal();
            }catch (InterruptedException e){

            }
            finally{
                dataLock.unlock();
            }

        }finally{
            producerLock.unlock();
        }

    }

    List<Integer> fromBuffer(int id, int size){
        consumerLock.lock();
        try{
            dataLock.lock();
            try{
                while( actualLength < size){
                    if(withStatistic) this.statisticCollector.noteWait(id);
                    this.consumer.await();
                }

                List<Integer> dataToReturn  = new LinkedList<>();

                while(size --> 0){
                    dataToReturn.add(this.data.remove(0));
                    this.actualLength--;
                }
                System.out.println("Buffer now contain:" + this.actualLength);

                if(withStatistic) this.statisticCollector.noteAction(id);
                this.producer.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                dataLock.unlock();
            }

        }finally {
            consumerLock.unlock();
        }



        return data;
    }
}
