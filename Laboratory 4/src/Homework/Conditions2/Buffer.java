package Homework.Conditions2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {


    int maxPortionSize;
    private int actualLength = 0;
    private final int maxLength;
    List<Integer> data = new LinkedList<Integer>();
    ReentrantLock dataLock = new ReentrantLock();
    private boolean withStatistic;
    ThreadStatisticCollector statisticCollector;


    Condition otherProducers = dataLock.newCondition();
    Condition otherConsumers = dataLock.newCondition();

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
        dataLock.lock();
        try{

            while(maxLength - actualLength < size){
                this.statisticCollector.noteWait(id);
                this.otherProducers.await();
            }


            while(size>0){
                this.data.add(size);
                this.actualLength++;
                --size;
            }
            System.out.println("Buffer now contain:" + this.actualLength);
            this.otherConsumers.signal();
            this.statisticCollector.noteAction(id);
        }catch(InterruptedException e){

        }finally{
            dataLock.unlock();
        }
    }

    List<Integer> fromBuffer(int id, int size){
        dataLock.lock();
        try{

            while( actualLength < size){
                this.statisticCollector.noteWait(id);
                this.otherConsumers.await();
            }

            List<Integer> dataToReturn  = new LinkedList<>();

            while(size --> 0){
                dataToReturn.add(this.data.remove(0));
                this.actualLength--;
            }
            System.out.println("Buffer now contain:" + this.actualLength);

            this.otherProducers.signal();
            this.statisticCollector.noteAction(id);

        }catch (InterruptedException e){

        }finally {
            dataLock.unlock();
        }

        return data;
    }
}
