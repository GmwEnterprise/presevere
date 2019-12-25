package cn.gmwenterprise.thinkinjava.chapter18;

import java.io.*;

public class ReaderTest {
    public static void main(String[] args) throws Exception {
        try (
            var reader =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(new File(
                            "./src/main/java/cn/gmwenterprise/thinkinjava/chapter18/ReaderTest.java"
                        )) {
                            @Override
                            public void close() throws IOException {
                                System.out.println("close fileInput");
                                super.close();
                            }
                        }
                    ) {
                        @Override
                        public void close() throws IOException {
                            System.out.println("close reader");
                            super.close();
                        }
                    }
                ) {
                    @Override
                    public void close() throws IOException {
                        System.out.println("close buf");
                        super.close();
                    }
                }
        ) {
            String line;
            int n = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(n++ + ": " + line);
            }
        }
    }
}
