package com.shortandprecise.config;

public class ConfigDTO {

    private int port;
    private String host;
    private int requestProcessor;

    public ConfigDTO() {
        requestProcessor = 2;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getRequestProcessor() {
        return requestProcessor;
    }

    public void setRequestProcessor(int requestProcessor) {
        this.requestProcessor = requestProcessor;
    }
}
