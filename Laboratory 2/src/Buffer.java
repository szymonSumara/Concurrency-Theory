public class Buffer {

    boolean isOccupied = false;


    synchronized void toBuffer(){
        System.out.println("Waiting to add");
        while(isOccupied){
            try {
                wait();
            } catch (InterruptedException e)  {

            }
        }
        isOccupied = true;
        System.out.println("Just added");
        notify();
    }

    synchronized void fromBuffer(){
        System.out.println("Waiting to get");
        while(!isOccupied){
            try {
                wait();
            } catch (InterruptedException e)  {

            }
        }
        isOccupied = false;
        System.out.println("Just got");
        notify();
    }
}
