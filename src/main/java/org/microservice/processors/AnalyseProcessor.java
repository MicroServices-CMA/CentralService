package org.microservice.processors;

import org.microservice.models.AnswerCentralServer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Contains a unique predefined method used to analyse the answer received from request to other servers;
 *
 * @author Ханк
 * @version 1.1
 */
public interface AnalyseProcessor extends ResponseProcessor {

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

