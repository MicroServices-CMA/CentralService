package org.microservice.handlers;

import org.microservice.Main; // TODO: 08.05.2020 create log file for GET requests and delete this import + Getters and Setters in Main class
import org.microservice.classes.ServiceConnector;
import org.microservice.models.Answer2Central;
import org.microservice.utils.Common;
import org.microservice.utils.PropertyManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CentralServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        if (request != null) {
            ServiceConnector connector;
            String clientId = "";
            clientId = request.getParameter("id");
            if (clientId.equals("")) {
                Main.getLog().error("Error: id not provided.");

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println(Common.getPrettyGson().toJson(
                        new Answer2Central("EMPTY_CENTRAL_REQUEST", null, null)));
                return;
            }
            try {
                int clientsServicePort = PropertyManager.getPropertyAsInteger("clientsService.port", 7000);
                String clientsServicePath = PropertyManager.getPropertyAsString("clientsService.path", "/userData");
                connector = new ServiceConnector(clientsServicePort, clientsServicePath);

                Answer2Central answer2Central = connector.getRequest(clientId);
                switch (answer2Central.getStatus()) {
                    case "OK":
                        response.setStatus(HttpServletResponse.SC_OK);
                        break;
                    case "CLIENT_NOT_FOUND":
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        break;
                    case "INTERNAL_CENTRAL_SERVER_ERROR":
                    case "INTERNAL_USERS_SERVER_ERROR":
                    case "INTERNAL_HISTORY_SERVER_ERROR":
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        break;
                }

                response.getWriter().println(Common.getPrettyGson().toJson(answer2Central));
            } catch (Exception e) {
                Main.getLog().error("Internal error while proceeding id: " + clientId + ", Error: " + e.getMessage());
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println(Common.getPrettyGson().toJson(
                        new Answer2Central("INTERNAL_SERVER_ERROR", null, null)));
            }
        } else {
            Main.getLog().error("Empty request.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(Common.getPrettyGson().toJson(
                    new Answer2Central("EMPTY_CENTRAL_REQUEST", null, null)));
        }
    }
}
