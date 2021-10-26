public class Producer extends Thread{
    private final Buffer buffer;


    Producer(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println("Start Producer");
        while(true){
            buffer.toBuffer();
        }

    }
}
