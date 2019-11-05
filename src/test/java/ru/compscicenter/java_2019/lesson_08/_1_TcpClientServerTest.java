package ru.compscicenter.java_2019.lesson_08;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_TcpClientServerTest {

    @Test
    public void testsClientServerOverTcp() throws InterruptedException {

        StringBuilder sb = new StringBuilder();

        Thread server = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(9999)) {
                while (true) {
                    Socket accept = serverSocket.accept();
                    InputStream inputStream = accept.getInputStream(); //TODO debug
                    byte[] message = inputStream.readAllBytes();
                    sb.append(new String(message, StandardCharsets.UTF_8));
                    inputStream.close(); //Closing the returned InputStream will close the associated socket.
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }, "server-thread");

        server.start();

        Thread client = new Thread(() -> {
            String message = "message from client";
            for (int i = 0; i < message.length(); i++) {
                try (Socket clientSocket = new Socket("localhost", 9999)) {
                    OutputStream outputStream = clientSocket.getOutputStream();
                    outputStream.write(message.substring(i, i + 1).getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    outputStream.close(); //Closing the returned OutputStream will close the associated socket
                } catch (IOException e) {
                    fail(e.getMessage());
                }
            }
        }, "client-thread");

        client.start();

        Thread.sleep(2000);//TODO debug

        assertEquals(__, sb.toString());

    }

}
