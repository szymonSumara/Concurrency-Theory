package JSCPBuffer;

import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.One2OneChannelSymmetricInt;

class Producer implements CSProcess {

    private AltingChannelInputInt ManagerChanel;
    private One2OneChannelSymmetricInt[] buffers;

    private static int createdInstances = 0;
    public String ID;
    public int operations = 0;

    public Producer(One2OneChannelSymmetricInt[] buffers, One2OneChannelSymmetricInt ManagerChanel) {

        this.ManagerChanel = ManagerChanel.in();
        this.ID = "PRODUCER_" + (++createdInstances);
        this.buffers = buffers;

    }

    public void run() {
        while (true) {
            int bufferIndex = this.ManagerChanel.read();
            int item = (int) (Math.random() * 100) + 1;
            this.buffers[bufferIndex].out().write(item);
            operations += 1;
        }

    }
}
