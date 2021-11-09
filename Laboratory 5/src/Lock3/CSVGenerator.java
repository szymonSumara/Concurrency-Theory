package Lock3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CSVGenerator {

    private List<String> headers;
    private final List<List<String>> body;

    CSVGenerator(){
        this.headers = new LinkedList<>();
        this.body = new LinkedList<>();
    }

    void setHeader(List<String> headers){
        this.headers = headers;
    }

    void addRow(List<String> row){
        this.body.add(row);
    }

    void saveToFile(String fileName)throws IOException  {

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        boolean isFirstLine = true;


        for (String header: this.headers) {
            if(!isFirstLine) writer.append(',');
            writer.append(header);
            isFirstLine = false;
        }
        isFirstLine = true;
        writer.append('\n');

        for( List<String> row : this.body){
            for( String cell : row){
                if(!isFirstLine) writer.append(',');
                writer.append(cell);
                isFirstLine = false;
            }
            isFirstLine = true;
            writer.append('\n');
        }

        writer.close();
    }
}
