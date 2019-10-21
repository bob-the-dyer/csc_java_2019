package ru.compscicenter.java_2019.lesson_06;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _3_DecoratorIOTest {

    private static String personFile = "person.bin";
    private static String textFile = "textFile.txt";

    static class Person implements Serializable {

        private final int age;
        private final String name;


        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

    }

    @Test
    public void whereIsMyUserDir() {
        assertEquals(__, System.getProperty("user.dir"));
    }

    @Test
    public void serializePerson() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(personFile))) {
            Person хоттабыч = new Person(92, "Старик Хоттабыч");
            oos.writeObject(хоттабыч);
            oos.flush();
            assertEquals(__, Files.exists(Path.of(personFile)));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void deserializePerson() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(personFile))) {
            assertEquals(__, ois.markSupported());
            Person person = (Person) ois.readObject();
            assertEquals(__, person.getAge());
        } catch (IOException | ClassNotFoundException e) {
            fail();
        }
    }

    @Test
    public void writeTextFile() {
        String line1 = "Hello Java, str1";
        String line2 = "Hello Java, str2";
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(textFile))) {
            writer.write(line1);
            writer.newLine();
            writer.write(line2);
            writer.flush();
            assertEquals(__, Files.exists(Path.of(textFile)));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readTextFile() {
        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(textFile))) {
            assertEquals(__, reader.markSupported());
            String line = reader.readLine();
            assertEquals(__, line);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void zipping() {
        try (
                ZipOutputStream zipOut =
                        new ZipOutputStream(
                                new BufferedOutputStream(
                                        new FileOutputStream("super.zip")));
                InputStream text =
                        new BufferedInputStream(
                                new FileInputStream(textFile));
                InputStream bin =
                        new BufferedInputStream(
                                new FileInputStream(personFile));
        ) {
            ZipEntry textEntry = new ZipEntry(textFile);
            zipOut.putNextEntry(textEntry);
            int buffer;
            while ((buffer = text.read()) > 0) {
                zipOut.write(buffer);
            }
            zipOut.closeEntry();

            ZipEntry binEntry = new ZipEntry(personFile);
            zipOut.putNextEntry(binEntry);

            while ((buffer = bin.read()) > 0) {
                zipOut.write(buffer);
            }
            zipOut.closeEntry();

        } catch (IOException e) {
            fail();
        }
    }

}
