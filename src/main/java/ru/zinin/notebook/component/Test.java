package ru.zinin.notebook.component;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class Test {
    public static void main(String[] args) throws IOException {
        Path testFilePath = Paths.get("/home/daboggg/aqwerty/test.txt");
        if (!Files.exists(testFilePath.getParent())) {
            System.out.println(" not exist");
            Files.createDirectories(testFilePath.getParent());
            Files.createFile(testFilePath);
        }
    }
}

