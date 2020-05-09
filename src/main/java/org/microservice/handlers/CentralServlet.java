package org.microservice.handlers;

import org.microservice.Main; // TODO: 08.05.2020 create log file for GET requests and delete this import + Getters and Setters in Main class
import org.microservice.classes.ServiceConnector;
import org.microservice.models.Answer;
import org.microservice.utils.Common;
import org.microservice.utils.PropertyManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CentralServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request != null) {
            ServiceConnector connector;
            String clientId = request.getParameter("id");
            if (clientId == null) {
                Main.getLog().error("Error on clientId, value not provided.");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println(Common.getPrettyGson().toJson(new Answer("ERROR", null)));
                return;
            }
            try {
                int clientsServicePort = PropertyManager.getPropertyAsInteger("clientsService.port", 7000);
                String clientsServicePath = PropertyManager.getPropertyAsString("clientsService.path", "/userData");
                connector = new ServiceConnector(clientsServicePort, clientsServicePath);

                Answer answer = connector.getRequest(clientId);
                response.getWriter().println(Common.getPrettyGson().toJson(answer));
            } catch(Exception e){
                Main.getLog().error("Internal error while proceeding id: " + clientId + ", Error: " + e.getMessage());
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println(Common.getPrettyGson().toJson(new Answer("ERROR", null)));
            }
        } else {
            Main.getLog().error("Empty request.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("EMPTY_REQUEST");
        }
    }
}

/*
catch(Exception e) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Answer answer = new Answer("Error: " + e.getMessage().toString(), null);
        response.getWriter().println(Common.getPrettyGson().toJson(answer));
        }*/
