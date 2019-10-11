package ru.compscicenter.java_2019.lesson_07;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _2_SerializationExternalizableTest {

    static class StarShip implements Externalizable {

        private static final long serialVersionUID = 2L;

        int maxWarpSpeed = 1;

        public StarShip() { //Externalizable must have a default constructor!
            maxWarpSpeed += 1;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            maxWarpSpeed++;
            out.writeInt(maxWarpSpeed);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            maxWarpSpeed = in.readInt();
            maxWarpSpeed += 1;
        }
    }

    @Test
    public void customObjectSerialization() {
        StarShip s = new StarShip();
        s.maxWarpSpeed = 9;

        try (ObjectOutputStream os =
                     new ObjectOutputStream(
                             new FileOutputStream("spaceship_ext.bin"))) {
            os.writeObject(s);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try (ObjectInputStream is =
                     new ObjectInputStream(
                             new FileInputStream("spaceship_ext.bin"))) {
            StarShip starShip = (StarShip) is.readObject();
            assertEquals(__, starShip.maxWarpSpeed);
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

    }

}

