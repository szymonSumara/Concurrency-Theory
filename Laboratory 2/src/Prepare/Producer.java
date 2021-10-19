package Prepare;

import Prepare.Buffor;

import java.util.concurrent.TimeUnit;

public class Producer extends Thread{
    private final Buffor buffor;
    private final int M;

    Producer(Buffor buffor, int M){
        this.buffor = buffor;
        this.M = M;
    }

    @Override
    public void run() {
        System.out.println("Start Prepare.Producer");
        for(int i = 0 ; i < 100 ; i ++ ){
            int toAdd = (int)Math.floor(Math.random()*(M));
            buffor.toBuffor(toAdd,new int[1]);

            int randomTime = (int)Math.floor(Math.random()*(10)) + 1;
            try{
                TimeUnit.SECONDS.sleep(randomTime);
            }
            catch(Exception e){

            }
        }
        System.out.println("Exit Prepare.Producer");

    }
}
