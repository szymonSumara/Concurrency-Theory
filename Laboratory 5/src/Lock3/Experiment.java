package Lock3;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class Experiment {

    private static final int P = 10;
    private static final int K = 10;
    private static final int M = 100;

    public static void main(String[] args) {

        CSVGenerator csvGenerator = new CSVGenerator();
        csvGenerator.setHeader(Arrays.asList("Method","Number of operations"));
       for(int i = 0 ; i < 10 ; i ++ ){

           ThreadStatisticCollector statistic = new ThreadStatisticCollector();
           IBuffer buffer = new Buffer3Lock(100,statistic);
           ExperimentStep experimentStep = new ExperimentStep(buffer, K, P);
           experimentStep.start();
           Integer actionCount = statistic.getTotalActions();
           csvGenerator.addRow(Arrays.asList("Buffer3Lock",actionCount.toString()));


           statistic = new ThreadStatisticCollector();
           buffer = new Buffer4Conditions(100,statistic);
           experimentStep = new ExperimentStep(buffer, K, P);
           experimentStep.start();
           actionCount = statistic.getTotalActions();
           csvGenerator.addRow(Arrays.asList("Buffer4Conditions",actionCount.toString()));

        }

        try {
            csvGenerator.saveToFile("test.csv");
        }catch(IOException e){}
    }

}
