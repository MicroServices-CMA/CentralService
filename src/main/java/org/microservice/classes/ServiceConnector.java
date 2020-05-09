package org.microservice.classes;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.microservice.Main;
import org.microservice.models.Answer;
import org.microservice.utils.PropertyManager;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServiceConnector {

    int port;
    String pathSpec;

    public ServiceConnector(int port, String pathSpec) {
        this.port = port;
        this.pathSpec = pathSpec;
    }

    public Answer getRequest(String id) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:" + port + pathSpec + "?clientId=" + id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine status = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            if (status.getStatusCode() == HttpServletResponse.SC_FOUND) {
                Pair<Integer, String> creditServerResp = callCreditServer(id);
                if (creditServerResp.t.equals(HttpServletResponse.SC_FOUND)) {
                    return new Answer("CREDIT_FOUND", "{ 'client': " + result +
                            ",  'history': " + creditServerResp.u + " }");
                } else {
                    return new Answer("NO_CREDIT_FOUND", creditServerResp.u); // TODO: 08.05.2020 And here
                }
                /*HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    Answer<T> answer = Common.getPrettyGson().fromJson(result, new TypeToken<Answer<T>>() {
                    }.getType());
                    return answer;*/
            } else if (status.getStatusCode() == HttpServletResponse.SC_BAD_REQUEST){
                return new Answer("BAD_REQUEST", result);
            } else if (status.getStatusCode() == HttpServletResponse.SC_NO_CONTENT){
                return new Answer("NO_CONTENT", result);
            } else if (status.getStatusCode() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR){
                return new Answer("INTERNAL_SERVER_ERROR", result);
            }
        } catch (Exception e) {
            Main.getLog().error("Error in Central Server. Description: " + e.getMessage());
            return new Answer("INTERNAL_SERVER_ERROR", "An internal Error occurred in Central Server");
        }


    }

    private Pair<Integer, String> callCreditServer(String id) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            int creditHistoryPort = PropertyManager.getPropertyAsInteger("creditHistoryService.port", 8500);
            String clientsServicePath = PropertyManager.getPropertyAsString("creditHistoryService.path", "/creditHistory");

            HttpGet request = new HttpGet("http://localhost:" + creditHistoryPort + clientsServicePath + "?id=" + id);

            CloseableHttpResponse response = httpClient.execute(request);
            StatusLine status = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return new Pair<>(status.getStatusCode(), result);
        } catch (Exception e) {
            Main.getLog().error("Error in Central Server. Description: " + e.getMessage());
            return new Pair<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Internal Error in Central Server");
        }
    }

}
