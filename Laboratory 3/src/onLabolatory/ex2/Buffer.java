package onLabolatory.ex2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {


    int maxPortionSize;
    private int actualLength = 0;
    private final int maxLength;
    List<Integer> data = new LinkedList<Integer>();
    ReentrantLock dataLock = new ReentrantLock();


    Condition firstProducer = dataLock.newCondition();
    Condition isEmpty = dataLock.newCondition();

    Condition firstConsumer = dataLock.newCondition();
    Condition isFull = dataLock.newCondition();

    Buffer(int size){
        this.maxLength = size*2;
        this.actualLength = 0;
    }


    void toBuffer(int id, int size){
        dataLock.lock();


        while(dataLock.hasWaiters(firstProducer))
            try{
                isEmpty.await();
            }catch(InterruptedException e){};


        System.out.println("( " + id + " ) Waiting to add" + size);

        while(maxLength - actualLength < size){
            try{
                firstProducer.await();
            }catch(InterruptedException e){}

        }

        //Sleep only for delay
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        while(size>0){
            this.data.add(size);
            this.actualLength++;
            --size;
        }


        System.out.println("( "+ id + " ) Just added" + size);

        firstConsumer.signal();
        isEmpty.signal();
        dataLock.unlock();
    }

    int fromBuffer(int id,int size){
        dataLock.lock();
        System.out.println("( "+ id + " ) Waiting to get " + size );

        while(dataLock.hasWaiters(firstConsumer))
            try{
                isFull.await();
            }catch(InterruptedException e){};

        while( actualLength < size){
            try{
                firstConsumer.await();
            }catch(InterruptedException e){}
        }

        //Sleep only for delay
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        while(size>0){
            this.data.add(size);
            this.actualLength--;
            --size;
        }

        int data = this.data.get(0);

        System.out.println("( "+ id + " ) Just got" + size);

        firstProducer.signal();
        isFull.signal();
        dataLock.unlock();
        return data;
    }
}
