package Ex4_2_3;

public class Writer extends Thread{
    private final ReadingRoom buffer;


    Writer(ReadingRoom buffer){
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
