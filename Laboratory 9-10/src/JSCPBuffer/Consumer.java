package Simple;

import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.One2OneChannelSymmetricInt;

class Consumer implements CSProcess {

    private AltingChannelInputInt ManagerChanel;
    private One2OneChannelSymmetricInt[] buffers;

    private static int createdInstances = 0;
    public String ID;
    public int operations = 0;


    public Consumer(One2OneChannelSymmetricInt[] buffers, One2OneChannelSymmetricInt ManagerChanel) {
        this.ManagerChanel = ManagerChanel.in();
        this.ID = "CONSUMER_" + (++createdInstances);
        this.buffers = buffers;


    }

    public void run() {
        while (true) {
            int bufferIndex = this.ManagerChanel.read();
            int item = buffers[bufferIndex].in().read();
            operations += 1;
        }
    }

}