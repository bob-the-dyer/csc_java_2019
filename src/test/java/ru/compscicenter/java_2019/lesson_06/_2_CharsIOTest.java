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
public class _2_CharsIOTest {

    @Test
    public void writeSomeCharsWithCharArrayWriter() {
        try (Writer writer = new CharArrayWriter()) { //Pay attention - close is done automatically!
            writer.write('0');
            writer.write(new char[]{'1', '2', '3', '4', '5'});
            writer.flush();
            assertEquals(__, ((CharArrayWriter) writer).size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readSomeCharsWithCharArrayReader() {
        try (Reader reader = new CharArrayReader(new char[]{'0', '1', '2', '3', '4', '5'})) { //Pay attention - close is done automatically!
            assertEquals(__, reader.markSupported());
            assertEquals(__, reader.read());
            char[] smallCharArray = new char[2];
            assertEquals(__, reader.read(smallCharArray));
            assertEquals(__, smallCharArray[0]);
            assertEquals(__, smallCharArray[1]);
            assertEquals(__, reader.skip(1));
            char[] giantCharArray = new char[1024];
            assertEquals(__, reader.read(giantCharArray));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void whereIsMyUserDir() {
        assertEquals(__, System.getProperty("user.dir"));
    }

    @Test
    public void writeSomeCharsWithFileWriter() {
        String fileName = "test.txt";
        try (Writer writer = new FileWriter(fileName)) { //Pay attention - close is done automatically!
            writer.write('0');
            writer.write(new char[]{'1', '2', '3', '4', '5'});
            writer.write("hello!");
            writer.write(42);
            writer.flush();
            assertEquals(__, Files.exists(Path.of(fileName)));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void readSomeChaWithFileReader() {
        String fileName = "test.txt";
        assertEquals(__, Files.exists(Path.of(fileName)));
        try (Reader reader = new FileReader(fileName)) { //Pay attention - close is done automatically!
            assertEquals(__, reader.markSupported());
            assertEquals(__, reader.read());
            char[] smallCharArray = new char[2];
            assertEquals(__, reader.read(smallCharArray));
            assertEquals(__, smallCharArray[0]);
            assertEquals(__, smallCharArray[1]);
            assertEquals(__, reader.skip(1));
            char[] giantCharArray = new char[1024];
            assertEquals(__, reader.read(giantCharArray));
        } catch (IOException e) {
            fail();
        }
    }

}
