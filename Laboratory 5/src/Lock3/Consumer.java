package Lock3;

public class Consumer extends Thread{

    private final Buffer buffer;
    private static int counter=10000;
    private final int consumerId;
    private final boolean randomPortion;
    private int portion;

    Consumer(Buffer buffer){
        this.buffer = buffer;
        this.consumerId = ++counter;
        this.randomPortion = true;
    }

    Consumer(Buffer buffer, int portionSize){
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

            System.out.println("( C:" + this.consumerId + " ) Wait for get " + this.portion);
            buffer.fromBuffer(this.consumerId,this.portion);
            System.out.println("( C:" + this.consumerId + " ) Just got " + this.portion);

            int randomTime = (int)Math.random()*10 + 1;
            try{
                Thread.sleep(randomTime*10);
            }catch(InterruptedException e){

            }

        }
    }
}
