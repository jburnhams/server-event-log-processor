package com.test.domain;

public class ServerEvent {

    private String id;
    private long duration;
    private boolean alert;

    //optional attributes
    private String type;
    private String host;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "ServerEvent{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", alert=" + alert +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
