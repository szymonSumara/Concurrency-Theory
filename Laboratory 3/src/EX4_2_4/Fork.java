package EX4_2_4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    boolean isTaken = false;
    Lock forkLock = new ReentrantLock();
    Condition isFree = forkLock.newCondition();
    private static int counter = 0;
    private int forkNumber;

    Fork(){
        this.forkNumber = ++counter;
    }

    boolean get(){
        forkLock.lock();
        //System.out.println("waiting to get  " + this.forkNumber +" fork");
        while(isTaken){
            try{
                if(!isFree.await(1, TimeUnit.SECONDS)){
          //          System.out.println("fork  " + this.forkNumber +" is already taken!!!");
                    forkLock.unlock();
                    return false;
                }

            }catch(InterruptedException e){}

        }
        isTaken = true;
        //System.out.println("Taken  " + this.forkNumber +" fork");
        forkLock.unlock();
        return true;
    }

    void put(){
        forkLock.lock();
        isTaken = false;
        //System.out.println("Put " + this.forkNumber +"fork");
        isFree.signal();
        forkLock.unlock();
    }
}
