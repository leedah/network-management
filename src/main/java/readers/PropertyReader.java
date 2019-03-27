package main.java.readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sofia on 7/28/15.
 */
public class PropertyReader {

    public String[] getPropertyValues() throws IOException {
        Properties prop = new Properties();
        String propFileName = "../application.properties";

        InputStream inputStream = this.getClass().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file [" + propFileName + "] not found in the classpath");
        }

        String[] result = new String[9];
        result[0] = prop.getProperty("base_stationPath");
        result[1] = prop.getProperty("batteryPath");
        result[2] = prop.getProperty("gpsPath");
        result[3] = prop.getProperty("wifiPath");
        result[4] = prop.getProperty("sql_ip");
        result[5] = prop.getProperty("sql_port");
        result[6] = prop.getProperty("sql_user");
        result[7] = prop.getProperty("sql_password");
        result[8] = prop.getProperty("sql_script");


        return result;
    }
}
