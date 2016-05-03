package csci599;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompositeNERAgreementParser {
    void findAgreement(JsonFormat[] corejson,JsonFormat[] opennlp,JsonFormat[] nltkjson)
    {
        ArrayList<String> labels = new ArrayList();
        ArrayList<Double> corevalues = new ArrayList();
        ArrayList<Double> openvalues = new ArrayList();
        ArrayList<Double> nltkvalues = new ArrayList();
        JsonOutput output = new JsonOutput();
        intermediateclass core = new intermediateclass();
        intermediateclass open = new intermediateclass();
        intermediateclass nltk = new intermediateclass();
        core.label = "CoreNLP";
        open.label = "OpenNLP";
        nltk.label = "NLTK";
        ArrayList<intermediateclass> series = new ArrayList();
        int count = 0;
        for(int i = 0 ; i < corejson.length; i++){
            count = count+1;
            System.out.println(corejson[i].id + "Count" + count);
               if(corejson[i].id.equals(opennlp[i].id) && nltkjson[i].id.equals(opennlp[i].id))
               {   
                   
                   //Get maximum no of names
                    int total = Math.max(Math.max(corejson[i].names.size(),nltkjson[i].names.size()),opennlp[i].names.size());
                    if(total != 0)
                    {
                        labels.add(corejson[i].id);
                        //Initializes all arraylist
                        ArrayList<String> corenames = new ArrayList();
                        ArrayList<String> opennames = new ArrayList();
                        ArrayList<String> nltknames = new ArrayList();
                        HashSet<String> combine = new HashSet();
                        String[] corearray = corejson[i].names.toArray(new String[corejson[i].names.size()]);
                        String[] openarray = opennlp[i].names.toArray(new String[opennlp[i].names.size()]);
                        String[] nltkarray = nltkjson[i].names.toArray(new String[nltkjson[i].names.size()]);
                        for (String corearray1 : corearray) {                        
                        corenames.addAll(Arrays.asList(corearray1.trim().split("\\s+")));   
                        }
                        corenames.removeAll(Arrays.asList(null,""));
                        //Removes duplicates from arraylist
                        Set<String> hs = new HashSet<>();
                        hs.addAll(corenames);
                        corenames.clear();
                        corenames.addAll(hs);
                        hs.clear();
                   
                        for (String openarray1 : openarray) {
                            opennames.addAll(Arrays.asList(openarray1.trim().split("\\s+")));
                        }
                        opennames.removeAll(Arrays.asList(null,""));
                        hs.addAll(opennames);
                        opennames.clear();
                        opennames.addAll(hs);
                        hs.clear();
                   
                        for (String nltkarray1 : nltkarray) {                        
                            nltknames.addAll(Arrays.asList(nltkarray1.trim().split("\\s+")));   
                        }
                        nltknames.removeAll(Arrays.asList(null,""));
                        hs.addAll(nltknames);
                        nltknames.clear();
                        nltknames.addAll(hs);
                        hs.clear();                 
                        //find intersect percentage
                        //Combine all unique values in set
                        combine.addAll(nltknames);
                        combine.addAll(opennames);
                        combine.addAll(corenames);
                        //Calculate match
                        
                        //Calculate intersection
                        HashSet<String> temp = new HashSet();
                        temp.addAll(combine);
                        temp.retainAll(nltknames);
                        int nltk_actual = temp.size();
                        temp.clear();
                        
                        temp.addAll(combine);
                        temp.retainAll(corenames);
                        int core_actual = temp.size();
                        temp.clear();
                        
                        temp.addAll(combine);
                        temp.retainAll(opennames);
                        int open_actual = temp.size();
                        temp.clear();  
                        System.out.println("open " + open_actual );
                        System.out.println("nltk " + nltk_actual );
                        System.out.println("core " + core_actual );
                        System.out.println("Combine " + combine.size());
                        double openpercent = ((double)open_actual/combine.size()) * 100;
                        double corepercent = ((double)core_actual/combine.size()) * 100;
                        double nltkpercent = ((double)nltk_actual/combine.size()) * 100;
                        System.out.println("OpenNLP : " + openpercent);
                        System.out.println("CoreNLP : " + corepercent);
                        System.out.println("NLTKNames : " + nltkpercent);
                        corevalues.add(corepercent);
                        openvalues.add(openpercent);
                        nltkvalues.add(nltkpercent);

                        
                    }
               }

        } 
        open.values = openvalues;
        core.values = corevalues;
        nltk.values = nltkvalues;       
        series.add(open);
        series.add(core);
        series.add(nltk);
        output.series = series;
        output.labels = labels;
        JSONReaderWriter.JSONWriter("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_9\\part9output.json", output);
        
    }   
}

class JsonOutput{
    ArrayList<String> labels;
    ArrayList<intermediateclass> series;
}
class intermediateclass{
    String label;
    ArrayList<Double> values;
}


