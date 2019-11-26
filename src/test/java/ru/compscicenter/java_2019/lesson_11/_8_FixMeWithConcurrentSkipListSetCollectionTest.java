package ru.compscicenter.java_2019.lesson_11;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static java.lang.System.out;

//FIXME with ConcurrentSkipListSet
// Вопросы:
// - Какую коллекцию будем менять и на какую?
// - Фиксим тест сейчас!
// - *Для какого сценария использования больше всего подходит ConcurrentSkipListSet?
public class _8_FixMeWithConcurrentSkipListSetCollectionTest {
    @Test
    public void testConcurrentSkipListSetWorksGreat() throws InterruptedException {

        SortedSet<String> set = new TreeSet<>();
        CountDownLatch latch = new CountDownLatch(1);
        List<Throwable> throwables = new ArrayList<>();

        class Adding implements Runnable {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i = 0; i < 100; i++) {
                        out.println("starting adding email " + i);
                        IntStream.range(0, 100).forEach(index -> set.add(Math.random() + "@gmail.com"));
                        out.println("finishing adding email " + i);
                        Thread.sleep(0);
                    }
                } catch (Throwable throwable) {
                    throwables.add(throwable); //TODO при дебаге можно поймать NPE при балансировке дерева
                }
            }
        }

        class Iterating implements Runnable {
            @Override
            public void run() {
                try {
                    latch.await();
                    for (int i = 0; i < 100; i++) {
                        out.println("starting read iteration " + i);
                        for (String s : set) {
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

        Assert.assertEquals(100 * 100 * 2, set.size());

        if (!throwables.isEmpty()) {
            Assert.fail(throwables.toString());
        }
    }
}
