package org.microservice.classes;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.microservice.models.Answer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Heartbeat {

    public boolean checkedBeat(int port, String pathSpec) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:" + port + pathSpec);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine status = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            if (status.getStatusCode() == HttpServletResponse.SC_OK) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
