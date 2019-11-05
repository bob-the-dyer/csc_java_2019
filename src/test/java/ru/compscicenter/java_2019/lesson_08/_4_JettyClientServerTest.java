package ru.compscicenter.java_2019.lesson_08;


import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _4_JettyClientServerTest {

    private Server server;

    static public class MirrorServlet extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);//TODO debug
            response.setContentType("text/html");
            String mirror = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            response.getWriter().print(mirror);
        }

    }

    @Before
    public void setup() throws Exception {
        server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);

        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(MirrorServlet.class, "/mirror");
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


    @Test
    public void testsClientServerOverHttpToJetty() throws InterruptedException, IOException {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/mirror"))
                .method("GET", HttpRequest.BodyPublishers.ofString("what's a difference a day made?"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());//TODO debug

        assertEquals(__, response.statusCode());//TODO debug
        assertEquals(__, response.body());

    }

}
