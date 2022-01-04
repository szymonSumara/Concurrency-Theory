package JSCPBuffer;

import org.jcsp.lang.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Buffer  implements CSProcess {

    List<Integer> data = new LinkedList<>();
    final int size;
    private static int createdInstances = 0;
    public final String ID;
    public int operations = 0;
    boolean shouldStop = false;

    AltingChannelInputInt[] producersChannels;
    AltingChannelOutputInt[] consumersChannels;

    public Buffer(One2OneChannelSymmetricInt [] producersChannels , One2OneChannelSymmetricInt [] consumersChannels, int size){
        this.size = size;
        ID = "Buffer_" + (createdInstances ++);
        this.producersChannels = new AltingChannelInputInt[producersChannels.length];
        for(int i = 0; i < producersChannels.length;i++)
            this.producersChannels[i] = producersChannels[i].in();

        this.consumersChannels = new AltingChannelOutputInt[consumersChannels.length];
        for(int i = 0; i < consumersChannels.length;i++)
            this.consumersChannels[i] = consumersChannels[i].out();
    }

    public void run ()
    {
        Guard[] guards = new Guard[producersChannels.length + consumersChannels.length ];
        System.arraycopy (producersChannels, 0, guards, 0, producersChannels.length);
        System.arraycopy (consumersChannels, 0, guards, producersChannels.length, consumersChannels.length);

        Guard[] guards1 = new Guard[producersChannels.length ];
        System.arraycopy (producersChannels, 0, guards1, 0, producersChannels.length);

        Guard[] guards2 = new Guard[consumersChannels.length ];
        System.arraycopy (consumersChannels, 0, guards2, 0,  consumersChannels.length);

        Alternative everyone = new Alternative(guards);
        Alternative producers = new Alternative(guards1);
        Alternative consumers = new Alternative(guards2);

        while(!shouldStop){
            if(data.size() == 0  ){
                int item = producersChannels[producers.fairSelect()].read();
                this.data.add(item);

            }else if (data.size() == this.size ) {
                int item = this.data.remove(0);
                consumersChannels[consumers.fairSelect()].write(item);
            }else{
                int index = everyone.fairSelect();
                if(index < producersChannels.length){
                    int item = producersChannels[index].read();
                    this.data.add(item);

                }else{
                    int item = this.data.remove(0);
                    consumersChannels[index - producersChannels.length].write(item);
                }
            }

            operations += 1;
        }
    }
}
