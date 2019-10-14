package ru.compscicenter.java_2019.lesson_05;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_ExceptionsTest {

    private void doStuff() throws IOException {
        throw new IOException();
    }

    @Test
    public void catchCheckedExceptions() {
        String s;
        try {
            doStuff();
            s = "code ran normally";
        } catch (IOException e) {
            s = "exception thrown";
        }
        assertEquals(__, s);
    }

    @Test
    public void useFinally() {
        String s = "";
        try {
            doStuff();
            s += "code ran normally";
        } catch (IOException e) {
            s += "exception thrown";
        } finally {
            s += " and finally ran as well";
        }
        assertEquals(__, s);
    }

    @Test
    public void finallyWithoutCatch() {
        String s = "";
        try {
            s = "code ran normally";
        } finally {
            s += " and finally ran as well";
        }
        assertEquals(__, s);
    }

    @Test(expected = Test.None.class)
    public void finallyWithoutCatchWithException() throws Exception {
        String s = "";
        try {
            s = "code ran normally";
            doStuff();
        } finally {
            s += " and finally ran as well";
            assertEquals(__, s);
        }
    }

    private void tryCatchFinallyWithVoidReturn(StringBuilder whatHappened) {
        try {
            whatHappened.append("did something dangerous");
            doStuff();
        } catch (IOException e) {
            whatHappened.append("; the catch block executed");
        } finally {
            whatHappened.append(", but so did the finally!");
        }
    }

    @Test
    public void finallyIsAlwaysRan() {
        StringBuilder whatHappened = new StringBuilder();
        tryCatchFinallyWithVoidReturn(whatHappened);
        assertEquals(__, whatHappened.toString());
    }

    @SuppressWarnings("finally")
    // this is suppressed because returning in finally block is obviously a compiler warning
    private String returnStatementsEverywhere(StringBuilder whatHappened) {
        try {
            whatHappened.append("try");
            doStuff();
            return "from try";
        } catch (IOException e) {
            whatHappened.append(", catch");
            return "from catch";
        } finally {
            whatHappened.append(", finally");
            // Think about how bad an idea it is to put a return statement in the finally block
            // DO NOT DO THIS!
            return "from finally";
        }
    }

    @Test
    public void returnInFinallyBlock() {
        StringBuilder whatHappened = new StringBuilder();
        // Which value will be returned here?
        assertEquals(__, returnStatementsEverywhere(whatHappened));
        assertEquals(__, whatHappened.toString());
    }

    private void doUncheckedStuff() {
        throw new RuntimeException();
    }

    @Test(expected = Test.None.class)
    public void catchUncheckedExceptions() {
        // What do you need to do to catch the unchecked exception?
        doUncheckedStuff();
    }

    @SuppressWarnings("serial")
    static class ParentException extends Exception {
    }

    @SuppressWarnings("serial")
    static class ChildException extends ParentException {
    }

    private void throwIt() throws ParentException {
        throw new ChildException();
    }

    @Test
    public void catchOrder() {
        String s = "";
        try {
            throwIt();
            //what happens if you change the order of catch blocks?
        } catch (ChildException e) {
            s = "ChildException";
        } catch (ParentException e) {
            s = "ParentException";
        } catch (Exception e) {
            s = "Exception";
        }
        assertEquals(__, s);
    }

    private int validateUsingIllegalArgumentException(String str) {
        // This is effective and both the evaluation and the error message
        // can be tailored which can be particularly handy if you're guarding
        // against more than null values
        if (null == str) {
            throw new IllegalArgumentException("str should not be null");
        }
        return str.length();
    }

    @Test
    public void failArgumentValidationWithAnIllegalArgumentException() {
        // This test demonstrates the use of exceptions in argument validation
        String s = "";
        try {
            s += validateUsingIllegalArgumentException(null);
        } catch (IllegalArgumentException ex) {
            s = "caught an IllegalArgumentException";
        }
        assertEquals(__, s);
    }

    @Test
    public void passArgumentValidationWithAnIllegalArgumentException() {
        // This test demonstrates the use of exceptions in argument validation
        String s = "";
        try {
            s += validateUsingIllegalArgumentException("valid");
        } catch (IllegalArgumentException ex) {
            s = "caught an IllegalArgumentException";
        }
        assertEquals(__, s);
    }

    @Test
    public void testIndexOutOfBoundsException() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= 10; i++) {
            Integer integer = integers.get(i);
            sb.append(integer);
        }
        assertEquals(__, sb.toString());
    }

    @Test(expected = Test.None.class)
    public void testStackOverflowError() { //DON'T DO THAT EVER!
        testStackOverflowError();
    }

    @Test(expected = Test.None.class)
    public void testOutOfMemoryError() { //DON'T DO THAT EVER!
        List<Object> garbage = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            garbage.add(i);
        }
        for (int i = 0; i < 1000000; i++) {
            garbage.add(List.copyOf(garbage));
        }
    }

}
