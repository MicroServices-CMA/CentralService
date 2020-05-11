package org.microservice.handlers;

import org.microservice.Main; // TODO: 08.05.2020 create log file for GET requests and delete this import + Getters and Setters in Main class
import org.microservice.classes.ServiceConnector;
import org.microservice.models.AnswerCentralServer;
import org.microservice.models.AnswerClientsServer;
import org.microservice.models.AnswerCreditHistory;
import org.microservice.processes.Analysing;
import org.microservice.processes.ResponseProcessing;
import org.microservice.processes.Searching;
import org.microservice.utils.Common;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CentralServlet extends HttpServlet implements ResponseProcessing, Searching, Analysing {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        AnswerCentralServer answerCentralServer = searchData(request);

        analyseAnswer(answerCentralServer, response);

    }
}
