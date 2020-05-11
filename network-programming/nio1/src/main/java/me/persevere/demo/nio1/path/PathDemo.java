package me.persevere.demo.nio1.path;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class PathDemo {

    public static void main(String[] args) throws IOException {
        Path p = Path.of("D:\\文本文件.txt");
        try (var reader = new BufferedReader(new InputStreamReader(new FileInputStream(p.toFile())))) {
            System.out.println(reader.readLine());
        }
    }
}
