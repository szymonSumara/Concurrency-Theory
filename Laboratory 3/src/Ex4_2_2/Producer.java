package Ex4_2_2;

public class Producer extends Thread{
    private final Buffer buffer;


    Producer(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println("Start Ex4_3_3.Producer");
        while(true){
            buffer.toBuffer();
        }

    }
}
