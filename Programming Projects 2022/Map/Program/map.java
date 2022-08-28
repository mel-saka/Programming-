import java.util.*;
import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * converts data into GeoJSON feature and writes to a GeoJSON file 
 * that contains a featurecollection
 * 
 * @author Mohammed El Saka
 */
class map {
    private static Map<String, ArrayList<String>> cleanData = new HashMap<String, ArrayList<String>>();
    private static ArrayList<ArrayList<String>> GeoJSON = new ArrayList<ArrayList<String>>();
    private static int Counter = 0;

    public static void main(String[] args) {
        String input = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter: ");
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            // String[] word = input.split("\\s+");
            DataCleaner(input);
            DataValidator();
        }
    }

    /**
     * Prefroms data cleansing and organises raw data for
     * processing
     * 
     * @param data input data by the user
     */
    public static void DataCleaner(String data) {

        boolean isLabel = false;
        String currCoor = "";
        String currentType = "";
        int indexOfType = 0;
        int typeCount = 0;
        int dataStrLength = 0;

        cleanData.put("data", new ArrayList<String>());
        cleanData.put("Type", new ArrayList<String>());
        cleanData.put("label", new ArrayList<String>());
       // String[] rawData1 = data.split("\\s+");
        //String spaces = Arrays.toString(rawData1);
        //String[] rawData2 = spaces.split("°");
       // String degrees = Arrays.toString(rawData2);
       // String[] rawData3 = degrees.split("°");
       // String hyph = Arrays.toString(rawData3);
       // String[] rawData = hyph.split("\"");
       String[] rawData = data.split("[°\"'\\s]");
       String hyph = Arrays.toString(rawData);;
       
        //String d = Arrays.toString(dataStr);
       // String h = Arrays.toString(dataStr);
        System.out.println(hyph);
       /* 
        int indexOfLabel = 0;
        String[] anotherData = new String[rawData.length - 1];
        for (int i = 0; i < rawData.length; i++) {
            if (ContainsLabel(rawData[i])) {
                isLabel = true;
                cleanData.get("label").add(rawData[i]);
                indexOfLabel = i;
                for (int j = 0; j < rawData.length; j++) {
                    if(j != indexOfLabel){
                    anotherData[j] = rawData[j];
            }
        }
        }
        }
        if(isLabel== true){
         dataStrLength = anotherData.length;
        }else{
         dataStrLength = rawData.length;
        }
        String[] dataStr = new String[dataStrLength];
        if(isLabel== true){
            System.arraycopy(anotherData, 0, dataStr, 0, anotherData.length);
           }else{
            System.arraycopy(rawData, 0, dataStr, 0, rawData.length);
           }
           */
          String[] dataStr = data.split("[°\"'\\s]");  
        int len = dataStr.length - 1;
        int start = 0;
        int middle = len/2;
        int  end = len;
        for (int i = 0; i < dataStr.length; i++) {

            if (ContainsLabel(dataStr[i])) {
                isLabel = true;
                cleanData.get("label").add(dataStr[i]);
            }

            if (ContainsCoordinates(dataStr[i])) {
                if (currCoor.equals("")) {
                    currCoor += dataStr[i];
                } else {
                    currCoor = currCoor + " " + dataStr[i];
                }

            }  else if (ContainsType(dataStr[i])) {
                if (currentType.equals("")) {
                    currentType += dataStr[i];
                } else {
                    currentType = currentType + " " + dataStr[i];
                }
                typeCount++;
                indexOfType = i;
            }

        }
        String coor = "";
        for (int j = 0; j < currCoor.length(); j++) {
            if (currCoor.charAt(j) != ',' && currCoor.charAt(j) != '°' && currCoor.charAt(j) != '\''
                    && currCoor.charAt(j) != '’' && currCoor.charAt(j) != '\"') {
                coor += currCoor.charAt(j);
            }
        }
        String type = "";
        for (int k = 0; k < currentType.length(); k++) {
            if (currentType.charAt(k) != ',') {
                type += currentType.charAt(k);
            }
        }

        String firstHalf = "";
        String rest = "";
        String[] coordinates = coor.split("\\s+");
        for (int i = 0; i < coordinates.length; i++) {
            if (i < coordinates.length / 2) {
                if (firstHalf.equals("")) {
                    firstHalf += coordinates[i];
                } else {
                    firstHalf = firstHalf + " " + coordinates[i];
                }
            } else {
                if (rest.equals("")) {
                    rest += coordinates[i];
                } else {
                    rest = rest + " " + coordinates[i];
                }
            }
        }
        // removing non digits

        cleanData.get("data").add(firstHalf);
        cleanData.get("data").add(rest);
        if (typeCount == 0) {
            cleanData.get("Type").add("");
            cleanData.get("Type").add("");

        } else if (typeCount == 1) {
            if (start == indexOfType || middle == indexOfType) {
                cleanData.get("Type").add(type);
                cleanData.get("Type").add("");
            } else if (end == indexOfType) {
                cleanData.get("Type").add("");
                cleanData.get("Type").add(type);
            }
        } else if (typeCount == 2) {
            String[] types = type.split("\\s+");
            cleanData.get("Type").add(types[0]);
            cleanData.get("Type").add(types[1]);

        }
    }
