package org.microservice.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.microservice.Main;
import org.microservice.models.AnswerCentralServer;
import org.microservice.models.AnswerClientsServer;
import org.microservice.utils.Common;
import org.microservice.utils.PropertyManager;
import org.microservice.models.AnswerCreditHistory;


import java.io.IOException;

public class ServiceConnector {

/*    int port;
    String pathSpec;

    public ServiceConnector(int port, String pathSpec) {
        this.port = port;
        this.pathSpec = pathSpec;
    }

    public AnswerCentralServer getRequest(String id) throws IOException {

        HttpGet request = new HttpGet("http://localhost:" + port + pathSpec + "?clientId=" + id);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            AnswerCentralServer answerCentralServer = new AnswerCentralServer();
            Gson g = new Gson();
            StatusLine status = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            //AnswerClientsServer answerClientsServer = g.fromJson(EntityUtils.toString(entity), AnswerClientsServer.class);
            AnswerClientsServer answerClientsServer = Common.getPrettyGson().fromJson(EntityUtils.toString(entity),
                    new TypeToken<AnswerClientsServer>() {}.getType());
            if (answerClientsServer.getStatus().equals("CLIENT_FOUND")) {
                answerCentralServer.setClient(answerClientsServer.getClient());
                AnswerCreditHistory answerCreditHistory = doGet2CreditServer(id);
                if (answerCreditHistory.getStatus().equals("HISTORY_FOUND") ||
                        answerCreditHistory.getStatus().equals("HISTORY_NOT_FOUND")) {
                    answerCentralServer.setHistory(answerCreditHistory.getItems());
                    answerCentralServer.setStatus("OK");
                } else {
                    answerCentralServer.setStatus(answerCreditHistory.getStatus());
                }
            } else {
                answerCentralServer.setStatus(answerClientsServer.getStatus());
            }
            return answerCentralServer;
        } catch (Exception e) {
            Main.getLog().error("Error in Central Server. Description: " + e.getMessage());
            return new AnswerCentralServer("INTERNAL_CENTRAL_SERVER_ERROR", null, null);
        }
    }*/


}
