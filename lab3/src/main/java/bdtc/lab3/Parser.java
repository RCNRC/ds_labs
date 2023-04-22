package bdtc.lab3;

import java.util.Objects;

/**
 * Parser class
 */
public class Parser {
    /**
     * Parser function, returns ready to export to logstash string
     *
     * @param message should be match json format
     */
    public static String parseString(String message){
        String []fields = message.split(",");
        if(Objects.equals(fields[fields.length - 1].split(":")[0], "\"priceUsd\"")) {
            String direction = fields[3].split(":")[1].split("\"")[1];
            Integer priceUsd = Double.valueOf(fields[7].split(":")[1].split("}")[0]).intValue();
            String priceUsdPowString = String.valueOf((String.valueOf(priceUsd).length()));
            return "{\"target\":\"" + direction + "_" + priceUsdPowString + "\"," + fields[6] + "}";
        } else {
            return "";
        }
    }
}
