package com.example;

public class JobRequest {
    private String name;
    private String group;
    private int intervalInSeconds;
    private String type;
    private int delayInSeconds;

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getIntervalInSeconds() {
        return intervalInSeconds;
    }

    public String getType() {
        return type;
    }

    public int getDelayInSeconds() {
        return delayInSeconds;
    }
}
