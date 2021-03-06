package ru.compscicenter.java_2019.lesson_10;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_LowLevelSynchronizationTest {

    class Counter {
        int count;

        void increment() {
            count++;
        }
    }

    @Test
    //TODO try to fix with intrinsic lock
    //TODO try to fix with syncronized block
    //TODO try to fix with AtomicInteger
    public void testsCounter() {
        Counter c1 = new Counter();
        IntStream.range(0, 1000000).forEach(i -> c1.increment());

        Counter c2 = new Counter();
        IntStream.range(0, 1000000).parallel().forEach(i -> c2.increment());

        assertEquals(__, c1.count);
        assertEquals(__, c2.count);

    }

    class SleepingBeauty implements Runnable {

        private String message = null;

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            while (message == null) ;  //TODO JMM - no guaranties on read
            System.out.println("oh, my prince!");
        }

    }

    @Test
    //TODO draw caches
    //TODO try to fix with volatile
    public void testsVolatile() throws InterruptedException {

        SleepingBeauty sleepingBeauty = new SleepingBeauty();

        Thread sb = new Thread(sleepingBeauty);

        sb.start();

        Thread.sleep(1000);

        System.out.println("before waking up");

        sleepingBeauty.setMessage("wake up!");

        System.out.println("before join");

        sb.join();

        System.out.println("after join");
    }

    static int x = 0, y = 0, a = 0, b = 0;

    @Test
    //TODO try volatile and ask why it's not working
    public void testsReordering() throws InterruptedException {

        Thread one = new Thread(() -> {
            a = 1;
            x = b;
        });
        Thread two = new Thread(() -> {
            b = 1;
            y = a;
        });

        one.start();
        two.start();

        one.join();
        two.join();

        assertEquals(__, a);
        assertEquals(__, b);
        assertEquals(__, x);
        assertEquals(__, y);

    }


    @Test
    //TODO draw deadlock
    public void testDeadlock() throws InterruptedException {
        final Integer i1 = 5;
        final Integer i2 = 10;
        AtomicInteger atomicInteger = new AtomicInteger(0);

        Thread thread1 = new Thread(() -> {
            synchronized (i1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (i2) {
                    System.out.println(i1 + i2);
                    atomicInteger.set(i1 + i2);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            synchronized (i2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (i1) {
                    System.out.println(i2 + i1);
                    atomicInteger.set(i2 + i1);
                }
            }
        });
        thread2.start();

        thread1.join(10000);
        thread2.join(10000);

        assertEquals(__, atomicInteger.get());

    }


    @Test
    //TODO draw 2 threads
    //TODO try to fix with help of synchronized block on list and on dedicated lock
    //TODO try to fix with help of class Collections
    public void testsCollection() throws InterruptedException {

        List<String> list = new ArrayList<>();

        Thread thread1 = new Thread(() -> {
            IntStream.range(0, 1000000).forEach(i -> {
                list.add(String.valueOf(i));
            });

        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            IntStream.range(0, 1000000).forEach(i -> {
                list.add(String.valueOf(i));
            });
        });
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(__, list.size());
    }


}
