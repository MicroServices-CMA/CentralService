package org.microservice.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.microservice.Main;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;

public class CentralServletTest {
    @Before
    public void setUp() throws Exception {
        Main.runServer();
    }


    @Test
    public void doGetFound() throws Exception
    {
        String url = "http://localhost:8000/centralServer?surname=Sergei&name=Lavrov&passportSerial=094528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doGetNoFound() throws Exception
    {
        String url = "http://localhost:8000/centralServer?surname=Sergei&name=Lavrovvv&passportSerial=094528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doGetBadRequest() throws Exception
    {
        String url = "http://localhost:8000/centralServer?sssurname=Sergei&name=Lavrov&passportSerial=094dfgd77528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doGetPageError() throws Exception
    {
        String url = "http://localhost:8000/ceeууrntralServer?sssurname=Sergei&name=Lavrov&passportSerial=094dfgd77528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @After
    public void tearDown() throws Exception {
        Main.stopServer();
    }
}