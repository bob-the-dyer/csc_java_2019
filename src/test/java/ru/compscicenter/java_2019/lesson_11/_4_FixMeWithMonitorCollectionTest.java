package ru.compscicenter.java_2019.lesson_11;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static java.lang.System.out;

//FIXME with monitor
// Вопросы:
// - Что делает это многопоточное приложение?
// - Какие есть проблемы в данном многопоточном приложении?
// - Запустим приложение прямо сейчас!
// - Какие есть варианты решения этой проблемы?
// - Какой объект может быть монитором?
// - Сделаем фикс прямо сейчас!
// - Какие есть проблемы в решении с монитором?
// - Какие проблемы остаются в коде?
// - *Для чего тут нужен CountDownLatch?
// - *Зачем вызывать join() на потоках?
public class _4_FixMeWithMonitorCollectionTest {
    @Test
    public void testMonitorWorksGreat() throws InterruptedException {

        List<String> list = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        List<Throwable> throwables = new ArrayList<>();

        class Adding implements Runnable {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i = 0; i < 100; i++) {
                        out.println("starting adding email " + i);
                        IntStream.range(0, 100).forEach(index -> list.add(Math.random() + "@gmail.com"));
                        out.println("finishing adding email " + i);
                        Thread.sleep(0);
                    }
                } catch (Throwable throwable) {
                    throwables.add(throwable);
                }
            }
        }

        class Iterating implements Runnable {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i = 0; i < 1000; i++) {
                        out.println("starting read iteration " + i);
                        for (String s: list) {
                            out.println(s);
                        }
                        out.println("finishing read iteration " + i);
                        Thread.sleep(0);
                    }
                } catch (Throwable throwable) {
                    throwables.add(throwable);
                }
            }
        }

        Thread t1 = new Thread(new Adding());
        Thread t2 = new Thread(new Adding());
        Thread t3 = new Thread(new Iterating());

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);

        latch.countDown();

        t1.join();
        t2.join();
        t3.join();

        Assert.assertEquals(100 * 100 * 2, list.size());

        if (!throwables.isEmpty()) {
            Assert.fail(throwables.toString());
        }
    }
}
