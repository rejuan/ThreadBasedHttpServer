package com.shortandprecise.config;

import java.io.File;
import java.io.FileInputStream;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class Config {

    private static Config instance;
    public ConfigDTO configDTO;

    public static Config getInstance() {
        if(instance == null) {
            instance = new Config();
            instance.loadConfig();
        }

        return instance;
    }

    private ConfigDTO loadConfig() {
        try {
            Yaml yaml = new Yaml(new Constructor(ConfigDTO.class));
            File file = new File("config.yaml");
            configDTO = yaml.load(new FileInputStream(file));
        } catch (Exception e) {
        }
        
        return configDTO;
    }

    public boolean isConfigValid() {
        
        if(configDTO != null) {
            if(configDTO.getPort() <= 0) {
                return false;
            }
            
            if((configDTO.getHost() == null) || (configDTO.getHost().length() < 1)) {
                return false;
            }
        }
        
        return true;
    }
    
}
