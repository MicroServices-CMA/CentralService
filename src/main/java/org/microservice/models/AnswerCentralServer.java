package org.microservice.models;

import org.microservice.structures.usersData.Client;
import org.microservice.structures.creditHistory.History;

import java.util.List;

/**
 * This is the server standard answer format.
 *
 * @author Ханк
 * @version 1.0
 */
public class AnswerCentralServer
{
    private String status;
    private Client client;
    private List<History> history;


    public AnswerCentralServer() {
    }

    public AnswerCentralServer(String status, Client client, List<History> history) {
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
