package EX4_2_4;

import java.util.Random;

public class Philosopher extends Thread{

    private Fork leftFork;
    private Fork rightFork;
    private static int counter = 0;
    private int philosopherNumber;

    Philosopher(Fork leftFork,Fork rightFork){
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.philosopherNumber = ++counter;
    }

    @Override
    public void run() {
        System.out.println("Start " + philosopherNumber +"Philosopher");

        while(true){

            int randomTime = (int)Math.random()*5 + 1;
            System.out.println(" " + philosopherNumber +" Philosopher start thinking");
            try{
                Thread.sleep(randomTime);
            }catch(InterruptedException e){}

            while(true){



                if(!leftFork.get())
                    continue;
                System.out.println("Get " + philosopherNumber +"fork");

                if( !rightFork.get()){
                    leftFork.put();
                    System.out.println("Put " + philosopherNumber  +"fork");
                    continue;
                }
                System.out.println("Get " + philosopherNumber + 1 +"fork");

                System.out.println("Start " + philosopherNumber +" Philosopher is eating");
                leftFork.put();
                rightFork.put();
                break;
            }
        }

    }
}
