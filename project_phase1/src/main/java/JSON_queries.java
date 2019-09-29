import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

//In this class we create the queries in a json form in order to use it in elastic search with curl

public class JSON_queries{

    public static void main(String[] args) throws IOException {

        //Open the file with the queries
        String file ="C:/Users/Demetra/Desktop/testingQueries.txt";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        int i=1;
        while(currentLine!=null) {
            //We take only the query and not its id e.g. Q01,Q02,etc.
            if(i<10){
                currentLine=currentLine.replaceFirst("Q0"+i,"");
            }else if(i==10){
                currentLine=currentLine.replaceFirst("Q"+i,"");
            }

            //Remove tab character which creates problem while parsing the query in elasticsearch
            currentLine= currentLine.replaceAll("\\t+"," ");

            JSONObject general_query = new JSONObject();
            JSONObject simple_query = new JSONObject();
            JSONObject s_query_info = new JSONObject();

            JSONArray fields=new JSONArray();
            fields.put("text");

            s_query_info.put("query",currentLine);
            s_query_info.put("fields",fields);
            simple_query.put("simple_query_string",s_query_info);

            general_query.put("query",simple_query);
            general_query.put("from",1);        //we don't want to get the first document because it is the query itself
            general_query.put("size",20);       //we want to get back k=20 documents

            try {
                FileWriter JSON_query = new FileWriter("C:/Users/Demetra/Desktop/json_queries/json_query"+i+".json");
                JSON_query.write(general_query.toString());
                JSON_query.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
            currentLine=reader.readLine();
        }
        reader.close();
    }
}
