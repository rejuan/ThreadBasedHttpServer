/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortandprecise;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author rejuan
 */
public class RequestAcceptor implements Runnable {

    private final int port;

    
    public RequestAcceptor(int port) {
        this.port = port;
    }
    
    @Override
    public void run() {
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    App.QUEUE.put(socket);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
        }
        
    }
    
}
