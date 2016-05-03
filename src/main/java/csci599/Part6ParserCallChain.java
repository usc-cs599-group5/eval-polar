package csci599;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

public class Part6ParserCallChain
{
    Tika tika;
    int count;
    int total;
    static ObjectMapper mapper;
    HashMap<String,String> opennlp=new HashMap<>();
    HashMap<String,String> corenlp=new HashMap<>();
    HashMap<String,String> nltk=new HashMap<>();
    HashMap<String, HashMap<String,Ratios>> output1=new HashMap<>();
    HashMap<String, HashMap<String,Double>> output2=new HashMap<>();
    HashMap<String, HashMap<String,Double>> output3=new HashMap<>();
    
    Part6ParserCallChain()
    {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        tika = new Tika();
        count=0;
        total=0;
        opennlp=readJSON("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part6\\","Part6OpenNlp.json");
        corenlp=readJSON("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part6\\","Part6CoreNLP.json");
        nltk=readJSON("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part6\\","Part6NLTK.json");
    }
    
    public void extract(File folder)
    {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                extract(file);
            } else {
                count++;
                System.out.println("**************************************************"+count+" "+file.getName()+"********************************************");
                try {
                    if(opennlp.containsKey(file.getName())&&corenlp.containsKey(file.getName())&&nltk.containsKey(file.getName()))
                    {
                        total++;
                        String data=parse(file);
                        double fileSize=file.length();
                        double text=data.length();
                        double metaOpenNLP=opennlp.get(file.getName()).length();
                        double metaCoreNLP=corenlp.get(file.getName()).length();
                        double metaNLTK=nltk.get(file.getName()).length();
                        addToOutput("OpenNLP",tika.detect(file),text/fileSize,metaOpenNLP/fileSize);
                        addToOutput("CoreNLP",tika.detect(file),text/fileSize,metaCoreNLP/fileSize);
                        addToOutput("NLTK",tika.detect(file),text/fileSize,metaNLTK/fileSize);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Part6ParserCallChain.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        }
        for(String s1:output1.keySet())
        {
            HashMap<String,Ratios> op1=output1.get(s1);
            for(String s2:op1.keySet())
            {
                Ratios r=op1.get(s2);
                r.text2fileSize=(r.text2fileSize/total)*100;
                r.metadata2fileSize=(r.metadata2fileSize/total)*100;
                op1.replace(s2, r);
            }
            HashMap<String,Double> op2=output2.get(s1);
            for(String s2:op2.keySet())
            {
                double t2f=op2.get(s2);
                t2f=t2f/total;
                op2.replace(s2, t2f);
            }
            HashMap<String,Double> op3=output3.get(s1);
            for(String s2:op3.keySet())
            {
                double m2f=op3.get(s2);
                m2f=m2f/total;
                op3.replace(s2, m2f);
            }
        }
        JSONReaderWriter.JSONWriter("outputs/part6/part6_1.json", output1);
        JSONReaderWriter.JSONWriter("outputs/part6/part6_2.json", output2);
        JSONReaderWriter.JSONWriter("outputs/part6/part6_3.json", output3);
    }
    //Extracts body contents from the file
    public static String parse(File f)
    {
        BodyContentHandler handler = new BodyContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = new FileInputStream(f)) 
        {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
        catch(Exception e)
        {
            System.out.println("Exception occurred " + e); 
        }
        return "";
    }
    
    public static HashMap<String,String> readJSON(String filePath,String fileName)
    {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(filePath+fileName), new TypeReference<Map<String, String>>() { });
        } catch (IOException ex) {
            Logger.getLogger(Part6ParserCallChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    void addToOutput(String parserName,String mimeType,double text2file,double meta2file)
    {
        if(!output1.containsKey(parserName))
        {
            Ratios r=new Ratios();
            r.text2fileSize=text2file;
            r.metadata2fileSize=meta2file;
            HashMap<String, Ratios> op1=new HashMap<>();
            HashMap<String, Double> op2=new HashMap<>();
            HashMap<String, Double> op3=new HashMap<>();
            op1.put(mimeType, r);
            op2.put(mimeType, text2file);
            op3.put(mimeType, meta2file);
            output1.put(parserName, op1);
            output2.put(parserName, op2);
            output3.put(parserName, op3);
        }
        else
        {
            if(output1.get(parserName).get(mimeType)!=null)
            {
                Ratios r=output1.get(parserName).get(mimeType);
                r.text2fileSize=(r.text2fileSize+text2file);
                r.metadata2fileSize=(r.metadata2fileSize+meta2file);
                double t2f=output2.get(parserName).get(mimeType);
                t2f=(t2f+text2file);
                double m2f=output3.get(parserName).get(mimeType);
                m2f=(m2f+meta2file);
                HashMap<String, Ratios> op1=new HashMap<>();
                output1.get(parserName).replace(mimeType, r);
                output2.get(parserName).replace(mimeType, t2f);
                output3.get(parserName).replace(mimeType, m2f);
            }
            else
            {
                Ratios r=new Ratios();
                r.text2fileSize=text2file;
                r.metadata2fileSize=meta2file;
                HashMap<String, Ratios> op1=new HashMap<>();
                op1.put(mimeType, r);
                output1.get(parserName).put(mimeType, r);
                output2.get(parserName).put(mimeType, text2file);
                output3.get(parserName).put(mimeType, meta2file);
            }
        }
    }
}

class MeasurementsJSON1
{
    @JsonProperty("id")
    String id;
    @JsonProperty("ContentType")
    String ContentType;
    @JsonProperty("FileSize")
    String FileSize;
    @JsonProperty("names")
    Set<String> names;
    @JsonProperty("units")
    Set<String> units;
}

class Ratios
{
    double text2fileSize;
    double metadata2fileSize;
}