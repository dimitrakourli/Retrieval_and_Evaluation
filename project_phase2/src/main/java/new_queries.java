//KOURLI DIMITRA-3150081

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;

//In this class ,we create 3 sets with different percentage of phrases(30%,60%,90%)
//We do this by calling the method create_sets for the Q01,Q02,... files grouped by the percentage in main

public class new_queries {

    public static void main(String[] args) {
        //Create the first set with the 30% of the phrases
        String setName="Percentage_30";
        create_sets(0.3,"Q01","193378.txt",setName);
        create_sets(0.3,"Q02","213164.txt",setName);
        create_sets(0.3,"Q03","204146.txt",setName);
        create_sets(0.3,"Q04","214253.txt",setName);
        create_sets(0.3,"Q05","212490.txt",setName);
        create_sets(0.3,"Q06","210133.txt",setName);
        create_sets(0.3,"Q07","213097.txt",setName);
        create_sets(0.3,"Q08","193715.txt",setName);
        create_sets(0.3,"Q09","197346.txt",setName);
        create_sets(0.3,"Q10","199879.txt",setName);

        //Create the first set with the 60% of the phrases
        setName="Percentage_60";
        create_sets(0.6,"Q01","193378.txt",setName);
        create_sets(0.6,"Q02","213164.txt",setName);
        create_sets(0.6,"Q03","204146.txt",setName);
        create_sets(0.6,"Q04","214253.txt",setName);
        create_sets(0.6,"Q05","212490.txt",setName);
        create_sets(0.6,"Q06","210133.txt",setName);
        create_sets(0.6,"Q07","213097.txt",setName);
        create_sets(0.6,"Q08","193715.txt",setName);
        create_sets(0.6,"Q09","197346.txt",setName);
        create_sets(0.6,"Q10","199879.txt",setName);

        //Create the first set with the 90% of the phrases
        setName="Percentage_90";
        create_sets(0.9,"Q01","193378.txt",setName);
        create_sets(0.9,"Q02","213164.txt",setName);
        create_sets(0.9,"Q03","204146.txt",setName);
        create_sets(0.9,"Q04","214253.txt",setName);
        create_sets(0.9,"Q05","212490.txt",setName);
        create_sets(0.9,"Q06","210133.txt",setName);
        create_sets(0.9,"Q07","213097.txt",setName);
        create_sets(0.9,"Q08","193715.txt",setName);
        create_sets(0.9,"Q09","197346.txt",setName);
        create_sets(0.9,"Q10","199879.txt",setName);
    }


    //This method creates a file with the percentage of phrases we want and stores it in the folder-set
    public static void create_sets(double percentage,String Q,String collection_file,String set_name){
        try {
            ArrayList<String> query = new ArrayList<String>();
            FileReader fr = new FileReader("C:/Users/Demetra/Desktop/Collection_1/"+collection_file);
            LineNumberReader lnr = new LineNumberReader(fr);
            int countOfLines = 0;
            String phrase=lnr.readLine();
            //Count the number of phrases and save the phrases in an arraylist
            while (phrase != null) {
                query.add(phrase);
                countOfLines++;
                phrase=lnr.readLine();
            }
            lnr.close();

            PrintWriter writer = new PrintWriter("C:/Users/Demetra/Desktop/"+set_name+"/new_"+Q+".txt", "UTF-8");
            //Write in txt file the percentage of phrases we want
            for(int i=0; i<=countOfLines*percentage-1; i++){
                writer.println(query.get(i));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}