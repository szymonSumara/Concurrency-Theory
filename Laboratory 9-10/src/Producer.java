import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

class Producer implements CSProcess {
    private One2OneChannelInt ManagerChanel;
    private One2OneChannelInt[] Buffers;


public Producer ( One2OneChannelInt managerChanel, One2OneChannelInt[] buffers )
        {
            this.ManagerChanel = managerChanel;
            this.Buffers = buffers;
        }

public void run ()
        {
            int item = (int)(Math.random()*100)+1;
            ManagerChanel.out().write(-1);
            int bufferIndex = ManagerChanel.in().read();
            this.Buffers[bufferIndex].out().write(item);

        } // run

} // class Producer
