package ru.compscicenter.java_2019.lesson_08;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _3_HttpClientServerTest {

    @Test
    public void testsClientServerOverHttp() throws InterruptedException, IOException {

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://yandex.ru"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());//TODO debug

        assertEquals(__, response.statusCode());//TODO debug
        assertEquals(__, response.version());
        assertEquals(__, response.headers().map().size());
        assertEquals(__, response.body().contains("<html"));

    }

}
