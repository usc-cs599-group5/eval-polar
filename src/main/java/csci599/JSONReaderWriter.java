package csci599;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;

public class JSONReaderWriter
{
    static void JSONWriter(String filePath, Object object)
    {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            //objectMapper.setSerializationInclusion(Include.NON_NULL);
            objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            objectMapper.writeValue(new FileWriter(filePath, true), object);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    
}
