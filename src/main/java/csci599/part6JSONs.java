package csci599;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class part6JSONs
{
    static ObjectMapper mapper;
    static HashMap<String,String> opennlp=new HashMap<>();
    
    
    static void part6JSONs()
    {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        //MeasurementsJSON[] mjson1=readJSON("","nltk.json");
        MeasurementsJSON[] mjson2=readJSON("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part6\\","corenlp.json");
        //MeasurementsJSON[] mjson3=readJSON("","corenlp.json");
        for(int i=0;i<mjson2.length;i++)
        {
            //System.out.println(mjson2[i].id);
            String data="{";
            data=data+"'id': '"+mjson2[i].id+"', 'contentType': '"+mjson2[i].ContentType+"', 'fileSize': '"+mjson2[i].FileSize+"', 'names': {";
            for(String s:mjson2[i].names)
                data=data+"'"+s+"',";
            data=data.substring(0, data.length()-1);
            data=data+"}, 'units': {";
            for(String s:mjson2[i].units)
                data=data+"'"+s+"',";
            data=data.substring(0, data.length()-1);
            data=data+"}";
            data=data+"}";
            opennlp.put(mjson2[i].id, data);
        }
        writeJSON("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part6\\","Part6CoreNLP");
    }
    
    public static MeasurementsJSON[] readJSON(String filePath,String fileName)
    {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(filePath+fileName), MeasurementsJSON[].class);
        } catch (IOException ex) {
            Logger.getLogger(Part10measurements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void writeJSON(String filePath, String fileName)
    {
        try {
                mapper.writeValue(new File(filePath+fileName+".json"), opennlp);
            } catch (IOException ex) {
                Logger.getLogger(Part10measurements.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
