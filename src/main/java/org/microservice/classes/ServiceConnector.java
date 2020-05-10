package org.microservice.classes;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.microservice.Main;
import org.microservice.models.Answer2Central;
import org.microservice.models.AnswerClientsServer;
import org.microservice.utils.PropertyManager;
import org.microservice.models.AnswerCreditHistory;


import java.io.IOException;

public class ServiceConnector {

    int port;
    String pathSpec;

    public ServiceConnector(int port, String pathSpec) {
        this.port = port;
        this.pathSpec = pathSpec;
    }

    public Answer2Central getRequest(String id) throws IOException {

        HttpGet request = new HttpGet("http://localhost:" + port + pathSpec + "?clientId=" + id);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            Answer2Central answer2Central = new Answer2Central();
            Gson g = new Gson();
            StatusLine status = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            AnswerClientsServer answerClientsServer = g.fromJson(EntityUtils.toString(entity), AnswerClientsServer.class);
            if (answerClientsServer.getStatus().equals("CLIENT_FOUND")) {
                answer2Central.setClient(answerClientsServer.getClient());
                AnswerCreditHistory answerCreditHistory = doGet2CreditServer(id);
                if (answerCreditHistory.getStatus().equals("HISTORY_FOUND") ||
                        answerCreditHistory.getStatus().equals("HISTORY_NOT_FOUND")) {
                    answer2Central.setHistory(answerCreditHistory.getItems());
                    answer2Central.setStatus("OK");
                } else {
                    answer2Central.setStatus(answerCreditHistory.getStatus());
                }
            } else {
                answer2Central.setStatus(answerClientsServer.getStatus());
            }
            return answer2Central;
        } catch (Exception e) {
            Main.getLog().error("Error in Central Server. Description: " + e.getMessage());
            return new Answer2Central("INTERNAL_CENTRAL_SERVER_ERROR", null, null);
        }
    }

    private AnswerCreditHistory doGet2CreditServer(String id) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            int creditHistoryPort = PropertyManager.getPropertyAsInteger("creditHistoryService.port", 8500);
            String clientsServicePath = PropertyManager.getPropertyAsString("creditHistoryService.path", "/creditHistory");

            HttpGet request = new HttpGet("http://localhost:" + creditHistoryPort + clientsServicePath + "?id=" + id);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            Gson g = new Gson();
            return g.fromJson(EntityUtils.toString(entity), AnswerCreditHistory.class);
        } catch (Exception e) {
            Main.getLog().error("Error in Central Server. Description: " + e.getMessage());
            return new AnswerCreditHistory("INTERNAL_CENTRAL_SERVER_ERROR", null, null);
        }
    }
}
