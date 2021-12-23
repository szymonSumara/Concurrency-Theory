package Simple;

import org.jcsp.lang.*;

public class BufferManager implements CSProcess {
    AltingChannelOutputInt[] producersChannels;
    AltingChannelOutputInt[] consumersChannels;
    int BufferNumber;
    int producersIterator = 0;
    int consumersIterator = 0;
    int fieldsOccupied = 0;

    public BufferManager(One2OneChannelSymmetricInt[] producersChannels,One2OneChannelSymmetricInt[] consumersChannels, int bufferNumber){

        this.BufferNumber = bufferNumber;

        this.producersChannels = new AltingChannelOutputInt[producersChannels.length];
        for(int i = 0; i < producersChannels.length;i++)
            this.producersChannels[i] = producersChannels[i].out();

        this.consumersChannels = new AltingChannelOutputInt[consumersChannels.length];
        for(int i = 0; i < consumersChannels.length;i++)
            this.consumersChannels[i] = consumersChannels[i].out();
    }

    public void run(){

        Guard[] guards = new Guard[producersChannels.length + consumersChannels.length];
        System.arraycopy (producersChannels, 0, guards, 0, producersChannels.length);
        System.arraycopy (consumersChannels, 0, guards, producersChannels.length, consumersChannels.length);

        final Alternative alt = new Alternative (guards);

        while(true){
            int index = alt.select();
            if(index < producersChannels.length) {
                //System.out.println(producersIterator);
                producersChannels[index].write(producersIterator);
                producersIterator+=1;
                producersIterator%=BufferNumber;
            }else {
                consumersChannels[index - producersChannels.length].write(consumersIterator);
                consumersIterator+=1;
                consumersIterator%=BufferNumber;
            }
        }
    }



}
