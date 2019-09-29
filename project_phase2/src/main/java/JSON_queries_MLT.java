//KOURLI DIMITRA-3150081

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//In this class we create the queries in a json form in order to use it in elastic search with curl for all the sets .
//We create ,specifically, MoreLikeThis Queries .


public class JSON_queries_MLT {

    public static void main(String[] args) throws IOException {

        //Open the file with the queries
        String file ="C:/Users/Demetra/Desktop/testingQueries.txt";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        int i=1;
        while(currentLine!=null) {
            //We take only the query and not its id e.g. Q01,Q02,etc.
            if (i < 10) {
                currentLine = currentLine.replaceFirst("Q0" + i, "");
            } else if (i == 10) {
                currentLine = currentLine.replaceFirst("Q" + i, "");
            }

            //Remove tab character which creates problem while parsing the query in elasticsearch
            currentLine = currentLine.replaceAll("\\t+", " ");

            //Create the MLT_Query
            JSONObject general_query = new JSONObject();
            JSONObject s_query = new JSONObject();
            JSONObject MLT_query = new JSONObject();
            JSONArray fields = new JSONArray();

            fields.put("text");

            MLT_query.put("fields",fields);
            MLT_query.put("like", currentLine);
            //If we want the default values,we just put the next five following parameters as comments
            //Or else we put certain values in order to create -more or less- powerful queries
            MLT_query.put("min_term_freq", 1);
            MLT_query.put("max_query_terms", 25);
            MLT_query.put("min_doc_freq", 5);
            MLT_query.put("max_doc_freq", 10);
            MLT_query.put("minimum_should_match", "50%");

            s_query.put("more_like_this", MLT_query);

            general_query.put("query", s_query);
            general_query.put("from", 0);
            general_query.put("size", 20);

            try {
                //We set the name of the path depending on the folder we want to save the queries e.g. folders: json_queries_default,json_queries_less,json_queries_medium,json_queries_powerful
                FileWriter JSON_query = new FileWriter("C:/Users/Demetra/Desktop/json_queries_powerful/json_query"+i+".json");
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
