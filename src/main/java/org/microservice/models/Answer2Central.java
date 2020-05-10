package org.microservice.models;

import org.microservice.classes.usersData.Client;
import org.microservice.classes.creditHistory.History;

import java.util.List;

public class Answer2Central
{
    private String status;
    private Client client;
    private List<History> history;

    public Answer2Central() {
    }

    public Answer2Central(String status, Client client, List<History> history) {
        this.status = status;
        this.client = client;
        this.history = history;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }
}
