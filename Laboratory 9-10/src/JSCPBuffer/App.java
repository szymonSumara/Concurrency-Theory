package Simple;

import org.jcsp.lang.*;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.ArrayList;

public class App {

    final static int PRODUCERS_NUMBER = 10;
    final static int CONSUMERS_NUMBER = 2;
    final static int[] BUFFER_SIZES = {10, 10, 10};
    final static int BUFFERS_NUMBER = BUFFER_SIZES.length;

    static Parallel par;
    static  ArrayList<CSProcess> processes;


    public static void main(String[] args) throws Exception{


        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {

                par.removeAllProcesses();
                for( CSProcess process : processes ){
                    if( process instanceof Producer){
                        System.out.println(((Producer) process).ID + " : " + ((Producer) process).operations);
                    }
                    if(process instanceof  Consumer){
                        System.out.println(((Consumer) process).ID + " : " + ((Consumer) process).operations);
                    }
                    if(process instanceof  Buffer){
                        System.out.println(((Buffer) process).ID + " : " + ((Buffer) process).operations);
                    }
                }
                System.exit(1);
            }
        });

        One2OneChannelSymmetricInt[][] producersChannels = new One2OneChannelSymmetricInt[PRODUCERS_NUMBER][BUFFERS_NUMBER];
        One2OneChannelSymmetricInt[][] consumersChannels = new One2OneChannelSymmetricInt[CONSUMERS_NUMBER][BUFFERS_NUMBER];

        One2OneChannelSymmetricInt[][] buffersProducersChannels = new One2OneChannelSymmetricInt[BUFFERS_NUMBER][PRODUCERS_NUMBER];
        One2OneChannelSymmetricInt[][] buffersConsumersChannels = new One2OneChannelSymmetricInt[BUFFERS_NUMBER][CONSUMERS_NUMBER];

        One2OneChannelSymmetricInt[] managersProducersChannels = new One2OneChannelSymmetricInt[PRODUCERS_NUMBER];
        One2OneChannelSymmetricInt[] managersConsumersChannels = new One2OneChannelSymmetricInt[CONSUMERS_NUMBER];

        for(int i = 0 ; i < PRODUCERS_NUMBER ; i ++)
            managersProducersChannels[i] = Channel.one2oneSymmetricInt();

        for(int i = 0 ; i < CONSUMERS_NUMBER ; i ++)
            managersConsumersChannels[i] = Channel.one2oneSymmetricInt();


        for(int b=0; b < BUFFERS_NUMBER ; b ++){
            for(int p = 0 ; p < PRODUCERS_NUMBER ; p ++){
                One2OneChannelSymmetricInt channel = Channel.one2oneSymmetricInt();
                producersChannels[p][b] = channel;
                buffersProducersChannels[b][p] = channel;
            }

            for(int c = 0 ;  c < CONSUMERS_NUMBER ; c  ++){
                One2OneChannelSymmetricInt channel = Channel.one2oneSymmetricInt();
                consumersChannels[c][b] = channel;
                buffersConsumersChannels[b][c] = channel;
            }

        }


        processes = new ArrayList();

        for(int i = 0 ; i < PRODUCERS_NUMBER ; i ++)
            processes.add(new Producer(producersChannels[i], managersProducersChannels[i]));

        for(int i = 0 ; i < CONSUMERS_NUMBER ; i ++)
            processes.add(new Consumer(consumersChannels[i], managersConsumersChannels[i]));

        for(int i = 0 ; i < BUFFERS_NUMBER ; i ++)
            processes.add(new Buffer(buffersProducersChannels[i], buffersConsumersChannels[i], BUFFER_SIZES[i]));

        processes.add(new BufferManager(managersProducersChannels,managersConsumersChannels, BUFFER_SIZES));

        CSProcess[] process = new CSProcess[processes.size()];
        process = processes.toArray(process);
        par = new Parallel(process);
        par.run();
    }
}
