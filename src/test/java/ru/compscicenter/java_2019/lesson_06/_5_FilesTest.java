package ru.compscicenter.java_2019.lesson_06;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _5_FilesTest {

    @Test
    public void files() throws IOException {

        String emptyDirName = "empty_dir";
        String copyFileName = "README_COPY.md";
        String emptyFileName = "empty_file.txt";

        Files.deleteIfExists(Path.of(emptyDirName));
        Files.deleteIfExists(Path.of(copyFileName));
        Files.deleteIfExists(Path.of(emptyFileName));

        Path readmePath = Path.of(System.getProperty("user.dir"), "README.md");

        assertEquals(__, Files.exists(readmePath));
        assertEquals(__, Files.size(readmePath));
        assertEquals(__, Files.readString(readmePath));
        assertEquals(__, Files.readAllLines(readmePath).size());
        assertEquals(__, Files.isDirectory(readmePath));
        assertEquals(__, Files.isExecutable(readmePath));
        assertEquals(__, Files.isHidden(readmePath));
        assertEquals(__, Files.isReadable(readmePath));
        assertEquals(__, Files.isWritable(readmePath));
        assertEquals(__, Files.isSymbolicLink(readmePath));
        assertEquals(__, Files.isSameFile(readmePath, readmePath));

        assertEquals(__, new String(Files.readAllBytes(readmePath)));

        Files.copy(readmePath, Path.of(copyFileName));
        assertEquals(__, Files.exists(Path.of(copyFileName)));

        Files.createFile(Path.of(emptyFileName));
        assertEquals(__, Files.exists(Path.of(emptyFileName)));

        Files.createDirectory(Path.of(emptyDirName));
        assertEquals(__, Files.exists(Path.of(emptyDirName)));

        Path tempFile = Files.createTempFile("csc", "2019");
        assertEquals(__, tempFile);
    }

    @Test
    public void walking() throws IOException {
        Path userDir = Path.of(System.getProperty("user.dir"));
        final int[] files = {0};
        final int[] dirs = {0};
        Files.walkFileTree(userDir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirs[0]++;
                System.out.println(dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                files[0]++;
                System.out.println(file);
                return super.visitFile(file, attrs);
            }
        });
        assertEquals(__, files[0]);
        assertEquals(__, dirs[0]);
    }

    @Test
    public void watching() throws InterruptedException, IOException {
        Path userDir = Path.of(System.getProperty("user.dir"));

        WatchService watcher = userDir.getFileSystem().newWatchService();
        userDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
        WatchKey key = watcher.poll(2, TimeUnit.MINUTES);
        // TODO create new file
        assertEquals(__, key.pollEvents().size());
        assertEquals(__, key.pollEvents().get(0).context());
    }

}
