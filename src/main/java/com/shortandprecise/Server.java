/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortandprecise;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rejuan
 */
public class Server implements Runnable {

    private final int port;
    
    public Server(int port) {
        this.port = port;
    }
    
    @Override
    public void run() {
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {                
                Socket socket = serverSocket.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream();
                        } catch (IOException ex) {
                            
                        }
                    }
                }).start();
            }
            
        } catch (IOException ex) {
        }
        
    }
    
}
