package Lock3;

import java.util.Random;

public class Producer extends Thread{
    private final IBuffer buffer;
    private static int counter=20000;
    private final int producerId;
    private final boolean randomPortion;
    private int portion;
    private Random random;

    Producer(IBuffer buffer, int seed){
        this.buffer = buffer;
        this.producerId = ++counter;
        this.randomPortion = true;
        this.random = new Random(seed);
    }

    Producer(IBuffer buffer, int seed , int portionSize){
        this.buffer = buffer;
        this.producerId = ++counter;
        this.randomPortion = false;
        this.portion = portionSize;
        this.random = new Random(seed);

    }

    @Override
    public void run() {

        while(true){

            if(this.randomPortion)
                this.portion = this.random.nextInt(100) + 1;

            //System.out.println("( P:" + this.producerId + " ) Wait for add " + this.portion);
            try {
                buffer.put(this.producerId,this.portion);
            }catch (InterruptedException e){;
                break;
            }

            //System.out.println("( P:" + this.producerId + " ) Just add " + this.portion);


        }

    }
}