/**
     *Checks clean data for validity 
     * 
     */
    public static void DataValidator() {
        double coordinate = 0.0;
        String type = "";
        String label = "";
        boolean latitudeFound = false;
        boolean longFound = false;
        String latitudeType = "";
        String longitudeType = "";
        String latitude = "";
        String longitude = "";
        String dataStr;
         
        if (cleanData.get("data").size() == cleanData.get("Type").size()) {
            for (int i = 0; i < cleanData.get("data").size(); i++) {
                String[] coorData = cleanData.get("data").get(i).split("\\s+");
                for (int a = 0; a < coorData.length; a++) {

                    if (cleanData.get("label").size() != 0) {
                        label = cleanData.get("label").get(0);
                    }
                    if (ContainsCoordinates(coorData[a]) == false){
                        System.out.println("Ha! I tricked you");
                        return;
                    }
                    coordinate = Double.valueOf(coorData[a]);

                    type = cleanData.get("Type").get(i);

                    // check first index first then terminate if false
                    if (a == 0 && !type.equals("label")) {
                        if (type.equals("N") && coordinate > 0.0 && coordinate < 90.000000) {
                            latitudeFound = true;
                            latitude = cleanData.get("data").get(i);
                            latitudeType = type;
                        } else if (type.equals("S") && coordinate > 0.0 && coordinate < 90.000000) {
                            latitudeFound = true;
                            latitude = cleanData.get("data").get(i);
                            latitudeType = type;
                        } else if (type.equals("E") && coordinate > 0.0 && coordinate < 180.000000) {
                            longFound = true;
                            longitude = cleanData.get("data").get(i);
                            longitudeType = type;
                        } else if (type.equals("W") && coordinate > 0.0 && coordinate < 180.000000) {
                            longFound = true;
                            longitude = cleanData.get("data").get(i);
                            longitudeType = type;
                        } else if (type.equals("") && coordinate > -90.000000 && coordinate < 90.000000) {
                            latitudeFound = true;
                            latitude = cleanData.get("data").get(i);
                            latitudeType = type;
                        } else if (type.equals("") && coordinate > -180.000000 && coordinate < 180.000000) {
                            longFound = true;
                            longitude = cleanData.get("data").get(i);
                            longitudeType = type;
                        }
                    }
            
                     
                    dataStr = "";
                    type = "";
                }

            }
            
            if (latitudeFound == true && longFound == true) {
                Converter(latitude, latitudeType, longitude, longitudeType);

            } else {
                System.out.println("Ha! tricked you");
            }
        } else {
           
        }

    }
    /**
     *converts valid data to GeoJson form 
     * @param latidue value for latitdue
     * @param latidueType The direction of Latidude
     * @param longitude value for longitude
     * @param longitudeType The direction of longitude
     */
    public static void Converter(String latitude, String latitudeType, String longitude, String longitudeType){
        String[] latitudes = latitude.split("\\s+");
        String[] longitudes = longitude.split("\\s+");
           double degrees = 200.00;
           double minutes = 200.00;
           double seconds = 200.00;
           double feature = 200.00;
        if(longitudes.length == 3){
            degrees = Double.valueOf(longitudes[0]);
            minutes = Double.valueOf(longitudes[1])/60;
            seconds = Double.valueOf(longitudes[2])/3600;
            feature = degrees + minutes + seconds;
         }else if(longitudes.length == 2){
            degrees = Double.valueOf(longitudes[0]);
            minutes = Double.valueOf(longitudes[1])/60;
            feature = degrees + minutes;
         }else if(longitudes.length == 1){
            feature = Double.valueOf(longitudes[0]);
         }
         if(longitudeType.equals("W")){
             feature = feature*(-1);
         }   
        GeoJSON.add(new ArrayList<String>());
        GeoJSON.get(Counter).add(0, String.valueOf(feature));
        
        if(latitudes.length == 3){
            degrees = Double.valueOf(latitudes[0]);
            minutes = Double.valueOf(latitudes[1])/60;
            seconds = Double.valueOf(latitudes[2])/3600;
            feature = degrees + minutes + seconds;
         }else if(latitudes.length == 2){
            degrees = Double.valueOf(latitudes[0]);
            minutes = Double.valueOf(latitudes[1])/60;
            feature = degrees + minutes;
         }else if(latitudes.length == 1){
            feature = Double.valueOf(latitudes[0]);
         }
         if(latitudeType.equals("S")){
             feature = feature*(-1);
         }
         GeoJSON.get(Counter).add(1, String.valueOf(feature));
 
         if(cleanData.get("label").size() != 0){
            GeoJSON.get(Counter).add(2, cleanData.get("label").get(0));
         }
         Counter++;
        System.out.println(GeoJSON.get(0).get(0) + ", " + GeoJSON.get(0).get(1)); 
        To_GeoJSON_File();
    }

   /**
     *Reads converterd features into a GeoJSON file
     */    
    public static void To_GeoJSON_File(){
        	// Create file
		Path location = Paths.get("map.geojson");

    String output = "";
    output += "{ \n \"type\": \"FeatureCollection\", \n \"features\" : [";
    
    for (int i = 0; i < GeoJSON.size(); i++) {
    output += "{ \n \"type\": \"Feature\", \n \"properties\": {"; 
    
    if(GeoJSON.get(i).size()== 3){
    output += "\"Label\": \"" + GeoJSON.get(i).get(2) + "\" \n },"; 
    }else{
        output += "},";   
    }
    
    output += "\n \"geometry\": { \n \"type\": \"Point\", \n \"coordinates\": [ \n" +  GeoJSON.get(i).get(0) + ", \n" + GeoJSON.get(i).get(1) + "\n ]   } \n }";
   if(i != GeoJSON.size()-1){
     output += ",";
   }
    }
    //end
    output +=  " \n ] \n } "; 
    
    try {
        Files.writeString(location, output, StandardCharsets.UTF_8);
    }
    catch (IOException ex) {
        System.out.print("error! Check file Path");
    }
    } 

    /**
     * checks if an arguments contains only Coordinates
     * 
     * @param s data input
     */
    public static Boolean ContainsCoordinates(String s) {
        if(s.equals("")){
            return false;
        }
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            if (i < s.length() - 1) {
                str = s.substring(i, i + 1);
            } else {
                str = s.substring(i);
            }
            if (str.matches("[^0-9]+") && !str.equals(".") && !str.equals("-") && !str.equals("+") && !str.equals("°")
                    && !str.equals("'") && !str.equals(",") && !str.equals("''") && !str.equals("’")
                    && !str.equals("’’") && !str.equals("\"")) {
                return false;
            }
        }
        return true;

    }
   /**
     * checks if an arguments contains labels
     * 
     * @param s data input
     */
    public static Boolean ContainsLabel(String s) {
        String d = "";
        String ch = "";
        s = s.toLowerCase();
        String str = "";
        for (int j = 0; j < s.length(); j++) {
            if (j < s.length() - 1) {
                ch = s.substring(j, j + 1);
            } else {
                ch = s.substring(j);
            }
            if (!ch.equals("n") && !ch.equals("s") && !ch.equals("e") && !ch.equals("w") && !ch.equals(",")) {
                d += ch;
            }
        }
        if (d.equals("")) {
            return false;
        }
        for (int i = 0; i < d.length(); i++) {
            if (i < d.length() - 1) {
                str = d.substring(i, i + 1);
            } else {
                str = d.substring(i);
            }
            if (str.matches("[^a-z A-z]+") && !str.equals("")) {
                return false;
            }
        }
        return true;

    }
/**
     * checks if an arguments contains Type
     * 
     * @param s data input
     */
    public static Boolean ContainsType(String s) {
        String ch = "";
        String d = "";
        String str = "";
        for (int j = 0; j < s.length(); j++) {
            if (j < s.length() - 1) {
                ch = s.substring(j, j + 1);
            } else {
                ch = s.substring(j);
            }
            if (!ch.equals(",")) {
                d += ch;
            }
        }
        if (d.equals("") || d.equals("")) {
            return false;
        }
        for (int i = 0; i < d.length(); i++) {
            if (i < d.length() - 1) {
                str = d.substring(i, i + 1);
            } else {
                str = d.substring(i);
            }
            if (!str.equals("N") && !str.equals("S") && !str.equals("E") && !str.equals("W")) {
                return false;
            }
        }
        return true;
    }
}
