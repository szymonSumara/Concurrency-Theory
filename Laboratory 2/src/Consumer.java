public class Consumer extends Thread{
    private final Buffer buffer;


    Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println("Start Customer");
        while(true){
            buffer.fromBuffer();
        }
    }
}
