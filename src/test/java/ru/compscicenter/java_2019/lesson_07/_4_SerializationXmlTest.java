package ru.compscicenter.java_2019.lesson_07;

import com.thoughtworks.xstream.XStream;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _4_SerializationXmlTest {


    static class Engine {

        private String type;

        public Engine() { //default must have for xstream to work

        }

        public Engine(String t) {
            type = t;
        }

        public String getType() { //must have for jackson to work
            return type;
        }

        public void setType(String type) { //must have for jackson to work
            this.type = type;
        }
    }

    static class Car {

        private Engine engine;

        public Engine getEngine() { //must have for jackson to work
            return engine;
        }

        public void setEngine(Engine engine) { //must have for jackson to work
            this.engine = engine;
        }
    }

    @Test
    public void customObjectSerializationToXml() {
        Car c = new Car();
        c.setEngine(new Engine("diesel"));

        XStream xstream = new XStream();

        try (Writer writer = new FileWriter("car.xml")) {
            xstream.toXML(c, writer);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (Reader reader = new FileReader("car.xml")) {
            Car car = (Car) xstream.fromXML(reader);
            assertEquals(__, car.getEngine().getType());
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

}

