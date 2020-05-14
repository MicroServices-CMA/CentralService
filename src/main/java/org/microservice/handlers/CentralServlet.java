package org.microservice.handlers;

import org.microservice.models.AnswerCentralServer;
import org.microservice.processors.AnalyseProcessor;
import org.microservice.processors.ResponseProcessor;
import org.microservice.processors.SearchProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is the servlet used to receive, transform and send back different requests and answers.
 *
 * @author Ханк
 * @version 1.4
 */
public class CentralServlet extends HttpServlet implements ResponseProcessor, SearchProcessor, AnalyseProcessor {
    public static Logger logCentralServlet = LoggerFactory.getLogger(CentralServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        AnswerCentralServer answerCentralServer = searchData(request);

        analyseAnswer(answerCentralServer, response);

    }
}
