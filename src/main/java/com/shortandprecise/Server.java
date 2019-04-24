/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shortandprecise;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                new Thread(() -> {
                    InputStream inputStream;
                    OutputStream outputStream;

                    try {
                        inputStream = socket.getInputStream();
                        outputStream = socket.getOutputStream();
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

                                String firstLine = request[FIRST_LINE_POSITION];
                                String[] firstLineArray = firstLine.split(" ");

                                String uri = firstLineArray[URI_POSITION];
                                if(uri.equalsIgnoreCase("/")) {
                                    uri = uri + "index.html";
                                }
                                System.out.println("URL: " + uri);
                                Path path = Paths.get("html" + File.separator + uri.substring(1));
                                StringBuilder response = new StringBuilder();
                                byte[] data;
                                if(path.toFile().isFile()) {
                                    data = Files.readAllBytes(path);
                                    String mimeType = new Tika().detect(path);
                                    response.append("HTTP/1.1 200 Ok\r\n")
                                            .append("Content-Type: ").append(mimeType).append("\r\n")
                                            .append("Connection: Closed\r\n")
                                            .append("Content-Length: ").append(data.length).append("\r\n\r\n");
                                } else {
                                    data = "Not found".getBytes();
                                    response.append("HTTP/1.1 404 Not Found\r\n")
                                            .append("Content-Type: text/html\r\n")
                                            .append("Connection: Closed\r\n")
                                            .append("Content-Length: ").append(data.length).append("\r\n\r\n");
                                }

                                outputStream.write(response.toString().getBytes());
                                outputStream.write(data);
                                break;
                            }
                        }

                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
            
        } catch (IOException ex) {
        }
        
    }
    
}
