package csci599;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.Tika;

public class getContentType
{
    public static void writeContentType(String ipFile,String opFile)
    {
        File file=new File(ipFile);
        Tika tika=new Tika();
        try {
            String contentType=tika.detect(file);
            FileWriter fw=new FileWriter(opFile,false);
            fw.write("\"ContentType\":\""+contentType+"\",\n");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(getContentTypeAndFileSize.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
