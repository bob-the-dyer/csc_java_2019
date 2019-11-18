package ru.compscicenter.java_2019.lesson_10;

import static java.lang.System.out;

public class Thread5WaitNotifyExample { //TODO draw phone

    private static class Hamburger {
    }

    private static Hamburger hamburger;

    private static final Object TRAY = new Object();

    public static void main(String[] args) {

        Thread consumer = new Thread(() -> {
            while (true) {
                out.println("едок пытается захватить TRAY");
                synchronized (TRAY) {
                    out.println("едок захватывает TRAY");
                    try {
                        if (hamburger != null) {
                            out.println("едок ест hamburger");
                            hamburger = null;
                            out.println("едок просит приготовить еще hamburger (notify)");
                            TRAY.notify();
                        }
                        out.println("едок собирается ждать пока пекарь сделает новый hamburger и засыпает");
                        TRAY.wait();
                        out.println("едок просыпается");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.println("едок отпускает TRAY");
                }
            }
        }, "едок");
        consumer.start();

        Thread producer = new Thread(() -> {
            while (true) {
                out.println("пекарь пытается захватить TRAY");
                synchronized (TRAY) {
                    out.println("пекарь захватывает TRAY");
                    try {
                        if (hamburger == null) {
                            out.println("пекарь готовит hamburger");
                            hamburger = new Hamburger();
                            out.println("пекарь сообщает что hamburger готов (notify)");
                            TRAY.notify();
                        }
                        out.println("пекарь собирается ждать пока едок съест hamburger и засыпает");
                        TRAY.wait();
                        out.println("пекарь просыпается");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.println("пекарь отпускает TRAY");
                }
            }
        }, "пекарь");
        producer.start();
    }
}
