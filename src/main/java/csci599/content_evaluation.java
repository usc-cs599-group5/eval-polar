package csci599;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class content_evaluation
{
    public static void main(String[] args)
    {
        switch(args[0])
        {
            case "copy":
            {
                copyFiles cf=new copyFiles();
                cf.copy(new File("G:\\latest-commoncrawl"),"E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\common-crawn-data");
            }
            case "getdata":
            {
                getData.parseToHTML(new File(args[1]));
                break;
            }
            case "write":
            {
                getContentTypeAndFileSize.writeContentTypeAndFileSize(args[1], args[2]);
                break;
            }
            case "contentType":
            {
                getContentType.writeContentType(args[1], args[2]);
                break;
            }
            case "text2json":
            {
                textToJSONParser t2j=new textToJSONParser();
            try {
                t2j.generateJSON(new File("F:\\Assignment3\\Content_Evaluation\\outputs\\part9\\corenlp\\outputs\\corenlp_final.txt"));
            } catch (IOException ex) {
                Logger.getLogger(content_evaluation.class.getName()).log(Level.SEVERE, null, ex);
            }
                break;
            }
            case "getFileSize":
            {
                getFileSize.getFileSize(new File(args[1]),args[2]);
                break;
            }
            case "part9":
            {
                switch(args[1])
                {
                    case "opennlp":
                    {
                        Part9OpenNLP onlp=new Part9OpenNLP();
                        onlp.extract(new File("E:\\Sem2\\CSCI599\\Test\\htmlFiles"));
                        break;
                    }
                    case "corenlp":
                    {
                        Part9CoreNLP cnlp=new Part9CoreNLP();
                        cnlp.createJSON(new File(args[2]),args[3]);
                        break;
                    }
                 }
                break;
            }
            case "part10":
            {
                Part10measurements part10=new Part10measurements();
                part10.identifySpectrum("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part10\\www.benchfly.com\\benchflydata.json");
                break;
            }
            case "part6":
            {
                Part6ParserCallChain part6=new Part6ParserCallChain();
                //part6.extract(new File("E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\common-crawn-data\\pdf"));
                part6.extract(new File("F:\\Assignment3\\polar8k"));
            }
            case "part6json":
            {
                part6JSONs.part6JSONs();
            }
            case "lang":
            {
                languageDetector k = new languageDetector();
                //path to folder
                k.forallfiles(new File("D:\\common-crawn-data"));
                //path for json file(pie_chart)
                JSONReaderWriter.JSONWriter("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_7\\languageDiversity_CommonCrawl.json", languageDetector.langDiversity);
                break;
            }
            case "convert":
            {
                ConvertSweetJSON.convert();
                break;
            }
            case "Part9AgreementParser":
            {
                try{
                ObjectMapper mapper = new ObjectMapper();
                CompositeNERAgreementParser cp = new CompositeNERAgreementParser();
                MeasurementsJSON[] corejson;
                MeasurementsJSON[] openjson;
                MeasurementsJSON[] nltkjson;
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                corejson = mapper.readValue(new File("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_9\\output\\corenlp.json"),MeasurementsJSON[].class);                
                openjson = mapper.readValue(new File("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_9\\output\\opennlp.json"),MeasurementsJSON[].class);
                nltkjson = mapper.readValue(new File("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_9\\output\\nltk.json"),MeasurementsJSON[].class);
                cp.findAgreement(corejson,openjson,nltkjson);
                break;
                }
                catch(Exception e){
                    System.out.println("Exception occured" + e);
                }
            }

        }
    }
}
