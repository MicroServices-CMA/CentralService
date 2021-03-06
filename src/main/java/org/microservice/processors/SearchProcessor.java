package org.microservice.processors;

import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.microservice.models.AnswerCentralServer;
import org.microservice.models.AnswerClientsServer;
import org.microservice.models.AnswerCreditHistory;
import org.microservice.utils.Common;
import org.microservice.utils.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.microservice.handlers.CentralServlet.logCentralServlet;


/**
 * Contains different predefined methods used to search data by sending requests to other servers;
 *
 * @author Ханк
 * @version 1.0
 */
public interface SearchProcessor {

    default AnswerCentralServer searchData(HttpServletRequest request) {

        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String passportSerial = request.getParameter("passportSerial");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        try {
            AnswerCentralServer answerCentralServer = new AnswerCentralServer();
            if (name != null && surname != null && passportSerial != null && email != null && country != null) {
                AnswerClientsServer answerClientsServer = searchClient(surname, name, passportSerial, email, country);
                if (answerClientsServer.getStatus().equals("OK")) {
                    answerCentralServer.setClient(answerClientsServer.getClient());
                    AnswerCreditHistory answerCreditHistory = searchCreditHistory(answerClientsServer.getClient().getClientId());
                    answerCentralServer.setHistory(answerCreditHistory.getItems());
                    answerCentralServer.setStatus(answerCreditHistory.getStatus());
                } else {
                    answerCentralServer.setStatus(answerClientsServer.getStatus());
                }
                return answerCentralServer;
            } else {
                logCentralServlet.error("Error: No name, surname, passportSerial or email provided");
                return new AnswerCentralServer("BAD_CENTRAL_REQUEST", null, null);
            }
        } catch (Exception e) {
            logCentralServlet.error("An error occurred within Central Server. Error: " + e.getMessage() +
                    "\nProvided parameters:\n" +
                    "surname: " + surname + "\n" +
                    "name: " + name + "\n" +
                    "passportSerial: " + passportSerial + "\n" +
                    "email: " + email + "\n" +
                    "country: " + country);
            return new AnswerCentralServer("INTERNAL_CENTRAL_SERVER_ERROR", null, null);
        }
    }

    default AnswerClientsServer searchClient(String surname, String name, String passportSerial, String email,
                                             String country) {

        int port = PropertyManager.getPropertyAsInteger("clientsService.port", 7000);
        String pathSpec = PropertyManager.getPropertyAsString("clientsService.path", "/userData");

        HttpGet request = new HttpGet("http://localhost:" + port + pathSpec + "?surname=" + surname +
                "&name=" + name + "&passportSerial=" + passportSerial + "&email=" + email + "&country=" + country);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse resp = httpClient.execute(request)) {
            return Common.getPrettyGson().fromJson(EntityUtils.toString(resp.getEntity()),
                    new TypeToken<AnswerClientsServer>() {
                    }.getType());
        } catch (Exception e) {
            logCentralServlet.error("Error in Central Server. Description: " + e.getMessage());
            return new AnswerClientsServer("INTERNAL_CENTRAL_SERVER_ERROR", "An internal Error occurred" +
                    " within Central Server while searching for a Client.", null);
        }
    }

    default AnswerCreditHistory searchCreditHistory(String id) throws IOException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            int port = PropertyManager.getPropertyAsInteger("creditHistoryService.port", 8500);
            String pathSpec = PropertyManager.getPropertyAsString("creditHistoryService.path", "/creditHistory");

            HttpGet request = new HttpGet("http://localhost:" + port + pathSpec + "?id=" + id);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            return Common.getPrettyGson().fromJson(EntityUtils.toString(entity), AnswerCreditHistory.class);
        } catch (Exception e) {
            logCentralServlet.error("Error in Central Server. Description: " + e.getMessage());
            return new AnswerCreditHistory("INTERNAL_CENTRAL_SERVER_ERROR", "An internal Error occurred" +
                    " within Central Server while searching for the Credit History.", null);
        }
    }
}
