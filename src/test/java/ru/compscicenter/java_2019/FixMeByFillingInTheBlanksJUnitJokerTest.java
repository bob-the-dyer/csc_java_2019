package ru.compscicenter.java_2019;


import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.*;

public class FixMeByFillingInTheBlanksJUnitJokerTest {

    @Test
    public void _1_whoIsTheOutsider() {

        class RaceHorse {

            private final int speed;
            private final int age;
            private final String name;

            public RaceHorse(int speed, int age, String name) {
                this.speed = speed;
                this.age = age;
                this.name = name;
            }

        }

        RaceHorse lindy = new RaceHorse(5, 3, "lindy");

        RaceHorse lightning = new RaceHorse(4, 3, "lightning");

        RaceHorse slowy = new RaceHorse(6, 5, "slowy");

        RaceHorse[] horses = {lindy, slowy, lightning};

        Arrays.sort(horses, Comparator.comparingInt(o -> o.speed / o.age));

        assertEquals(__________, horses[0].name);

    }

    @Test
    public void _2_howAboutALittleBitOfLeftBitShifting() {

        int leftShift = 0x80000000;

        leftShift = leftShift << 1;

        assertEquals(__________, leftShift);

    }


    @Test
    public void _3_whatStringIsConstructed() {

        class A {

            String someString = "pi";

            A() {
                someString += "pu";
            }

        }

        class B extends A {

            private B() {

                someString += "pa";

            }

        }

        assertEquals(____________, new B().someString);
    }

    interface Human {

        default String sound() {
            return "Hello-oo!";
        }

    }

    interface Bull {

        default String sound() {
            return "Moo-oo!";
        }

    }

    class Minotaur implements Human, Bull {

        @Override
        public String sound() {
            //TODO Minotaur has a head of bull. How one can call the right method?
            return __________________________________________;
        }
    }

    @Test
    public void _4_howDiamondProblemIsSolvedInJava() {
        Minotaur minotaur = new Minotaur();
        assertEquals("moo-oo!", minotaur.sound());
    }


    @Test
    public void _5_methodPreferences() {

        class WiseMan {

            String whoAmI(int i) {
                return "int";
            }

            String whoAmI(Integer i) {
                return "Integer";
            }

            String whoAmI(Object i) {
                return "Object";
            }

        }

        WiseMan w = new WiseMan();

        assertEquals(________________, w.whoAmI(1));

        assertEquals(________________, w.whoAmI(Integer.valueOf(1)));

        long l = 1;
        assertEquals(________________, w.whoAmI(l));

        Long L = 1L;
        assertEquals(________________, w.whoAmI(L));

        byte b = 1;
        assertEquals(________________, w.whoAmI(b));

        Double D = 1D;
        assertEquals(________________, w.whoAmI(D));

    }

}
