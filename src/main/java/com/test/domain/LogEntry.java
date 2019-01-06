package com.test.domain;

public class LogEntry {

    public enum State {
        STARTED,
        FINISHED
    }

    private String id;
    private State state;
    private long timestamp;

    //optional attributes
    private String type;
    private String host;

    public String getId() {
        return id;
    }

    public LogEntry setId(String id) {
        this.id = id;
        return this;
    }

    public State getState() {
        return state;
    }

    public LogEntry setState(State state) {
        this.state = state;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public LogEntry setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getType() {
        return type;
    }

    public LogEntry setType(String type) {
        this.type = type;
        return this;
    }

    public String getHost() {
        return host;
    }

    public LogEntry setHost(String host) {
        this.host = host;
        return this;
    }

}
