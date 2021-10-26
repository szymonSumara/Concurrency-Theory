package onLabolatory.ex1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    boolean isOccupied = false;
    List<Integer> data = new LinkedList<Integer>();
    Lock dataLock = new ReentrantLock();
    Condition isEmpty = dataLock.newCondition();
    Condition isFull = dataLock.newCondition();

    void toBuffer(int id, int data){
        dataLock.lock();

        System.out.println("( "+ id + " ) Waiting to add");

        while(isOccupied){
            try{
                isEmpty.await();
            }catch(InterruptedException e){}

        }

        //Sleep only for delay
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}


        this.data.add(data);
        isOccupied = true;

        System.out.println("( "+ id + " ) Just added");

        isFull.signal();
        dataLock.unlock();
    }

    int fromBuffer(int id){
        dataLock.lock();
        System.out.println("( "+ id + " ) Waiting to get");

        while(!isOccupied){
            try{
                isFull.await();
            }catch(InterruptedException e){}
        }

        //Sleep only for delay
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        int data = this.data.get(0);
        isOccupied = false;

        System.out.println("( "+ id + " ) Just got");

        isEmpty.signal();
        dataLock.unlock();
        return data;
    }
}
