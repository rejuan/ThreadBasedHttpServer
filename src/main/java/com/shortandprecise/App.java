package com.shortandprecise;

import com.shortandprecise.config.Config;

public class App {

    public static void main(String[] args) {
        
        if(!Config.getInstance().isConfigAlright()) {
            System.out.println("Please check your configuration");
            System.exit(0);
        }
        
        
    }

}
