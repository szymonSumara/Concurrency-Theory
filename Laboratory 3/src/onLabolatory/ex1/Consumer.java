package onLabolatory.ex1;

public class Consumer extends Thread{
    private final Buffer buffer;
    private static int counter=0;
    private final int consumerId;
    Consumer(Buffer buffer){
        this.buffer = buffer;
        this.consumerId = ++counter;
    }

    @Override
    public void run() {
        System.out.println("Start Customer");
        while(true){
            buffer.fromBuffer(this.consumerId);
        }
    }
}
