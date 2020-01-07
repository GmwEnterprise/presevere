package cn.gmwenterprise.thinkinjava.io;

import java.io.*;
import java.util.ArrayList;

public class TestTryWithResource {

    public static void main(String[] args) throws Exception {
        File file = new File("./src/main/resources/temp/test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        try (
            FileOutputStream fileOutputStream = new FileOutputStream(file) {
                @Override
                public void close() throws IOException {
                    System.out.println("close fileOutputStream");
                    super.close();
                }
            };
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream) {
                @Override
                public void close() throws IOException {
                    System.out.println("close bufferedOutputStream");
                    super.close();
                }
            };
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream) {
                @Override
                public void close() throws IOException {
                    System.out.println("close objectOutputStream");
                    super.close();
                }
            }
        ) {
            objectOutputStream.writeChars("HELLO WORLD");
            System.out.println("done.");
        }
    }


    void test() {
        final ArrayList<String> strings = new ArrayList<>();
        strings.removeIf(item -> !"".equals(item));
    }
}
