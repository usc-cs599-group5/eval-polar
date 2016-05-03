package csci599;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

public class getData
{
    //Extracts body contents from the file
    public static void parseToHTML(File folder)
    {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                parseToHTML(file);
            } else {
                BodyContentHandler handler = new BodyContentHandler();
                AutoDetectParser parser = new AutoDetectParser();
                Metadata metadata = new Metadata();
                try (InputStream stream = new FileInputStream(file)) 
                {
                    parser.parse(stream, handler, metadata);
                    //System.out.println(trimXHTMLData(handler.toString()));
                    String data=trimXHTMLData(handler.toString());
                    if(data.length()>0)
                    {
                        FileWriter fw=new FileWriter("F:\\Assignment3\\data1000PDFs\\"+file.getName(),false);
                        fw.write(data);
                        fw.close();
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Exception occurred " + e); 
                }
            }
        }
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
