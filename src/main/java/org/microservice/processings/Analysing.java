package org.microservice.processings;

import org.microservice.models.AnswerCentralServer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Analysing extends ResponseProcessing {

    default void analyseAnswer(AnswerCentralServer ans, HttpServletResponse response) throws IOException {

        int statusCode;
        switch (ans.getStatus()) {
            case "OK":
                statusCode = HttpServletResponse.SC_OK;
                break;
            case "CLIENT_NOT_FOUND":
                statusCode = HttpServletResponse.SC_NOT_FOUND;
                break;
            case "INTERNAL_CENTRAL_SERVER_ERROR":
            case "INTERNAL_CLIENTS_SERVER_ERROR":
            case "INTERNAL_HISTORY_SERVER_ERROR":
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                break;
            default:
                statusCode = HttpServletResponse.SC_BAD_REQUEST;
                break;
        }

        setRespParam(response, statusCode, ans);
    }
}

