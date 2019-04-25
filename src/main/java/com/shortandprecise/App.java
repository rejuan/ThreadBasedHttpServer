package com.shortandprecise;

import com.shortandprecise.config.Config;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class App {

    public static final LinkedBlockingQueue<Socket> QUEUE
            = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        
        if(!Config.getInstance().isConfigValid()) {
            System.out.println("Please check your configuration");
            System.exit(0);
        }

        RequestAcceptor requestAcceptor = new RequestAcceptor(Config.getInstance().configDTO.getPort());
        new Thread(requestAcceptor).start();

        int requestProcessorNumber = Config.getInstance().configDTO.getRequestProcessor();
        for (int i = 1; i < requestProcessorNumber; i++) {
            RequestProcessor requestProcessor = new RequestProcessor();
            new Thread(requestProcessor).start();
        }
        
    }

}
