package ru.compscicenter.java_2019.lesson_06;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _4_PathTest {

    @Test
    public void whereIsMyUserDir() {
        assertEquals(__, System.getProperty("user.dir"));
    }

    @Test
    public void dirAndFileAsPath() {

        Path userDirPath = Path.of(System.getProperty("user.dir"));

        assertEquals(__, userDirPath.toString());
        assertEquals(true, userDirPath.endsWith(__));
        assertEquals(true, userDirPath.startsWith(__));

        assertEquals(__, userDirPath.getNameCount());
        assertEquals(__, userDirPath.getName(0));
        assertEquals(__, userDirPath.getName(1));

        assertEquals(__, userDirPath.isAbsolute());

        Path readmePath = Path.of(System.getProperty("user.dir"), "README.md");

        assertEquals(__, Files.exists(readmePath));

        assertEquals(__, userDirPath.compareTo(readmePath));
        assertEquals(__, readmePath.compareTo(readmePath));
        assertEquals(__, readmePath.compareTo(userDirPath));

        assertEquals(Path.of(__), readmePath.relativize(userDirPath));
        assertEquals(Path.of(__), userDirPath.relativize(readmePath));

        assertEquals(Path.of(__), readmePath.getRoot());
        assertEquals(Path.of(__), readmePath.getParent());
    }

    @Test
    public void fileSystem() {
        Path readmePath = Path.of(System.getProperty("user.dir"), "README.md");
        FileSystem fileSystem = readmePath.getFileSystem();
        for (Path root : fileSystem.getRootDirectories()) {
            System.out.println(root);
        }
        System.out.println("----");
        for (FileStore store : fileSystem.getFileStores()) {
            System.out.println(store);
        }
        assertEquals(__, fileSystem.getSeparator());
    }

}
