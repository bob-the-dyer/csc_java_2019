package ru.compscicenter.java_2019.lesson_10;

import java.util.stream.IntStream;

public class Thread1CreateStartExample { //TODO draw
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> IntStream.range(1, 1000).forEach(System.out::println));
        System.out.println("before start");
        t1.start(); //JMM: любые действия перед start happens-before любых действий в потоке !
        System.out.println("finish");
    }
}
