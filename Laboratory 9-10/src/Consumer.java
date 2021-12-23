import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

class Consumer implements CSProcess
{ private AltingChannelInputInt channel;

    public Consumer (final One2OneChannelInt in)

    { channel = in.in();
    } // constructor

    public void run ()
    {  int item = channel.read();
        System.out.println(item);
    } // run

} // class Consumer