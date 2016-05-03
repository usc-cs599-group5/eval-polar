package csci599;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part10measurements
{
    static ObjectMapper mapper;
    static HashMap<String,Values> measurements;
    static HashMap<String,Values> measurementTypes;
    static HashMap<String,Values> measurementContentTypes;
    static HashMap<String,Spectrum> measurementSpectrum;
    static HashMap<String,Spectrum> measurementTypesSpectrum;
    static HashMap<String,Spectrum> measurementContentTypesSpectrum;
    
    Part10measurements()
    {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        measurements=new HashMap<>();
        measurementTypes=new HashMap<>();
        measurementContentTypes=new HashMap<>();
        measurementSpectrum=new HashMap<>();
        measurementTypesSpectrum=new HashMap<>();
        measurementContentTypesSpectrum=new HashMap<>();
    }
    
    public static void identifySpectrum(String ipJSON)
    {
        MeasurementsJSON[] mjson=readJSON(ipJSON);
        for(int i=0;i<mjson.length;i++)
        {
            Set<String> units=mjson[i].units;
            String contentType=mjson[i].ContentType;
            
            for(String s:units)
            {
                if(s.matches(".*\\d+.*"))
                {
                    String str=s.toLowerCase();
                    String unitValue=str.replaceAll("\\D+","");
                    double val=Double.parseDouble(unitValue);
                    updateContentTypes(contentType,val);
                    if(str.contains("celsius")||isContain(str,"c")||str.contains("fahrenheit")||isContain(str,"f"))
                    {
                        updateTypes("temperature",val);
                        if(str.contains("celsius")||isContain(str,"c"))
                        {
                            updateUnits("celcius",val);
                        }
                        if(str.contains("fahrenheit")||isContain(str,"f"))
                        {
                            updateUnits("fahrenheit",val);
                        }
                    }
                    else if(isContain(str,"kilometer")||isContain(str,"kilometers")||isContain(str,"km")||isContain(str,"meter")||isContain(str,"meters")||isContain(str,"m")||isContain(str,"mile")||isContain(str,"miles")||isContain(str,"mi")||isContain(str,"inch")||isContain(str,"inches")||isContain(str,"in")||isContain(str,"foot")||str.contains("feet")||isContain(str,"ft"))
                    {
                        updateTypes("distance",val);
                        if(isContain(str,"kilometer")||isContain(str,"kilometers")||isContain(str,"km"))
                        {
                            updateUnits("kilometer",val);
                        }
                        if(isContain(str,"meter")||isContain(str,"meters")||isContain(str,"m"))
                        {
                            updateUnits("meter",val);
                        }
                        if(isContain(str,"mile")||isContain(str,"miles")||isContain(str,"mi"))
                        {
                            updateUnits("mile",val);
                        }
                        if(isContain(str,"inch")||isContain(str,"inches")||isContain(str,"in"))
                        {
                            updateUnits("inch",val);
                        }
                        if(isContain(str,"foot")||isContain(str,"feet")||isContain(str,"ft"))
                        {
                            updateUnits("foot",val);
                        }
                    }
                    else if(isContain(str,"kilogram")||isContain(str,"kilograms")||isContain(str,"kg")||isContain(str,"pound")||isContain(str,"pounds")||isContain(str,"lb")||isContain(str,"gram")||isContain(str,"grams")||isContain(str,"g")||isContain(str,"ounce")||isContain(str,"ounces")||isContain(str,"oz")||isContain(str,"ton"))
                    {
                        updateTypes("weight",val);
                        if(isContain(str,"kilogram")||isContain(str,"kilograms")||isContain(str,"km"))
                        {
                            updateUnits("kilogram",val);
                        }
                        if(isContain(str,"pound")||isContain(str,"pounds")||isContain(str,"lb"))
                        {
                            updateUnits("pound",val);
                        }
                        if(isContain(str,"gram")||isContain(str,"grams")||isContain(str,"g"))
                        {
                            updateUnits("gram",val);
                        }
                        if(isContain(str,"ounce")||isContain(str,"ounces")||isContain(str,"oz"))
                        {
                            updateUnits("ounce",val);
                        }
                        if(isContain(str,"ton"))
                        {
                            updateUnits("ton",val);
                        }
                    }
                    else if(isContain(str,"acre"))
                    {
                        updateTypes("area",val);
                        updateUnits("acre",val);
                    }
                    else if(isContain(str,"bit")||isContain(str,"bits")||isContain(s,"b")||isContain(str,"byte")||isContain(str,"bytes")||isContain(s,"B")||isContain(str,"kilobyte")||isContain(str,"kilobytes")||isContain(str,"kb")||isContain(str,"megabyte")||isContain(str,"megabytes")||isContain(str,"mb")||isContain(str,"gigabyte")||isContain(str,"gigabytes")||isContain(str,"gb")||isContain(str,"terabyte")||isContain(str,"terabytes")||isContain(str,"tb"))
                    {
                        updateTypes("data_byte",val);
                        if(isContain(str,"bit")||isContain(str,"bits")||isContain(s,"b"))
                        {
                            updateUnits("bit",val);
                        }
                        if(isContain(str,"byte")||isContain(str,"bytes")||isContain(s,"B"))
                        {
                            updateUnits("byte",val);
                        }
                        if(isContain(str,"kilobyte")||isContain(str,"kilobytes")||isContain(str,"kb"))
                        {
                            updateUnits("kilobyte",val);
                        }
                        if(isContain(str,"megabyte")||isContain(str,"megabytes")||isContain(str,"mb"))
                        {
                            updateUnits("megabyte",val);
                        }
                        if(isContain(str,"gigabyte")||isContain(str,"gigabytes")||isContain(str,"gb"))
                        {
                            updateUnits("gigabyte",val);
                        }
                        if(isContain(str,"terabyte")||isContain(str,"terabytes")||isContain(str,"tb"))
                        {
                            updateUnits("terabyte",val);
                        }
                    }
                    else if(str.contains("year")||str.contains("month")||str.contains("week")||str.contains("day")||str.contains("hour")||str.contains("minute")||str.contains("second")||isContain(str,"am")||isContain(str,"a.m.")||isContain(str,"pm")||isContain(str,"p.m."))
                    {
                        updateTypes("time",val);
                        if(str.contains("year"))
                        {
                            updateUnits("year",val);
                        }
                        if(str.contains("month"))
                        {
                            updateUnits("month",val);
                        }
                        if(str.contains("week"))
                        {
                            updateUnits("week",val);
                        }
                        if(str.contains("day"))
                        {
                            updateUnits("day",val);
                        }
                        if(str.contains("hour"))
                        {
                            updateUnits("hour",val);
                        }
                        if(str.contains("minute"))
                        {
                            updateUnits("minute",val);
                        }
                        if(str.contains("second"))
                        {
                            updateUnits("second",val);
                        }
                        if(isContain(str,"am")||isContain(str,"a.m."))
                        {
                            updateUnits("am",val);
                        }
                        if(isContain(str,"pm")||isContain(str,"p.m."))
                        {
                            updateUnits("pm",val);
                        }
                    }
                    else if(isContain(str,"hertz")||isContain(str,"hz")||isContain(str,"kilohertz")||isContain(str,"khz")||isContain(str,"megahertz")||isContain(str,"mhz")||isContain(str,"gigahertz")||isContain(str,"ghz"))
                    {
                        updateTypes("frequency",val);
                        if(isContain(str,"hertz")||isContain(str,"hz"))
                        {
                            updateUnits("hertz",val);
                        }
                        if(isContain(str,"kilohertz")||isContain(str,"khz"))
                        {
                            updateUnits("kilohertz",val);
                        }
                        if(isContain(str,"megahertz")||isContain(str,"mhz"))
                        {
                            updateUnits("megahertz",val);
                        }
                        if(isContain(str,"gigahertz")||isContain(str,"ghz"))
                        {
                            updateUnits("gigahertz",val);
                        }
                    }
                    else if(isContain(str,"degree"))
                    {
                        updateTypes("angle",val);
                        updateUnits("degree",val);
                    }
                    else if(str.contains("percent")||str.contains("percentage")||isContain(str,"%"))
                    {
                        updateTypes("percent",val);
                        updateUnits("percent",val);
                    }
                }
            }
        }
        for(String s:measurements.keySet())
        {
            Spectrum spect=new Spectrum();
            spect.min=measurements.get(s).min;
            spect.max=measurements.get(s).max;
            spect.average=measurements.get(s).average;
            measurementSpectrum.put(s, spect);
        }
        for(String s:measurementTypes.keySet())
        {
            Spectrum spect=new Spectrum();
            spect.min=measurementTypes.get(s).min;
            spect.max=measurementTypes.get(s).max;
            spect.average=measurementTypes.get(s).average;
            measurementTypesSpectrum.put(s, spect);
        }
        for(String s:measurementContentTypes.keySet())
        {
            Spectrum spect=new Spectrum();
            spect.min=measurementContentTypes.get(s).min;
            spect.max=measurementContentTypes.get(s).max;
            spect.average=measurementContentTypes.get(s).average;
            measurementContentTypesSpectrum.put(s, spect);
        }
        writeJSON("measurementSpectrum");
        writeJSON("measurementTypesSpectrum");
        writeJSON("measurementContentTypesSpectrum");
    }
    
    public static MeasurementsJSON[] readJSON(String filePath)
    {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new File(filePath), MeasurementsJSON[].class);
        } catch (IOException ex) {
            Logger.getLogger(Part10measurements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void writeJSON(String fileName)
    {
        String filePath="E:\\Sem2\\CSCI599\\Assignment3\\Content_Evaluation\\outputs\\part10\\www.benchfly.com\\";
        if(fileName.equals("measurementSpectrum"))
        {
            try {
                mapper.writeValue(new File(filePath+fileName+".json"), measurementSpectrum);
            } catch (IOException ex) {
                Logger.getLogger(Part10measurements.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(filePath+fileName+".json created");
        }
        else if(fileName.equals("measurementTypesSpectrum"))
        {
            try {
                mapper.writeValue(new File(filePath+fileName+".json"), measurementTypesSpectrum);
            } catch (IOException ex) {
                Logger.getLogger(Part10measurements.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(filePath+fileName+".json created");
        }
        else if(fileName.equals("measurementContentTypesSpectrum"))
        {
            try {
                mapper.writeValue(new File(filePath+fileName+".json"), measurementContentTypesSpectrum);
            } catch (IOException ex) {
                Logger.getLogger(Part10measurements.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(filePath+fileName+".json created");
        }
    }
    
    private static boolean isContain(String source, String subItem)
    {
         String pattern = "\\b"+subItem+"\\b";
         Pattern p=Pattern.compile(pattern);
         Matcher m=p.matcher(source);
         return m.find();
    }
    static void updateUnits(String unit,double val)
    {
        if(!measurements.containsKey(unit))
        {
            Values v=new Values();
            v.min=val;
            v.max=val;
            v.average=val;
            v.total=1.0;
            v.sum=val;
            measurements.put(unit, v);
        }
        else
        {
            Values v=measurements.get(unit);
            v.total=v.total+1;
            if(v.min>val)
                v.min=val;
            if(v.max<val)
                v.max=val;
            v.sum=v.sum+val;
            v.total=v.total+1;
            v.average=v.sum/v.total;
            measurements.replace(unit, v);
        }
    }
    
    static void updateTypes(String type,double val)
    {
        if(!measurementTypes.containsKey(type))
        {
            Values v=new Values();
            v.min=val;
            v.max=val;
            v.average=val;
            v.total=1.0;
            v.sum=val;
            measurementTypes.put(type, v);
        }
        else
        {
            Values v=measurementTypes.get(type);
            v.total=v.total+1;
            if(v.min>val)
                v.min=val;
            if(v.max<val)
                v.max=val;
            v.sum=v.sum+val;
            v.total=v.total+1;
            v.average=v.sum/v.total;
            measurementTypes.replace(type, v);
        }
    }
    
    static void updateContentTypes(String contentType,double val)
    {
        if(!measurementContentTypes.containsKey(contentType))
        {
            Values v=new Values();
            v.min=val;
            v.max=val;
            v.average=val;
            v.total=1.0;
            v.sum=val;
            measurementContentTypes.put(contentType, v);
        }
        else
        {
            Values v=measurementContentTypes.get(contentType);
            v.total=v.total+1;
            if(v.min>val)
                v.min=val;
            if(v.max<val)
                v.max=val;
            v.sum=v.sum+val;
            v.total=v.total+1;
            v.average=v.sum/v.total;
            measurementContentTypes.replace(contentType, v);
        }
    }
}

class MeasurementsJSON
{
    @JsonProperty("id")
    String id;
    @JsonProperty("ContentType")
    String ContentType;
    @JsonProperty("FileSize")
    String FileSize;
    @JsonProperty("names")
    Set<String> names;
    @JsonProperty("units")
    Set<String> units;
}

class Values
{
    double min;
    double max;
    double total;
    double sum;
    double average;
}

class Spectrum
{
    double min;
    double max;
    double average;
}