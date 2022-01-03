//import org.jcsp.lang.*;
//
//class Producer implements CSProcess{
//    private ChannelOutputInt channel;
//
//
//public Producer (final One2OneChannelInt out)
//
//        { channel = out.out();
//        } // constructor
//
//public void run ()
//        { int item = (int)(Math.random()*100)+1;
//        channel.write(item);
//        } // run
//
//        } // class Producer
//
///** Consumer class: reads one int from input channel, displays it, then
// * terminates.
// */
//class Consumer implements CSProcess
//{ private AltingChannelInputInt channel;
//
//    public Consumer (final One2OneChannelInt in)
//
//    { channel = in.in();
//    } // constructor
//
//    public void run ()
//    {  int item = channel.read();
//        System.out.println(item);
//    } // run
//
//} // class Consumer
//
//
///** Main program class for Producer/Consumer example.
// * Sets up channel, creates one of each process then
//
// * executes them in parallel, using JCSP.
// */
//final class PCMain
//{
//    public static void main (String[] args)
//
//    { new PCMain();
//    } // main
//
//    public PCMain ()
//    { // Create channel object
//        final One2OneChannelInt channel =  Channel.one2oneInt();
//
//
//        // Create and run parallel construct with a list of processes
//
//        CSProcess[] procList = { new Producer(channel), new Consumer(channel) };
//        // Processes
//
//        Parallel par = new Parallel(procList); // PAR construct
//        par.run(); // Execute processes in parallel
//    } // PCMain constructor
//
//} // class PCMain