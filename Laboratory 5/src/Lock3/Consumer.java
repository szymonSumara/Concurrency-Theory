package Lock3;

import java.util.Random;

public class Consumer extends Thread{

    private final IBuffer buffer;
    private static int counter=10000;
    private final int consumerId;
    private final boolean randomPortion;
    private int portion;
    private Random random;

    Consumer(IBuffer buffer, int seed){
        this.buffer = buffer;
        this.consumerId = ++counter;
        this.randomPortion = true;
        this.random = new Random(seed);
    }

    Consumer(IBuffer buffer, int seed, int portionSize){
        this.buffer = buffer;
        this.consumerId = ++counter;
        this.randomPortion = false;
        this.portion = portionSize;
        this.random = new Random(seed);
    }


    @Override
    public void run() {

        while(true){

            if(this.randomPortion)
                this.portion = this.random.nextInt(100) + 1;

            //System.out.println("( C:" + this.consumerId + " ) Wait for get " + this.portion);
           try{
               buffer.get(this.consumerId,this.portion);
           }catch(InterruptedException e){
               break;
           }

            //System.out.println("( C:" + this.consumerId + " ) Just got " + this.portion);


        }

    }
}
