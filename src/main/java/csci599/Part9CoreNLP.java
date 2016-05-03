package csci599;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Part9CoreNLP
{
    public void createJSON(File file,String json)
    {
        BufferedReader br = null;
        try {
            br=new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Part9CoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line="";
        try {
            CoreNLP_Names combined = null;
            while((line=br.readLine())!=null)
            {
                if(line.contains("CSCI599FileName"))
                {
                    if(combined!=null)
                    {
                        if(combined.names==null)
                            combined.names=new HashSet<String>();
                        if(combined.units==null)
                            combined.units=new HashSet<String>();
                        JSONReaderWriter.JSONWriter("outputs/part9/corenlp/corenlp.json", combined);
                    }
                    combined=new CoreNLP_Names();
                    String[] temp=line.split(" ");
                    combined.id=temp[1];
                }
                else if(line.contains("Content-Type"))
                {
                    String[] temp=line.split(" ");
                    combined.ContentType=temp[1];
                }
                else if(line.contains("File-Size"))
                {
                    String[] temp=line.split(" ");
                    combined.FileSize=temp[1]+" "+temp[2];
                }
                else
                {
                    String[] temp=line.split(":");
                    if(temp[0].contains("NER_PERSON")||temp[0].contains("NER_LOCATION")||temp[0].contains("NER_ORGANIZATION")||temp[0].contains("NER_DATE")||temp[0].contains("NER_TIME"))
                    {
                        if(combined.names==null)
                            combined.names=new HashSet<String>();
                        combined.names.add(temp[1]);
                    }
                    else if(temp[0].contains("NER_PERCENT")||temp[0].contains("NER_MONEY"))
                    {
                        if(combined.units==null)
                            combined.units=new HashSet<String>();
                        combined.units.add(temp[1]);
                    }
                }
            }
            if(combined.names==null)
                combined.names=new HashSet<String>();
            if(combined.units==null)
                combined.units=new HashSet<String>();
            JSONReaderWriter.JSONWriter("outputs/part9/corenlp/corenlp.json", combined);
        } catch (IOException ex) {
            Logger.getLogger(Part9CoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//For Combined features
class CoreNLP_Names
{
    String id;
    String ContentType;
    String FileSize;
    Set<String> names;
    Set<String> units;
    //Set<String> date;
}
//For CoreNLP features
class CoreNLP_JSON
{
    String id;
    Map<String, Set<String>> OpenNLP;
    String ContentType;
    String FileSize;
}