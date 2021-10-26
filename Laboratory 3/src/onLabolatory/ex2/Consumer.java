package onLabolatory.ex2;

public class Consumer extends Thread{

    private final Buffer buffer;
    private static int counter=0;
    private final int consumerId;
    private final boolean randomPortion;
    private int portion;

    Consumer(Buffer buffer){
        this.buffer = buffer;
        this.consumerId = ++counter;
        this.randomPortion = true;
    }

    Consumer(Buffer buffer,int portionSize){
        this.buffer = buffer;
        this.consumerId = ++counter;
        this.randomPortion = false;
        this.portion = portionSize;
    }

    @Override
    public void run() {
        System.out.println("Start Customer");
        while(true){
            if(this.randomPortion)
                this.portion = (int)Math.floor(Math.random()*(100));
            buffer.fromBuffer(this.consumerId,this.portion);
        }
    }
}
