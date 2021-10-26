package Ex4_2_3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadingRoom {

    boolean isOccupied = false;
    Lock dataLock = new ReentrantLock();
    Condition isEmpty = dataLock.newCondition();
    Condition isFull = dataLock.newCondition();

    void toBuffer(){
        dataLock.lock();
        System.out.println("Waiting to add");
        while(isOccupied){
            try{
                isEmpty.await();
            }catch(InterruptedException e){}

        }
        isOccupied = true;
        System.out.println("Just added");
        isFull.signal();
        dataLock.unlock();
    }

    void fromBuffer(){
        dataLock.lock();
        System.out.println("Waiting to get");
        while(!isOccupied){
            try{
                isFull.await();
            }catch(InterruptedException e){}
        }
        isOccupied = false;
        System.out.println("Just got");
        isEmpty.signal();
        dataLock.unlock();
    }
}
