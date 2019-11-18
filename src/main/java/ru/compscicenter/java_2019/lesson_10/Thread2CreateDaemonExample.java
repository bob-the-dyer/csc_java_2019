package ru.compscicenter.java_2019.lesson_10;

import java.util.stream.IntStream;

public class Thread2CreateDaemonExample { //TODO draw
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> IntStream.range(1, Integer.MAX_VALUE).forEach(System.out::println));
        t1.setDaemon(true);//not recommended for io tasks!
        System.out.println("before start");
        t1.start();
        Thread.sleep(1000);
        System.out.println("finish");
    }
}
