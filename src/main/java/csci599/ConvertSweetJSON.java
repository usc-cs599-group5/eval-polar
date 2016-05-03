
package csci599;

import java.io.File;
import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;

public class ConvertSweetJSON {
    
    public static void convert(){
       ArrayList al = new ArrayList();
       try{
           HashMap<String,ArrayList<Object>> result = new ObjectMapper().readValue(new File("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_8\\Sweet_Output.json"), HashMap.class); 
           System.out.println(result.size());
           for(String s : result.keySet()){
               HashMap<String,Object> output = new HashMap();
               
               System.out.println(s);
               String h = s.substring(s.indexOf("NER_Sweet_")+10);
               output.put("text",h);
               int size = result.get(s).size() + 10;
               output.put("size",size);
               al.add(output);
               System.out.println(h);
           }
           Iterator itr = al.iterator();
           while(itr.hasNext()){
               System.out.println(itr.next());
           }
           JSONReaderWriter.JSONWriter("D:\\Studies\\USC\\2nd_Sem\\CSCI599\\HW3\\Outputs\\Part_8\\SweetD3_output.json", al);
           //Json ma rakhvanu che
       }
       catch(Exception e){
           System.out.println("Exception occured" + e);
       }
       
    }
    
}
