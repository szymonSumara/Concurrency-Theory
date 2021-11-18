package activeObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Do implementacji classy Future użyłem locka, ponieważ pozwoziło mi to wyeliminować aktywne czekanie.


public class Future<T> {

    protected T value;
    protected boolean isAvailable;
    private Lock lock = new ReentrantLock();
    private Condition dataIsAvailable = lock.newCondition();

    protected void setData(T data){
        lock.lock();
        this.value = data;
        this.isAvailable = true;
        dataIsAvailable.signal();
        lock.unlock();
    }

    public boolean isAvailable(){
        return  this.isAvailable;
    }

    public T get() throws InterruptedException{
        lock.lock();
        try{
            if(!this.isAvailable)
                dataIsAvailable.await();
        }finally{
            lock.unlock();
        }
        return this.value;
    }

}
