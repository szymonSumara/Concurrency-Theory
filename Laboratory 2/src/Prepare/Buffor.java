package Prepare;

public class Buffor {

    private int[] array;
    private int head;

    Buffor(int M){
        array = new int[2*M];
        head = -1;
    }

    synchronized void toBuffor(int howMuch, int[] portion){
        System.out.println("Waiting to add  " + howMuch );
        while( (this.array.length - this.head)  <=  howMuch){
            try {
                wait();
            } catch (InterruptedException e)  {

            }
        }

        this.head += howMuch;
        System.out.println("Added " + howMuch + ". Now Contain " + (this.head + 1));
        notifyAll();
    }

    synchronized void fromBuffor(int howMuch, int[] portion){
        System.out.println("Waiting for take  " + howMuch );
        while( (this.head + 1)  <  howMuch ){
            try {
                wait();
            } catch (InterruptedException e)  {

            }
        }

        this.head -= howMuch;
        System.out.println("Teaken " + howMuch + ". Now Contain " + (this.head + 1));
        notifyAll();
    }
}
