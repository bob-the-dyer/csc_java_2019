package ru.compscicenter.java_2019.lesson_10;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Thread4InterruptExample { //TODO draw 2 possibly interruption moments
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true) {
                try {

                    IntStream.range(1, 10000).forEach(System.out::println);

                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("interrupted by flag");
                        return;
                    }

                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));

                } catch (InterruptedException e) {
                    System.out.println("interrupted by exception");
                    //TODO поговорить о том что тут надо делать с эксепшеном или флагом
                    return;
                }
            }
        });

        t1.start();

        Thread.sleep(ThreadLocalRandom.current().nextInt(1000));

        t1.interrupt();

        System.out.println("finished");
    }
}
