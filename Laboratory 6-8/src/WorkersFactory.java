import activeObject.IAsyncBuffer;
import syncBuffer.IBuffer;

import java.nio.Buffer;

public class WorkersFactory {

    private int offsideOperationNumber;
    private int portionSize;
    private IAsyncBuffer asyncBuffer;
    private IBuffer syncBuffer;
    private BufferType bufferType;


    WorkersFactory(){
    }

    public WorkersFactory setOffsideOperationNumber(int operationNumber){
        this.offsideOperationNumber = operationNumber;
        return this;
    }

    public WorkersFactory setPortionSize( int portionSize){
        this.portionSize = portionSize;
        return this;
    }

    public WorkersFactory setAsyncBuffer(IAsyncBuffer asyncBuffer){
        this.asyncBuffer = asyncBuffer;
        this.bufferType = BufferType.ASYNC;
        return this;
    }

    public WorkersFactory setSyncBuffer(IBuffer syncBuffer){
        this.syncBuffer = syncBuffer;
        this.bufferType = BufferType.SYNC;
        return this;
    }

    public Consumer createConsumer() throws Exception {

        if( this.bufferType == null)
            throw new Exception("No Buffer Specified");

        if( this.bufferType == BufferType.ASYNC)
            return new Consumer(this.asyncBuffer, this.portionSize, this.offsideOperationNumber);
        else
            return new Consumer(this.syncBuffer, this.portionSize, this.offsideOperationNumber);

    }

    public Producer createProducer() throws Exception {

        if( this.bufferType == null)
            throw new Exception("No Buffer Specified");

        if( this.bufferType == BufferType.ASYNC)
            return new Producer(this.asyncBuffer, this.portionSize, this.offsideOperationNumber);
        else
            return new Producer(this.syncBuffer, this.portionSize, this.offsideOperationNumber);

    }
}
