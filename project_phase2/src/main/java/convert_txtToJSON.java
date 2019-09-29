//KOURLI DIMITRA-3150081

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

//In this class we create the queries in a json form in order to use it in elastic search with curl for all the sets
//In the end, we get three new sets in JSON form

public class convert_txtToJSON {

    public static void main(String[] args) {
        //Call for its set the ForAllInSet method
        ForAllInSet("30");
        ForAllInSet("60");
        ForAllInSet("90");
    }

    public static void ForAllInSet(String percentage){
        //Open the folder with the queries
        File folder = new File("C:/Users/Demetra/Desktop/Percentage_"+percentage);
        File[] listOfFiles = folder.listFiles();

        //Take each file and convert it to JSON query
        for(int i=0; i<listOfFiles.length; i++) {

            try {
                FileReader fr = new FileReader("C:/Users/Demetra/Desktop/Percentage_"+percentage+"/"+listOfFiles[i].getName());

                ArrayList<String> query = new ArrayList<String>();
                LineNumberReader lnr = new LineNumberReader(fr);
                String phrase = lnr.readLine();
                while (phrase != null) {
                    query.add(phrase);
                    phrase = lnr.readLine();
                }
                lnr.close();

                JSONObject general_query = new JSONObject();
                JSONObject simple_query = new JSONObject();
                JSONObject s_query_info = new JSONObject();

                JSONArray fields = new JSONArray();
                fields.put("text");
                String currentLine = String.join(",", query);
                //Remove tab character which creates problem while parsing the query in elasticsearch
                currentLine = currentLine.replaceAll("\\t+", " ");

                s_query_info.put("query", currentLine);
                s_query_info.put("fields", fields);
                simple_query.put("simple_query_string", s_query_info);

                general_query.put("query", simple_query);
                general_query.put("from", 0);
                general_query.put("size", 20);       //we want to get back k=20 documents

                try {
                    FileWriter JSON_query = new FileWriter("C:/Users/Demetra/Desktop/JSON_"+percentage+"/"+"JSON_"+listOfFiles[i].getName().substring(0,7)+".json");
                    JSON_query.write(general_query.toString());
                    JSON_query.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
