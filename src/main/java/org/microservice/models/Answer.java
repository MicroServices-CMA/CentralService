package org.microservice.models;

import org.microservice.classes.Pair;

public class Answer
{
    private String status;
    private String Data;

    public Answer(String status, String data) {
        this.status = status;
        Data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
