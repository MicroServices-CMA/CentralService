package org.microservice;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.microservice.classes.Heartbeat;
import org.microservice.handlers.CentralServlet;
import org.microservice.utils.Common;
import org.microservice.utils.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class Main
{
    private static Logger log = LoggerFactory.getLogger(Main.class.getSimpleName());

    private static Server server;

    public static void main(String[] args) throws Exception
    {

        PropertyManager.load();
        Common.configure();
        runServer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {

                stopServer();

            }
        },"Stop Jetty Hook"));

    }
// ddd
    private static void runServer() {
        int port = PropertyManager.getPropertyAsInteger("server.port", 8000);
        String contextStr = PropertyManager.getPropertyAsString("server.context", "/");

        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Heartbeat heartbeat = new Heartbeat();
                int clientsServicePort = PropertyManager.getPropertyAsInteger("clientsService.port", 7000);
                int creditHistoryPort = PropertyManager.getPropertyAsInteger("creditHistoryService.port", 8500);
                String heartbeatPath = PropertyManager.getPropertyAsString("heartbeat.path", "/heart");
                if(heartbeat.checkedBeat(creditHistoryPort, heartbeatPath) && heartbeat.checkedBeat(clientsServicePort, heartbeatPath)){
                    System.out.println("Services connected!");
                }
                else
                {
                    System.out.println("Services no connected!");
                    log.error("Services no connected!");
                }
            }
        }, 3 * 1000, 3*1000);

        handler.addServletWithMapping(CentralServlet.class, "/centralServer");
        try
        {
            server.start();
            log.warn("Server has started at port: " + port);
            server.join();
        }catch(Throwable t){
            log.error("Error while starting server", t);
        }


    }

    private static void stopServer() {
        try {
            if(server.isRunning()){
                log.warn("Server is being stopped.");
                server.stop();
            }
        } catch (Exception e) {
            log.error("Error while stopping server", e);
        }
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        Main.log = log;
    }
}
