package com.shortandprecise;

import com.shortandprecise.config.Config;

public class App {

    public static void main(String[] args) {
        
        if(!Config.getInstance().isConfigValid()) {
            System.out.println("Please check your configuration");
            System.exit(0);
        }
        
        Server server = new Server(Config.getInstance().configDTO.getPort());
        new Thread(server).start();
        
    }

}
