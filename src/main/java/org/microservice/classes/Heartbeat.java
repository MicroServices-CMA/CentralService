package org.microservice.classes;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

public class Heartbeat {
    private static Logger logHeartbeat = LoggerFactory.getLogger(Heartbeat.class.getSimpleName());

    public boolean isAlive(int port, String pathSpec) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:" + port + pathSpec);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine status = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return status.getStatusCode() == HttpServletResponse.SC_OK;
        } catch (Exception e) {
            logHeartbeat.warn("Warning: ", e);
        }
        return false;
    }
}
