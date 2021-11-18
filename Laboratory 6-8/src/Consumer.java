import activeObject.Future;
import activeObject.IAsyncBuffer;
import basic.CSVGenerator;
import syncBuffer.IBuffer;

import java.io.IOException;
import java.util.Random;

public class Consumer extends Worker{


    Consumer(IBuffer syncBuffer, int portionSize, int offsideJobIterations){
        super(portionSize, offsideJobIterations);
        System.out.println(portionSize);

        this.syncBuffer = syncBuffer;
        this.bufferType = BufferType.SYNC;

    }

    Consumer(IAsyncBuffer asyncBuffer, int portionSize, int offsideJobIterations){
        super(portionSize, offsideJobIterations);
        System.out.println(portionSize);
        this.asyncBuffer = asyncBuffer;
        this.bufferType = BufferType.ASYNC;
    }


    @Override
    protected void syncBufferRoutine() throws Exception {
        double tmp = 0d;
        this.syncBuffer.get(this.id,this.portion);
        for(int i =0 ; i < this.offsideJobIterations;i++){
            tmp += Math.sin(tmp + 123);
        }
        System.out.println(tmp);
    }

    @Override
    protected void asyncBufferRoutine() throws Exception {
        boolean getValue = false;
        double tmp = 0d;
        Future future = asyncBuffer.get(this.id, this.portion );
        for(int i =0 ; i < this.offsideJobIterations;i++){
            if( !getValue && i % 1000 == 0 && future.isAvailable()){
                //csvGenerator.addRow(Arrays.asList(String.valueOf(i),""));
                getValue = true;
            }
            tmp += Math.sin(tmp + 123);
        }
        if(!getValue) future.get();
        System.out.println(tmp);
    }

    public static void SaveInfo() throws IOException {
       csvGenerator.saveToFile("ConsumersOffsideJob.csv");
    }

}
