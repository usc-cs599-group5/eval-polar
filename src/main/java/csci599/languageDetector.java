package csci599;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import org.apache.tika.Tika;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

public class languageDetector {
    private final HashMap<String,String> lang;
    public static HashMap<String,Integer> langDiversity;
    languageDetector(){
        lang = new HashMap();
        lang.put("da","Danish");
        lang.put("de","German");
        lang.put("et","Estonian");
        lang.put("el","Greek");
        lang.put("en","English");
        lang.put("es","Spanish");
        lang.put("fi","Finnish");
        lang.put("fr","French");
        lang.put("hu","Hungarian");
        lang.put("is","Icelandic");
        lang.put("it","Italian");
        lang.put("nl","Dutch");
        lang.put("no","Norwegian");
        lang.put("pl","Polish");
        lang.put("pt","Portuguese");
        lang.put("ru","Russian");
        lang.put("sv","Swedish");
        lang.put("th","Thai");   
        langDiversity = new HashMap();
        langDiversity.put("Danish",0);
        langDiversity.put("German",0);
        langDiversity.put("Estonian",0);
        langDiversity.put("Greek",0);
        langDiversity.put("English",0);
        langDiversity.put("Spanish",0);
        langDiversity.put("Finnish",0);
        langDiversity.put("French",0);
        langDiversity.put("Hungarian",0);
        langDiversity.put("Icelandic",0);
        langDiversity.put("Italian",0);
        langDiversity.put("Dutch",0);
        langDiversity.put("Norwegian",0);
        langDiversity.put("Polish",0);
        langDiversity.put("Portuguese",0);
        langDiversity.put("Russian",0);
        langDiversity.put("Swedish",0);
        langDiversity.put("Thai",0); 
        
        
    }
    //Do this for all part
    public void forallfiles(File folder){
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                int count =0;
                if(file.length() > 0)
                {   
                    count = count+1;
                    System.out.println(file.getName() + " Count " + count);
                    detect(file);
                }
            } else {
                System.out.println(file.getAbsolutePath().substring(3));
                forallfiles(file);
            }
        }
    }
    //Detect language of file
    public void detect(File file){
        try{
            HashMap<String,Object> data = new HashMap();
            Tika tika = new Tika();
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream content = new FileInputStream(file);
            parser.parse(content, handler, metadata, new ParseContext());
            LanguageIdentifier object = new LanguageIdentifier(handler.toString());
            String language = lang.get(object.getLanguage());           
            String mediaType = tika.detect(file);
            double fileLength = (double)file.length()/1024;
            data.put("ID", file.getName());
            data.put("Language", language);
            data.put("ContentType", mediaType);
            data.put("FileSize",fileLength);
            int count = langDiversity.get(language) + 1;
            langDiversity.put(language,count);
            JSONReaderWriter.JSONWriter("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_7\\languageDetector_CommonCrawl.json", data);
        }
        catch(Exception e){
            System.out.println("Exception occured" + e);
        }   
    }
}
