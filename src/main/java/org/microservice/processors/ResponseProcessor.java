package org.microservice.processors;

import org.microservice.utils.Common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Contains different predefined methods used to set response parameters:.
 *
 * @author Ханк
 * @version 1.0
 */
public interface ResponseProcessor {
    default void setRespStatus(HttpServletResponse response, int code){
        response.setStatus(code);
    }

    default<T> void writeRespContent(HttpServletResponse response, T content) throws IOException {
        response.getWriter().println(Common.getPrettyGson().toJson(content));
    }

    default<T> void setRespParam(HttpServletResponse response, int code, T content) throws IOException {
        setRespStatus(response, code);
        writeRespContent(response, content);
        response.setContentType("application/json");
    }
}
