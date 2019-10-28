package ru.compscicenter.java_2019.lesson_07;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _3_SerializationJsonTest {


    static class Engine {

        private String type;

        public Engine() { //default must have for jackson to work

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
    public void customObjectSerializationToJson() {
        Car c = new Car();
        c.setEngine(new Engine("diesel"));

        ObjectMapper objectMapper = new ObjectMapper();

        try (Writer writer = new FileWriter("car.json")) {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(writer, c);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (Reader reader = new FileReader("car.json")) {
            Car car = objectMapper.readValue(reader, Car.class);
            assertEquals(__, car.getEngine().getType());
            //What happens if you change car.json structurally? Names of elements?
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

}

