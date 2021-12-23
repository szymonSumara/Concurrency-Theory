package Simple;

import java.util.HashMap;

public class StatisticCollector {

    public static StatisticCollector instance = new StatisticCollector();
    private static HashMap<String,Integer> stat = new HashMap<>();
    private StatisticCollector(){}

    public synchronized void nodeAction(String ID){
        if(!stat.containsKey(ID)){
            stat.put(ID,0);
        }
        Integer value = stat.get(ID);
        value+=1;
        stat.put(ID,value);
    }

    @Override
    public String toString() {
        return stat.toString();
    }

}
