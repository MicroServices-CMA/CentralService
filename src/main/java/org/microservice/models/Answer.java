package org.microservice.models;

import org.microservice.classes.usersData.Client;
import org.microservice.classes.creditHistory.History;

public class Answer
{
    private String status;
    private Client client;
    private History history;

    public Answer(String status, Client client, History history) {
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

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
