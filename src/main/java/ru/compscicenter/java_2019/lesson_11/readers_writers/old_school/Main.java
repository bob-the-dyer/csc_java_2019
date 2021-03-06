package ru.compscicenter.java_2019.lesson_11.readers_writers.old_school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static ru.compscicenter.java_2019.lesson_11.readers_writers.Utils.*;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        log.info("starting Main");

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true); //try unfair lock as well

        CountDownLatch latch = new CountDownLatch(1);

        IntStream.range(0, NUMBER_OF_READERS).forEach(i ->
                new Reader("reader_" + i, rwLock, latch).start()
        );
        IntStream.range(0, NUMBER_OF_WRITERS).forEach(i ->
                new Writer("writer_" + i, rwLock, latch).start()
        );

        Thread.sleep(1000);

        log.info("all threads should be ready now but no guaranties!");

        latch.countDown();

        exitAfterDelay(() -> Reader.servedReaders.get(), () -> Writer.servedWriters.get());
    }

}
