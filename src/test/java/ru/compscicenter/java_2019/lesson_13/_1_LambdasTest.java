package ru.compscicenter.java_2019.lesson_13;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_LambdasTest {

    @Test
    public void functionHasReferenceAsAnyRegularObject() {
        Runnable r8 = () -> str = "changed in lambda"; //что делают ()?
        r8.run();
        assertEquals(__, str);
    }

    @FunctionalInterface
    interface Caps {
        String capitalize(String name);

    }

    static String str = "";

    @Test
    public void simpleLambdaCreation() {
        Caps caps = (String n) -> {
            return n.toUpperCase();
        };
        String capitalized = caps.capitalize("James");
        assertEquals(__, capitalized);
    }

    @Test
    public void methodReferenceCanBeUsedAsLambda() {
        Caps caps = String::toUpperCase;
        String capitalized = caps.capitalize("Gosling");
        assertEquals(__, capitalized);
    }

    @Test
    public void shorterLambdaDefinition() {
        //parameter type can be omitted,
        //code block braces {} and return statement can be omitted for single statement lambda
        //parameter parenthesis can be omitted for single parameter lambda
        Caps caps = s -> s.toUpperCase();
        String capitalized = caps.capitalize("King Arthur");
        assertEquals(__, capitalized);
    }

    //lambda has access to "this" - this is outer object!!!
    Caps thisLambdaField = s -> this.toString();
    //lambda has access to object methods
    Caps toStringLambdaField = s -> toString();

    @Test
    public void lambdaField1() {
        assertEquals(__, thisLambdaField.capitalize(""));
    }

    @Test
    public void lambdaField2() {
        assertEquals(__, toStringLambdaField.capitalize(""));
    }

    @Test
    public void localVariablesAreEffectivelyFinal() {
        //final can be omitted like this:
        /* final */
        String effectivelyFinal = "I'm effectively final";
        Caps caps = s -> effectivelyFinal.toUpperCase();
        assertEquals(__, caps.capitalize("Am I effectively final?"));
    }


    String fieldFoo = "Lambdas";

    @Test
    public void thisIsSurroundingClass() {
        //"this" in lambda points to surrounding class
        Function<String, String> foo = s -> s + this.fieldFoo + s;
        assertEquals(__, foo.apply("|"));
    }

    @Override
    public String toString() {
        return "CAPS";
    }

}

