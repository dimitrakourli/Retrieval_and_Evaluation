import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

//In this class we convert XML files and we unite them into a Json file in order to upload it in Elasticsearch

public class XML_JSON {

    public static void main(String argv[]) {
        //Open the folder with XML files and create a list with the files
        File folder = new File("C:/Users/Demetra/Desktop/Documents");
        File[] listOfFiles = folder.listFiles();

        //For each file change tags as requested and save the new XML files
        for(int i=0; i<listOfFiles.length; i++){
            try {
                if(listOfFiles[i].isDirectory()){i++;}
                String filepath = "C:/Users/Demetra/Desktop/Documents/"+listOfFiles[i].getName();
                System.out.println(filepath);
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(filepath);

                Node project = doc.getFirstChild();

                Node rcn=doc.getElementsByTagName("rcn").item(0);
                Node acronym=doc.getElementsByTagName("acronym").item(0);
                Node identifier=doc.getElementsByTagName("identifier").item(0);
                Node title=doc.getElementsByTagName("title").item(0);
                Node objective=doc.getElementsByTagName("objective").item(0);

                Element text =doc.createElement("text");
                project.appendChild(text);
                project.replaceChild(text,objective);
                project.replaceChild(identifier,title);
                project.removeChild(project.getLastChild());
                text.setTextContent(title.getTextContent()+" : "+objective.getTextContent());

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("C:/Users/Demetra/Desktop/Documents/NEW_XMLS/"+"NEW_"+listOfFiles[i].getName()));

                transformer.transform(source, result);

                System.out.println("File saved!");

            } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (SAXException sae) {
                sae.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }

        //Open the folder with the new XML files and create a list with the files
        File folder_new_xmls = new File("C:/Users/Demetra/Desktop/Documents/NEW_XMLS");
        File[] listOfXMLs = folder_new_xmls.listFiles();

        String line="",str="",line2="";
        try {
            //Create the final json file which is going to be used in order to put the documents  with curl at once
            File file = new File("C:/Users/Demetra/Desktop/Documents/NEW_XMLS/JSON_FILE/" + "projects.json");
            int index_num=1;
            PrintWriter pw = new PrintWriter(file);
            for(int j=0; j<listOfXMLs.length; j++) {

                if(listOfXMLs[j].isFile()) {
                    String filepath_xmlNEW = "C:/Users/Demetra/Desktop/Documents/NEW_XMLS/" + listOfXMLs[j].getName();
                    System.out.println(filepath_xmlNEW);

                    BufferedReader br = new BufferedReader(new FileReader(filepath_xmlNEW));
                    boolean dont_read_first=false;
                    while ((line = br.readLine()) != null ) {
                        if(line!=null) {
                            if (dont_read_first) {
                                str += line;
                            }
                            line2=line;
                            dont_read_first = true;
                        }else{
                            break;
                        }
                    }
                    str=str.substring(0,str.length()-line2.length());
                    str.replaceAll("\\p{Cntrl}", "");
                    JSONObject jsondata = XML.toJSONObject(str);

                    pw.write("{\"index\":{\"_id\":\""+index_num+"\"}}");
                    pw.write("\n");
                    pw.write(jsondata.toString());
                    index_num++;
                    pw.write("\n");
                    line = "";
                    str = "";
                }
            }
            pw.write("\n");
            pw.flush();
            pw.close();
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}