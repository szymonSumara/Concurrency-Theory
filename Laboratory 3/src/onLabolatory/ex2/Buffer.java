package onLabolatory.ex2;

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


    Condition firstProducer = dataLock.newCondition();
    Condition otherProducers = dataLock.newCondition();

    Condition firstConsumer = dataLock.newCondition();
    Condition otherConsumers = dataLock.newCondition();

    Buffer(int size){
        this.maxLength = size*2;
        this.actualLength = 0;
    }


    void toBuffer( int id , int size ){
        dataLock.lock();
        try{

            while(dataLock.hasWaiters(firstProducer))
                otherProducers.await();

            while(maxLength - actualLength < size)
                firstProducer.await();

            while(size>0){
                this.data.add(size);
                this.actualLength++;
                --size;
            }
            System.out.println("Buffer now contain:" + this.actualLength);
            firstConsumer.signal();
            otherProducers.signal();

        }catch(InterruptedException e){

        }finally{
            dataLock.unlock();
        }
    }

    List<Integer> fromBuffer(int id, int size){
        dataLock.lock();
        try{
            while(dataLock.hasWaiters(firstConsumer))
                otherConsumers.await();

            while( actualLength < size)
                firstConsumer.await();

            List<Integer> dataToReturn  = new LinkedList<>();

            while(size --> 0){
                dataToReturn.add(this.data.remove(0));
                this.actualLength--;
            }
            System.out.println("Buffer now contain:" + this.actualLength);

            firstProducer.signal();
            otherConsumers.signal();

        }catch (InterruptedException e){

        }finally {
            dataLock.unlock();
        }



        return data;
    }
}
