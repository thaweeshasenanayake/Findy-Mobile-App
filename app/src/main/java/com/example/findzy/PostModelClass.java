package com.example.findzy;

public class PostModelClass {

    private String serviceName ;
    private String location;
    private String category;

    public PostModelClass(String serviceName, String location, String category) {
        this.serviceName = serviceName;
        this.location = location;
        this.category = category;

    }

    public String getServiceName() {
        return serviceName;
    }
    public String getLocation() {
        return location;
    }
    public String getCategory() {
        return category;
    }
}
