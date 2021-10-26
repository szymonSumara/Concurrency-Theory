package EX4_2_4;

import java.util.LinkedList;
import java.util.List;

public class App {


    private static final int PhilisophersNum =5;

    public static void main(String[] args) {

        Fork forks[] = new Fork[5];
        List<Philosopher> philosophers = new LinkedList();

        for(int i = 0 ; i <  PhilisophersNum ; i++)
            forks[i] = new Fork();

        for(int i = 0 ; i < PhilisophersNum;i++)
            philosophers.add(new Philosopher(forks[i],forks[(i+1)%PhilisophersNum]));

        philosophers.forEach(Thread::start);
        philosophers.forEach(worker -> {
            try{
                worker.join();
            }catch(Exception e){

            }
        });

    }
}


