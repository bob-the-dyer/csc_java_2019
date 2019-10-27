package ru.compscicenter.java_2019.lesson_07;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_SerializationBasicsTest {


    static class StarShip implements Serializable {

        // Although it is not enforced, you should define this constant
        // to make sure you serialize/deserialize only compatible versions
        // of your objects
        private static final long serialVersionUID = 1L;

        int maxWarpSpeed;

    }

    @Test
    public void customObjectSerialization() {
        StarShip s = new StarShip();
        s.maxWarpSpeed = 9;

        try (ObjectOutputStream os =
                     new ObjectOutputStream(
                             new FileOutputStream("spaceship.bin"))) {
            os.writeObject(s);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (ObjectInputStream is =
                     new ObjectInputStream(
                             new FileInputStream("spaceship.bin"))) {
            StarShip starShip = (StarShip) is.readObject();
            assertEquals(__, starShip.maxWarpSpeed);
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

    }

    static class Engine { //this class DOESN'T implement Serializable

        String type;

        public Engine(String t) {
            type = t;
        }

    }

    static class Car implements Serializable { //this class DOES implement Serializable
        // Transient means: Ignore field for serialization
        transient Engine engine;

        // Notice these methods are private and will be called by the JVM
        // internally - as if they where defined by the Serializable interface
        // but they aren't defined as part of the interface!!
        private void writeObject(ObjectOutputStream os) throws IOException {
            os.defaultWriteObject();
            os.writeObject(engine.type);
        }

        private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
            is.defaultReadObject();
            engine = new Engine((String) is.readObject());
        }
    }

    @Test
    public void customObjectSerializationWithTransientFields() {
        // Note that this kind of access of fields is not good OO practice.
        // But let's focus on serialization here :)
        Car c = new Car();
        c.engine = new Engine("diesel");

        try (ObjectOutputStream os =
                     new ObjectOutputStream(
                             new FileOutputStream("car.bin"))) {
            os.writeObject(c);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (ObjectInputStream is =
                     new ObjectInputStream(
                             new FileInputStream("car.bin"))) {
            Car car = (Car) is.readObject();
            assertEquals(__, car.engine.type);
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

    }

    @SuppressWarnings("serial")
    static class Boat implements Serializable {
        Engine engine; //Field is NOT transient
    }

    @Test
    public void customSerializationWithUnserializableField() {
        Boat boat = new Boat();
        boat.engine = new Engine("diesel");

        String marker = "start+";

        try (ObjectOutputStream os =
                     new ObjectOutputStream(
                             new FileOutputStream("boat.bin"))) {
            os.writeObject(boat);
            marker += "finish";
        } catch (NotSerializableException e) {
            marker += "exception";
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(__, marker);
    }

    static class Animal implements Serializable { //parent implements Serializable
        String name;

        public Animal(String s) {
            name = s;
        }
    }

    static class Dog extends Animal { //child DOESN'T have to implement Serializable

        public Dog(String s) {
            super(s);
        }
    }

    @Test
    public void serializeWithInheritance() {
        Dog d = new Dog("snoopy");

        try (ObjectOutputStream os =
                     new ObjectOutputStream(
                             new FileOutputStream("dog.bin"))) {
            os.writeObject(d);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (ObjectInputStream is =
                     new ObjectInputStream(
                             new FileInputStream("dog.bin"))) {
            Dog dog = (Dog) is.readObject();
            assertEquals(__, dog.name);
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }
    }

    static class Plane {//parent DOESN'T implement Serializable
        String name = "unknown";

        public Plane(String s) {
            name = s;
        }

        public Plane() { //TODO what happens if the Plane doesn't have default constructor?
            name = "noname";
        }

    }

    static class MilitaryPlane extends Plane implements Serializable { //child DOES implement Serializable
        public MilitaryPlane(String s) {
            super(s);
        }
    }

    @Test
    public void serializeWithInheritanceWhenParentNotSerializable() {
        MilitaryPlane p = new MilitaryPlane("F22");

        try (ObjectOutputStream os =
                     new ObjectOutputStream(
                             new FileOutputStream("plane.bin"))) {
            os.writeObject(p);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (ObjectInputStream is =
                     new ObjectInputStream(
                             new FileInputStream("plane.bin"))) {
            MilitaryPlane plane = (MilitaryPlane) is.readObject();
            // Does this surprise you?
            assertEquals(__, plane.name);

            // Think about how deserialization creates objects...
            // It does not use constructors! But if a parent object is not serializable
            // it actually uses constructors and if the fields are not in a serializable class...
            // unexpected things - like this - may happen
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

    }

}

