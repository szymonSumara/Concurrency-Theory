
import activeObject.IAsyncBuffer;
import basic.CSVGenerator;
import syncBuffer.IBuffer;

import java.io.IOException;
import java.util.Random;

public abstract class Worker extends Thread{
    protected IAsyncBuffer asyncBuffer;
    protected IBuffer syncBuffer;
    protected BufferType bufferType;

    protected static int consumersInstancesCount;
    protected final int id;

    protected final int offsideJobIterations;
    protected final boolean randomPortion;
    protected int portion;
    protected final Random random;
    protected boolean shouldStop = false;

    protected static CSVGenerator csvGenerator = new CSVGenerator();

    Worker(int portionSize, int offsideJobIterations){
        System.out.println(portionSize);
        this.id = ++consumersInstancesCount + 100000 ;
        this.random = new Random(this.id);

        if(portionSize > 0 ) this.randomPortion = false;
        else this.randomPortion = true;
        this.portion = portionSize;
        this.offsideJobIterations = offsideJobIterations;


    }
    
    
    @Override
    public void run() {
        while(!this.shouldStop){
            if(this.randomPortion) this.portion = this.random.nextInt(100) + 1;

            try {
                if(this.bufferType == BufferType.SYNC) this.syncBufferRoutine();
                else this.asyncBufferRoutine();
                
            }catch (Exception e){;
                break;
            }
        }
    }

    protected abstract void syncBufferRoutine() throws Exception;
    protected abstract void asyncBufferRoutine() throws Exception;
    
    public static void SaveInfo() throws IOException {
        csvGenerator.saveToFile("ConsumersOffsideJob.csv");
    }
}
