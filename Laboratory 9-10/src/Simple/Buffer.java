package Simple;

import org.jcsp.lang.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Buffer  implements CSProcess {
    AltingChannelInputInt[] producersChannels;
    AltingChannelOutputInt[] consumersChannels;
    List<Integer> data = new LinkedList<>();
    final static int MAX_CAPTIVITY = 100;
    private String ID;
    private static int createdInstances = 0;
    boolean shouldStop = false;

    public Buffer(One2OneChannelSymmetricInt [] producersChannels , One2OneChannelSymmetricInt [] consumersChannels){
        ID = "Buffer " + (createdInstances ++);
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
        boolean[] mask = new boolean[producersChannels.length + consumersChannels.length ];
        final Alternative alt = new Alternative(guards);

        while(!shouldStop){

            if(data.size() == 0){
                for(int i=0; i < producersChannels.length;i++ ){
                    mask[i] = true;
                }

                for(int i=producersChannels.length; i < producersChannels.length + consumersChannels.length;i++ ){
                    mask[i] = false;
                }
            }else if (data.size() == Buffer.MAX_CAPTIVITY){
                for(int i=0; i < producersChannels.length;i++ ){
                    mask[i] = false;
                }

                for(int i=producersChannels.length; i < producersChannels.length + consumersChannels.length;i++ ){
                    mask[i] = true;
                }

            }else{
                for(int i=0; i < producersChannels.length + consumersChannels.length;i++ ){
                    mask[i] = true;
                }
            }

            int index = alt.fairSelect(mask);
            System.out.println(ID + " Index:" + index);
                        try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                System.out.println(e);
            }

            if(data.size() == 0  ){
                int item = producersChannels[index].read();
                this.data.add(item);
                //System.out.println("PUT:" + item + " " + this.data.size());
            }else if (data.size() == Buffer.MAX_CAPTIVITY ) {
                int item = this.data.remove(0);
                consumersChannels[index - producersChannels.length].write(item);
                //System.out.println("GET:" + item + " " + this.data.size());
            }else{
                if(index < producersChannels.length){
                    int item = producersChannels[index].read();
                    this.data.add(item);
                    //System.out.println("PUT:" + item + " " + this.data.size());
                }else{
                    int item = this.data.remove(0);
                    consumersChannels[index - producersChannels.length].write(item);
                    //System.out.println("GET:" + data + " " + this.data.size());
                }
            }

            StatisticCollector.instance.nodeAction(ID);
        }
    } // run
}
