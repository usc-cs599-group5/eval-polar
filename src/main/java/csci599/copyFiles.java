package csci599;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

public class copyFiles
{
    private static Tika tika = new Tika();
    int[] count;
    int countT;
    
    copyFiles()
    {
         count=new int[15];
         countT=0;//counts total number of files
    }

    public void copy(File folder,String dest)
    {
        if(countT>=10000)
            return;
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                try{
                copy(file,dest);
                }
                catch (NullPointerException ex) {
                    //Logger.getLogger(copyFiles.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(file.getAbsolutePath());
                }
            } else {
                try {
                    String contentType=tika.detect(file);
                    //System.out.println(file.getAbsolutePath());
                    System.out.println(countT);
                    if(contentType.equalsIgnoreCase("application/pdf")&&count[0]<=3500)   
                    {
                        FileUtils.copyFileToDirectory(file,new File(dest+"\\pdf"));
                        count[0]++;
                        countT++;
                    }
                    if(contentType.equalsIgnoreCase("application/xhtml+xml")&&count[1]<=1000)
                    {
                        FileUtils.copyFileToDirectory(file,new File(dest+"\\xhtml"));
                        count[1]++;
                        countT++;
                    }
                    /*if(tika.detect(file).equalsIgnoreCase("image/gif")&&count[2]<=500)
                    {
                    FileUtils.copyFileToDirectory(file,dest);
                    count[2]++;
                    countT++;
                    }
                    if(tika.detect(file).equalsIgnoreCase("image/png")&&count[3]<=500)
                    {   
                    FileUtils.copyFileToDirectory(file,dest);
                    count[3]++;
                    countT++;
                    }
                    if(tika.detect(file).equalsIgnoreCase("application/pdf")&&count[4]<=250)
                    {   
                    FileUtils.copyFileToDirectory(file,dest);
                    count[4]++;
                    countT++;
                    }
                    if(tika.detect(file).equalsIgnoreCase("audio/mpeg")&&count[5]<=500)
                    {   
                    FileUtils.copyFileToDirectory(file,dest);
                    count[5]++;
                    countT++;
                    }
                    if(tika.detect(file).equalsIgnoreCase("image/jpeg")&&count[6]<=500)
                    {   
                    FileUtils.copyFileToDirectory(file,dest);
                    count[6]++;
                    countT++;
                    }
                    if(tika.detect(file).equalsIgnoreCase("application/mp4")&&count[7]<=500)
                    {   
                    FileUtils.copyFileToDirectory(file,dest);
                    count[7]++;
                    countT++;
                    }
                    if(tika.detect(file).equalsIgnoreCase("application/rss")&&count[8]<=250)
                    {   
                    FileUtils.copyFileToDirectory(file,dest);
                    count[8]++;
                    countT++;
                    }*/
                    if(contentType.equalsIgnoreCase("text/html")&&count[9]<=3500)
                    {   
                        FileUtils.copyFileToDirectory(file,new File(dest+"\\html"));
                        count[9]++;
                        countT++;
                    }
                    /*if(tika.detect(file).equalsIgnoreCase("application/octet-stream")&&count[10]<=250)
                    {
                    FileUtils.copyFileToDirectory(file,dest);
                    count[10]++;
                    countT++;
                    }*/
                    if(contentType.equalsIgnoreCase("text/plain")&&count[11]<=1000)
                    {
                        FileUtils.copyFileToDirectory(file,new File(dest+"\\plain"));
                        count[11]++;
                        countT++;
                    }
                    if(contentType.equalsIgnoreCase("application/xml")&&count[12]<=1000)
                    {
                        FileUtils.copyFileToDirectory(file,new File(dest+"\\xml"));
                        count[12]++;
                        countT++;
                    }
                    /*if(tika.detect(file).equalsIgnoreCase("video/mp4")&&count[13]<=250)
                    {
                    FileUtils.copyFileToDirectory(file,dest);
                    count[13]++;            
                    countT++;
                    }*/
                    if(contentType.equalsIgnoreCase("application/msword")&&count[14]<=1000)
                    {
                        FileUtils.copyFileToDirectory(file,new File(dest+"\\msword"));
                        count[14]++;
                        countT++;
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(copyFiles.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
            }
        }
    }
}