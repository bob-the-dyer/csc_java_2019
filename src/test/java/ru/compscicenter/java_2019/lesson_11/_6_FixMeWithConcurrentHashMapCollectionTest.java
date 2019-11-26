package ru.compscicenter.java_2019.lesson_11;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.out;
import static org.junit.Assert.assertEquals;

//FIXME with ConcurrentHashMap
// Вопросы:
// - Какую коллекцию будем менять и на какую?
// - Фиксим тест сейчас!
// - *Для какого сценария по нагрузке больше всего подходит ConcurrentHashMap?
public class _6_FixMeWithConcurrentHashMapCollectionTest {
    @Test
    public void testConcurrentHashMapWorksGreat() throws InterruptedException {

        Map<String, String> map = new HashMap<>();
        CountDownLatch latch = new CountDownLatch(1);
        List<Throwable> throwables = new ArrayList<>();

        class Putting implements Runnable {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i = 0; i < 1000; i++) {
                        out.println("starting adding email " + i);
                        String s = Math.random() + "@gmail.com";
                        map.put(s, s);
                        out.println("finishing adding email " + i);
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
                        map.forEach((k, v) -> out.println(k)); // TODO а почему не падает вот тут после замены на ConcurrentHashMap?
                        out.println("finishing read iteration " + i);
                    }
                } catch (Throwable throwable) {
                    throwables.add(throwable);
                }

            }
        }

        Thread t1 = new Thread(new Putting());
        Thread t2 = new Thread(new Putting());
        Thread t3 = new Thread(new Iterating());

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);

        latch.countDown();

        t1.join();
        t2.join();
        t3.join();

        assertEquals(1000 * 2, map.size()); //TODO try several times to reproduce fail

        if (!throwables.isEmpty()) {
            Assert.fail(throwables.toString());
        }
    }
}
