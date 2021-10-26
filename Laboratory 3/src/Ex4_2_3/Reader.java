package Ex4_2_3;

public class Reader extends Thread{
    private final ReadingRoom buffer;

    Reader(ReadingRoom buffer){
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
