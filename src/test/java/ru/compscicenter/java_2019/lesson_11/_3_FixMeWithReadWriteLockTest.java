package ru.compscicenter.java_2019.lesson_11;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import static java.lang.String.format;
import static java.lang.System.out;

//FIXME with ReadWriteReentrantLock, проверим время выполнения программы
public class _3_FixMeWithReadWriteLockTest {
    @Test
    public void testReadWriteReentrantLockWorksGreat() throws InterruptedException {
        out.println("start");

        long start = System.currentTimeMillis();
        final List<Integer> list = new ArrayList<>();
        List<Throwable> throwables = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        CyclicBarrier barrier = new CyclicBarrier(4);

        class Populator extends Thread {
            @Override
            public void run() {

                int count = 0;
                try {
                    latch.await();
                    for (int i = 0; i < 1000; i++) {
                        count++;
                        list.add(i);
                    }
                } catch (Throwable throwable) {
                    throwables.add(throwable);
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                out.println(format("add called %s times", count));
            }
        }

        class Summator extends Thread  {
            @Override
            public void run() {
                int count = 0;
                try {
                    latch.await();
                    for (int i = 0; i < 1000; i++) {
                        count++;
                        sum(list);
                    }
                } catch (Throwable throwable) {
                    throwables.add(throwable);
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                out.println(format("sum called %s times", count));
            }
        }

        Populator p1 = new Populator();
        Populator p2 = new Populator();

        Summator s1 = new Summator();
        Summator s2 = new Summator();

        p1.start();
        p2.start();
        s1.start();
        s2.start();

        Thread.sleep(1000);

        latch.countDown();

        p1.join();
        p2.join();
        s1.join();
        s2.join();

        out.println(format("execution time = %s millis", (System.currentTimeMillis() - start)));

        if (!throwables.isEmpty()) {
            Assert.fail(throwables.toString());
        }
    }

    private void sum(List<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
        BigDecimal sum = new BigDecimal(0);
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            sum = sum.add(BigDecimal.valueOf(next));
        }
        out.println(sum);
    }
}
