package Simple;

import org.jcsp.lang.*;

public class BufferManager implements CSProcess {
    AltingChannelOutputInt[] producersChannels;
    AltingChannelOutputInt[] consumersChannels;
    int BufferNumber;
    int producersIterator = 0;
    int consumersIterator = 0;
    int[] buffersSizes;
    int[] bufferOccupancy;
    int fieldsOccupied = 0;
    int totalFields = 0;

    public BufferManager(One2OneChannelSymmetricInt[] producersChannels, One2OneChannelSymmetricInt[] consumersChannels, int[] buffersSizes) {

        this.BufferNumber = buffersSizes.length;
        this.buffersSizes = buffersSizes;
        this.bufferOccupancy = new int[buffersSizes.length];

        for (int i = 0; i < this.bufferOccupancy.length; i++) {
            bufferOccupancy[0] = 0;
            totalFields += this.buffersSizes[i];
        }

        this.producersChannels = new AltingChannelOutputInt[producersChannels.length];
        for (int i = 0; i < producersChannels.length; i++)
            this.producersChannels[i] = producersChannels[i].out();

        this.consumersChannels = new AltingChannelOutputInt[consumersChannels.length];
        for (int i = 0; i < consumersChannels.length; i++)
            this.consumersChannels[i] = consumersChannels[i].out();
    }

    public void run() {

        Guard[] guards = new Guard[producersChannels.length + consumersChannels.length];
        System.arraycopy(producersChannels, 0, guards, 0, producersChannels.length);
        System.arraycopy(consumersChannels, 0, guards, producersChannels.length, consumersChannels.length);

        Guard[] guards1 = new Guard[producersChannels.length];
        System.arraycopy(producersChannels, 0, guards1, 0, producersChannels.length);

        Guard[] guards2 = new Guard[consumersChannels.length];
        System.arraycopy(consumersChannels, 0, guards2, 0, consumersChannels.length);

        Alternative everyone = new Alternative(guards);
        Alternative producers = new Alternative(guards1);
        Alternative consumers = new Alternative(guards2);

        while (true) {
            int index;
            if (fieldsOccupied == totalFields)
                sendConsumerToBuffer(consumers.fairSelect());
             else if (fieldsOccupied == 0)
                sendProducerToBuffer(producers.fairSelect());
             else {

                index = everyone.fairSelect();
                if (index < producersChannels.length)
                  sendProducerToBuffer(index);
                else
                    sendConsumerToBuffer(index - producersChannels.length);

            }
        }
    }

    private void incrementProducersIterator() {
        producersIterator += 1;
        producersIterator %= BufferNumber;
    }

    private void incrementConsumersIterator() {
        consumersIterator += 1;
        consumersIterator %= BufferNumber;
    }

    private void sendProducerToBuffer(int producerId){
        while (bufferOccupancy[producersIterator] == buffersSizes[producersIterator])
            incrementProducersIterator();
        this.bufferOccupancy[producersIterator]+=1;
        producersChannels[producerId].write(producersIterator);
        fieldsOccupied+=1;
        incrementProducersIterator();
    }

    private void sendConsumerToBuffer(int consumerId){
        while (bufferOccupancy[consumersIterator] == 0)
            incrementConsumersIterator();
        consumersChannels[consumerId ].write(consumersIterator);
        this.bufferOccupancy[consumersIterator]-=1;
        fieldsOccupied-=1;
        incrementConsumersIterator();
    }


}
