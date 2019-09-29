import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

//In this class,we combine all the results we got from elasticsearch into the form that trec_eval requires and we create the file your_results_file.txt

public class TREC_EVAL_MYRESULTS {

    public static void main(String argv[]) throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter writer = new PrintWriter("C:/Users/Demetra/Desktop/your_results_file.txt", "UTF-8");
        writer.println("q_id"+"\t"+"iter"+"\t"+"docno"+"\t"+"rank"+"\t"+"sim"+"\t"+"\t"+"\t"+"run_id");

        int number_of_results = 10;

        for (int j = 1; j <= number_of_results; j++) {
            //We get the results from all the queries
            String filename = "C:/Users/Demetra/Desktop/results_queries/results_query"+j+".json";
            String result = "";

            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject hits = new JSONObject(result);
            JSONObject hits_inner = hits.getJSONObject("hits");
            JSONArray docs = hits_inner.getJSONArray("hits");

            //We take the information we need from results_queries
            for (int i = 0; i < docs.length(); ++i) {
                JSONObject doc = docs.getJSONObject(i);
                float score = doc.getFloat("_score");
                JSONObject _source = doc.getJSONObject("_source");
                int rcn = _source.getInt("rcn");

                System.out.println(score + "\t" + rcn);
                //We write the your_results_file in the form we need
                if(j<10) {
                    writer.println("Q0"+j + "\t" + "\t" + "Q0" + "\t" + "\t" + rcn + "\t" + "0" + "\t" + "\t" + score + "\t" + "ElasticSearch");
                }else{
                    writer.println("Q"+j + "\t" + "\t" + "Q0" + "\t" + "\t" + rcn + "\t" + "0" + "\t" + "\t" + score + "\t" + "ElasticSearch");
                }
            }


        }writer.close();
    }

}