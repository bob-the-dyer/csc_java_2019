package ru.compscicenter.java_2019.lesson_10;

import static java.lang.System.out;

public class Thread5WaitNotifyExample {

    private static class Hamburger {
    }

    private static Hamburger hamburger;

    private static final Object MONITOR = new Object();

    public static void main(String[] args) {

        Thread consumer = new Thread(() -> {
            while (true) {
                out.println("едок пытается захватить MONITOR");
                synchronized (MONITOR) {
                    out.println("едок захватывает MONITOR");
                    try {
                        if (hamburger != null) {
                            out.println("едок ест hamburger");
                            hamburger = null;
                            out.println("едок просит приготовить еще hamburger (notify)");
                            MONITOR.notify();
                        }
                        out.println("едок собирается ждать пока пекарь сделает новый hamburger и засыпает");
                        MONITOR.wait();
                        out.println("едок просыпается");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.println("едок отпускает MONITOR");
                }
            }
        }, "едок");
        consumer.start();

        Thread producer = new Thread(() -> {
            while (true) {
                out.println("пекарь пытается захватить MONITOR");
                synchronized (MONITOR) {
                    out.println("пекарь захватывает MONITOR");
                    try {
                        if (hamburger == null) {
                            out.println("пекарь готовит hamburger");
                            hamburger = new Hamburger();
                            out.println("пекарь сообщает что hamburger готов (notify)");
                            MONITOR.notify();
                        }
                        out.println("пекарь собирается ждать пока едок съест hamburger и засыпает");
                        MONITOR.wait();
                        out.println("пекарь просыпается");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.println("пекарь отпускает MONITOR");
                }
            }
        }, "пекарь");
        producer.start();
    }
}
