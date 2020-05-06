package org.microservice.central;

import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.microservice.model.Answer;
import org.microservice.model.History;
import org.microservice.utils.Common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChiConnector<T> {

    int port;
    String pathSpec;
    public ChiConnector(int port, String pathSpec) {
        this.port = port;
        this.pathSpec = pathSpec;
    }

    public Answer<T> getRequest(int id) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("http://localhost:"+port+"/"+pathSpec+"?id="+id);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                StatusLine status = response.getStatusLine();
                if(status.getStatusCode()== HttpServletResponse.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        String result = EntityUtils.toString(entity);
                        Answer<T> answer = Common.getPrettyGson().fromJson(result, new TypeToken<Answer<T>>() {
                        }.getType());
                        return answer;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
