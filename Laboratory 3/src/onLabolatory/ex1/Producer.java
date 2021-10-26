package onLabolatory.ex1;

public class Producer extends Thread{
    private final Buffer buffer;
    private static int counter=0;
    private final int producerId;

    Producer(Buffer buffer){
        this.buffer = buffer;
        this.producerId = ++counter;
    }

    @Override
    public void run() {
        System.out.println("Start Producer");
        while(true){

            buffer.toBuffer(this.producerId, this.producerId);
        }

    }
}
