package ru.compscicenter.java_2019.lesson_10;

import static java.lang.System.out;

public class Thread51WaitNotifyExample {

    private static class Hamburger {
    }

    private static Hamburger hamburger;

    private static final Object MONITOR = new Object();

    public static void main(String[] args) {

        Thread consumer = new Thread(() -> {
            out.println("едок пытается захватить MONITOR");
            synchronized (MONITOR) {
                out.println("едок захватывает MONITOR");
                while (true) {
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
                }
            }
        }, "едок");
        consumer.start();

        Thread producer = new Thread(() -> {
            out.println("пекарь пытается захватить MONITOR");
            synchronized (MONITOR) {
                out.println("пекарь захватывает MONITOR");
                while (true) {
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
                }
            }
        }, "пекарь");
        producer.start();
    }
}
