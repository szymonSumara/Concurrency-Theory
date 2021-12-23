package Simple;

import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.One2OneChannelSymmetricInt;

class Consumer implements CSProcess
{

    private AltingChannelInputInt ManagerChanel;
    private One2OneChannelSymmetricInt[] buffers;

    private String ID;
    private static int createdInstances = 0;
    public Consumer ( One2OneChannelSymmetricInt[] buffers , One2OneChannelSymmetricInt ManagerChanel)
    {
        this.ManagerChanel = ManagerChanel.in();
        this.ID = "CONSUMER" + (++createdInstances);
        this.buffers = buffers;


    } // constructor

    public void run ()
    {
        while (true){
            int bufferIndex = this.ManagerChanel.read();
            int item = buffers[bufferIndex].in().read();
            //System.out.println("Get: " + item);
            StatisticCollector.instance.nodeAction(this.ID);
//            try{
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                System.out.println(e);
//            }
        }

    } // run

} // class Consumer