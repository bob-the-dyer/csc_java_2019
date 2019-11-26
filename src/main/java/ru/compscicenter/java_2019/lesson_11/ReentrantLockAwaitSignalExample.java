package ru.compscicenter.java_2019.lesson_11;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;

public class ReentrantLockAwaitSignalExample { //TODO see ru.compscicenter.java_2019.lesson_10.Thread5WaitNotifyExample

    private static class Hamburger {
    }

    private static Hamburger hamburger;

    private static final Lock TRAY = new ReentrantLock();
    private static final Condition HAMBURGER_EXCHANGE = TRAY.newCondition(); // может быть сколько угодно Condition!

    public static void main(String[] args) {

        Thread consumer = new Thread(() -> {
            while (true) {
                out.println("едок пытается захватить TRAY");
                TRAY.lock(); // TODO lock может быть в одном месте ...
                try {
                    out.println("едок захватывает TRAY");
                    if (hamburger != null) {
                        out.println("едок ест hamburger");
                        hamburger = null;
                        out.println("едок просит приготовить еще hamburger (signal)");
                        HAMBURGER_EXCHANGE.signal();
                    }
                    out.println("едок собирается ждать пока пекарь сделает новый hamburger и засыпает");
                    HAMBURGER_EXCHANGE.await();
                    out.println("едок просыпается");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    TRAY.unlock(); // TODO ... а unlock может быть в совершенно другом!
                    out.println("едок отпускает TRAY");
                }
            }
        }, "едок");
        consumer.start();

        Thread producer = new Thread(() -> {
            while (true) {
                out.println("пекарь пытается захватить TRAY");
                TRAY.lock();
                try {
                    out.println("пекарь захватывает TRAY");
                    if (hamburger == null) {
                        out.println("пекарь готовит hamburger");
                        hamburger = new Hamburger();
                        out.println("пекарь сообщает что hamburger готов (signal)");
                        HAMBURGER_EXCHANGE.signal(); // TODO  не путать с HAMBURGER_EXCHANGE.notify()!
                    }
                    out.println("пекарь собирается ждать пока едок съест hamburger и засыпает");
                    HAMBURGER_EXCHANGE.await(); // TODO  не путать с HAMBURGER_EXCHANGE.wait()!
                    out.println("пекарь просыпается");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    TRAY.unlock();
                    out.println("пекарь отпускает TRAY");
                }
            }
        }, "пекарь");
        producer.start();
    }
}
