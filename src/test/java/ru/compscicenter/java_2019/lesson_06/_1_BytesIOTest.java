package ru.compscicenter.java_2019.lesson_06;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _1_BytesIOTest {

    @Test
    public void writeSomeBytesWithByteArrayOutputStream() {
        try (OutputStream stream = new ByteArrayOutputStream()) { //Pay attention - close is done automatically!
            stream.write(42);
            stream.write(new byte[]{1, 2, 3, 4, 5});
            stream.flush();
            assertEquals(__, ((ByteArrayOutputStream) stream).size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readSomeBytesWithByteArrayInputStream() {
        try (InputStream stream = new ByteArrayInputStream(new byte[]{42, 1, 2, 3, 4, 5})) { //Pay attention - close is done automatically!
            assertEquals(__, stream.markSupported());
            assertEquals(__, stream.available());
            assertEquals(__, stream.read());
            assertEquals(__, stream.available());
            byte[] smallByteArray = new byte[2];
            assertEquals(__, stream.read(smallByteArray));
            assertEquals(__, stream.available());
            assertEquals(__, smallByteArray[0]);
            assertEquals(__, smallByteArray[1]);
            assertEquals(__, stream.skip(1));
            assertEquals(__, stream.available());
            byte[] remainingBytes = stream.readAllBytes();
            assertEquals(__, stream.available());
            assertEquals(__, remainingBytes.length);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void whereIsMyUserDir() {
        assertEquals(__, System.getProperty("user.dir"));
    }

    @Test
    public void writeSomeBytesWithFileOutputStream() {
        String fileName = "test.bin";
        try (OutputStream stream = new FileOutputStream(fileName)) { //Pay attention - close is done automatically!
            stream.write(42);
            stream.write(new byte[]{1, 2, 3, 4, 5});
            stream.flush();
            assertEquals(__, Files.exists(Path.of(fileName)));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readSomeBytesWithFileInputStream() {
        String fileName = "test.bin";
        assertEquals(__, Files.exists(Path.of(fileName)));
        try (InputStream stream = new FileInputStream(fileName)) { //Pay attention - close is done automatically!
            assertEquals(__, stream.markSupported());
            assertEquals(__, stream.available());
            assertEquals(__, stream.read());
            assertEquals(__, stream.available());
            byte[] smallByteArray = new byte[2];
            assertEquals(__, stream.read(smallByteArray));
            assertEquals(__, stream.available());
            assertEquals(__, smallByteArray[0]);
            assertEquals(__, smallByteArray[1]);
            assertEquals(__, stream.skip(1));
            assertEquals(__, stream.available());
            byte[] remainingBytes = stream.readAllBytes();
            assertEquals(__, stream.available());
            assertEquals(__, remainingBytes.length);
        } catch (IOException e) {
            fail();
        }
    }


}
