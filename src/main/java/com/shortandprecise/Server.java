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

/**
 *
 * @author rejuan
 */
public class Server implements Runnable {

    private final int port;
    private static final int FIRST_LINE_POSITION = 0;
    private static final int METHOD_POSITION = 0;
    private static final int URI_POSITION = 1;
    private static final int HTTP_VERSION_POSITION = 2;
    
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
                        InputStream inputStream;
                        OutputStream outputStream;
                        
                        try {
                            inputStream = socket.getInputStream();
                            byte[] buffer = new byte[1024];
                            StringBuilder stringBuilder = new StringBuilder();
                            while (true) {                                
                                int len = inputStream.read(buffer);
                                if(len > 0) {
                                    stringBuilder
                                            .append(new String(buffer, 0, len));
                                }
                                
                                int position = stringBuilder.indexOf("\r\n\r\n");
                                if(position > 0) {
                                    String[] request = stringBuilder
                                            .substring(0, position)
                                            .split("\r\n");
                                    
                                    // TODO need to check request length
                                    String firstLine = request[FIRST_LINE_POSITION];
                                    String[] firstLineArray = firstLine.split(" ");
                                    
                                    // TODO need to check firstline validity
                                    String uri = firstLineArray[URI_POSITION];
                                    System.out.println(uri);
                                }
                                
                            }
                        } catch (IOException ex) {
                            
                        }
                    }
                }).start();
            }
            
        } catch (IOException ex) {
        }
        
    }
    
}
