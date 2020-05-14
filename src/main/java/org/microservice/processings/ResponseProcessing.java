package org.microservice.processings;

import org.microservice.utils.Common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseProcessing {
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
