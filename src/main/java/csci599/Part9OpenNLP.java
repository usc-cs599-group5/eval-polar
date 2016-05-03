package csci599;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ner.opennlp.OpenNLPNERecogniser;
import org.apache.tika.sax.BodyContentHandler;

public class Part9OpenNLP
{
    static Map<String, Set<String>> onlpdata;
    static OpenNLPNERecogniser name;
    Tika tika;
    //Initializes the OpenNLP Models
    Part9OpenNLP()
    {
        Map<String,String> models=new HashMap<String,String>();
        models.put("PERSON", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-person.bin");
        models.put("LOCATION", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-location.bin");
        models.put("ORGANIZATION", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-organization.bin");
        models.put("DATE", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-date.bin");
        models.put("TIME", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-time.bin");
        models.put("PERCENT", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-percentage.bin");
        models.put("MONEY", "opennlp-ner-models/org/apache/tika/parser/ner/opennlp/en-ner-money.bin");
        name = new OpenNLPNERecogniser(models);
        tika = new Tika();
    }
    //Extract OpenNLP entities from all the files in the dataset
    public void extract(File folder)
    {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                extract(file);
            } else {
                String text=parseToHTML(file);
                double fileSize=file.length()/1024;
                String contentType="";
                try {
                    contentType=tika.detect(file);
                } catch (IOException ex) {
                    Logger.getLogger(Part9OpenNLP.class.getName()).log(Level.SEVERE, null, ex);
                }
                onlpdata=name.recognise(text);
                OpenNLP_JSON json=new OpenNLP_JSON();
                json.id=file.getName();
                json.ContentType=contentType;
                json.FileSize=fileSize+" KB";
                json.OpenNLP=onlpdata;
                OpenNLP_Names combined=new OpenNLP_Names();
                combined.id=file.getName();
                combined.ContentType=contentType;
                combined.FileSize=fileSize+" KB";
                combined.names=new HashSet<String>();
                combined.units=new HashSet<String>();
                //combined.date=new HashSet<String>();
                for(String s1:onlpdata.keySet())
                {
                    //if(s1.equalsIgnoreCase("PERSON")||s1.equalsIgnoreCase("LOCATION")||s1.equalsIgnoreCase("ORGANIZATION"))
                    if(s1.equalsIgnoreCase("PERSON")||s1.equalsIgnoreCase("LOCATION")||s1.equalsIgnoreCase("ORGANIZATION")||s1.equalsIgnoreCase("DATE")||s1.equalsIgnoreCase("TIME"))
                    {
                        Iterator i=onlpdata.get(s1).iterator();
                        while(i.hasNext())
                            combined.names.add((String) i.next());
                    }
                    if(s1.equalsIgnoreCase("PERCENT")||s1.equalsIgnoreCase("MONEY"))
                    {
                        Iterator i=onlpdata.get(s1).iterator();
                        while(i.hasNext())
                            combined.units.add((String) i.next());
                    }
                    /*if(s1.equalsIgnoreCase("DATE")||s1.equalsIgnoreCase("TIME"))
                    {
                        Iterator i=onlpdata.get(s1).iterator();
                        while(i.hasNext())
                            combined.date.add((String) i.next());
                    }*/
                }
                JSONReaderWriter.JSONWriter("outputs/part9/opennlp.json", json);
                JSONReaderWriter.JSONWriter("outputs/part9/opennlpNames.json", combined);
            }
        }
    }
    //Extracts body contents from the file
    public static String parseToHTML(File f)
    {
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = new FileInputStream(f)) 
        {
            parser.parse(stream, handler, metadata);
            return trimXHTMLData(handler.toString());
        }
        catch(Exception e)
        {
            System.out.println("Exception occurred " + e); 
        }
        return "";
    }
    //Trims the extracted contents of the file
    public static String trimXHTMLData(String contents) throws IOException
    {
        BufferedReader  br= new BufferedReader(new StringReader(contents));
        String line="";
        StringBuffer xhtml=new StringBuffer();
        int i = 0;
        while ((line = br.readLine()) != null)
        {

            if (!line.trim().isEmpty()) 
            {    
                 if (i > 0)
                    xhtml.append("\n");
                 xhtml.append(line.trim());
                 i++;
                 
            }
        }
        return xhtml.toString();
    }
}
//For Combined features
class OpenNLP_Names
{
    String id;
    String ContentType;
    String FileSize;
    Set<String> names;
    Set<String> units;
    //Set<String> date;
}
//For OpenNLP features
class OpenNLP_JSON
{
    String id;
    Map<String, Set<String>> OpenNLP;
    String ContentType;
    String FileSize;
}