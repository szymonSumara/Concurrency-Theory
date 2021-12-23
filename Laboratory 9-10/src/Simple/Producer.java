package Simple;

import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.One2OneChannelSymmetricInt;

class Producer implements CSProcess {
    private AltingChannelInputInt ManagerChanel;
    private One2OneChannelSymmetricInt[] buffers;

    private int iterator = 0;
    private String ID;
    private static int createdInstances = 0;
    private int actionsNumber = 0;

public Producer (  One2OneChannelSymmetricInt[] buffers , One2OneChannelSymmetricInt ManagerChanel)
        {

            this.ManagerChanel = ManagerChanel.in();
            this.ID = "PRODUCER" + (++createdInstances);
            this.buffers = buffers;

        }

public void run ()
        {

            int i = 0;
            while (true){

                int bufferIndex = this.ManagerChanel.read();
                actionsNumber+=1;
                int item = (int)(Math.random()*100)+1;
                this.buffers[bufferIndex].out().write(item);
                try{
                    Thread.sleep(0);
                }catch (InterruptedException e){
                    System.out.println(e);
                }
                //System.out.println(ID + " " + actionsNumber);
                StatisticCollector.instance.nodeAction(this.ID);
//                iterator += 1;
//                iterator%= buffers.length;
            }

        } // run

} // class Producer
