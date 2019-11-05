package ru.compscicenter.java_2019.lesson_08;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _2_UdpClientServerTest {

    @Test
    public void testsClientServerOverUdp() throws InterruptedException {

        StringBuilder sb = new StringBuilder();

        Thread server = new Thread(() -> {
            try (DatagramSocket datagramSocket = new DatagramSocket(9999)) {
                while (true) {
                    byte[] buffer = new byte[65508];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    datagramSocket.receive(packet);
                    String subMessage = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);//TODO debug
                    sb.append(subMessage);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }, "server-thread");

        server.start();

        Thread client = new Thread(() -> {
            String message = "message from client";
            for (int i = 0; i < message.length(); i++) {
                try (DatagramSocket datagramSocket = new DatagramSocket()) {
                    byte[] buffer = message.substring(i, i + 1).getBytes();
                    InetAddress receiverAddress = InetAddress.getLocalHost();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress, 9999);
                    datagramSocket.send(packet);
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
