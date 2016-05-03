package csci599;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class getFileSize
{
    static void getFileSize(File file,String tempFile)
    {
        try {
            FileWriter fw=new FileWriter(tempFile,false);
            fw.write("File-Size "+(file.length()/1024)+" KB\n");
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(getFileSize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
