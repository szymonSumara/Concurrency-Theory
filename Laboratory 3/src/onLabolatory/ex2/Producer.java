package onLabolatory.ex2;

public class Producer extends Thread{
    private final Buffer buffer;
    private static int counter=0;
    private final int producerId;
    private final boolean randomPortion;
    private int portion;

    Producer(Buffer buffer){
        this.buffer = buffer;
        this.producerId = ++counter;
        this.randomPortion = true;
    }

    Producer(Buffer buffer,int portionSize){
        this.buffer = buffer;
        this.producerId = ++counter;
        this.randomPortion = false;
        this.portion = portionSize;
    }

    @Override
    public void run() {
        System.out.println("Start Producer");
        while(true){
            this.portion = (int)Math.floor(Math.random()*(100));
            buffer.toBuffer(this.producerId, this.portion);
        }

    }
}
