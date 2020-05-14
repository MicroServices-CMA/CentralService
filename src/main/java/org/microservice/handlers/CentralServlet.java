package org.microservice.handlers;

import org.microservice.models.AnswerCentralServer;
import org.microservice.processings.Analysing;
import org.microservice.processings.ResponseProcessing;
import org.microservice.processings.Searching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CentralServlet extends HttpServlet implements ResponseProcessing, Searching, Analysing {
    public static Logger logCentralServlet = LoggerFactory.getLogger(CentralServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        AnswerCentralServer answerCentralServer = searchData(request);

        analyseAnswer(answerCentralServer, response);

    }
}
