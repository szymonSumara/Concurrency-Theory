package Ex4_3_3;

import java.util.concurrent.TimeUnit;

public class Consumer extends Thread{
    private final Buffer buffer;
    private final int M;

    Consumer(Buffer buffer, int M){
        this.buffer = buffer;
        this.M = M;
    }

    @Override
    public void run() {
        System.out.println("Start Customer");
        for(int i = 0 ; i < 100 ; i ++ ){
            int toAdd = (int)Math.floor(Math.random()*(M)) + 1;
            buffer.fromBuffor(toAdd,new int[1]);

            int randomTime = (int)Math.floor(Math.random()*(10)) ;
            try{
                TimeUnit.SECONDS.sleep(randomTime);
            }
            catch(Exception e){

            }
        }
        System.out.println("Exit Customer");
    }
}
