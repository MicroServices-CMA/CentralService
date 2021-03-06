package org.microservice.models;

import org.microservice.structures.usersData.Client;

/**
 * This is the clientsMicroService standard answer format.
 *
 * @author Ханк
 * @version 1.0
 */
public class AnswerClientsServer {

    private String status;
    private String details;
    private Client client;


    public AnswerClientsServer() {
    }

    public AnswerClientsServer(String status, String details, Client client) {
        this.status = status;
        this.details = details;
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
