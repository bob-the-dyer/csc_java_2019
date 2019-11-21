package ru.compscicenter.java_2019.lesson_11;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//FIXME with BlockingQueue
// Вопросы:
// - Что делает это многопоточное приложение?
// - Какие есть проблемы в данном многопоточном приложении?
// - Запустим приложение прямо сейчас!
// - Фиксим тест сейчас!
// - *Для какого сценария использования больше всего подходит ArrayBlockingQueue?
public class _9_FixMeWithArrayBlockingQueueCollectionTest {
    @Test
    public void testArrayBlockingQueueWorksGreat() throws InterruptedException {

        List<Integer> list = new ArrayList<>(); //TODO уменьшить capacity до 1
        CountDownLatch latch = new CountDownLatch(1);
        List<Throwable> throwables = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            try {
                latch.await();
                for (int i = 0; i < 100; i++) {
                    list.remove(0);
                }
            } catch (Throwable throwable) {
                throwables.add(throwable);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                latch.await();
                for (int i = 0; i < 100; i++) {
                    list.add(list.size(), 5);
                }
            } catch (Throwable throwable) {
                throwables.add(throwable);
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(1000);

        latch.countDown();

        t1.join();
        t2.join();

        Assert.assertEquals(0, list.size());

        if (!throwables.isEmpty()) {
            Assert.fail(throwables.toString());
        }

    }
}
