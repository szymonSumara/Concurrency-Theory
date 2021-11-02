package Homework.NoDeadLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadStatisticCollector {

    Map<Integer,ThreadStat> threads = new HashMap<>();

    public void noteWait(int id){
        if(!threads.containsKey(id)){
            threads.put(id,new ThreadStat());
        }
        threads.get(id).noteWait();
    }

    public void noteAction(int id){
        if(!threads.containsKey(id)){
            threads.put(id,new ThreadStat());
        }

        threads.get(id).noteAction();
    }

    @Override
    public String toString() {
        final AtomicReference<String> output = new AtomicReference<>();
        output.set("");
        this.threads.forEach( (key,value) -> {
            output.set( output.get() + key + ":" + value + "\n");
        });

        return output.get();
    }

    class ThreadStat{

        private int waitsCounter;
        private int actionCounter;

        @Override
        public String toString() {
            return " " + this.actionCounter + " | " + this.waitsCounter + " | "+ ((double)this.waitsCounter/(double)this.actionCounter) + "Waits per action";
        }

        public void noteWait(){
            this.waitsCounter++;
        }

        public void noteAction(){
            this.actionCounter++;
        }


    }


}
