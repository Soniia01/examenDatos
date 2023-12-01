package common;

import jakarta.inject.Singleton;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Singleton
@Log4j2
@Data
public class Configuration {
    private static Configuration instance;
    private Properties pxml;
    private String pathDB;
    private String userDB;
    private String passDB;
    private String driver;
    private String pathXml;

    private Configuration(){
        try {
            pxml = new Properties();
            pxml.loadFromXML(Configuration.class.getClassLoader().getResourceAsStream("config/properties.xml"));
            this.pathDB = pxml.getProperty("path");
            this.userDB = pxml.getProperty("user");
            this.passDB = pxml.getProperty("pass");
            this.driver = pxml.getProperty("driver");
            this.pathXml = pxml.getProperty("xmlPath");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    public static Configuration getInstance() {
        if (instance == null){
            instance = new Configuration();
        }
        return instance;
    }
}
