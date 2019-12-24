package cn.gmwenterprise.thinkinjava.chapter18;

import java.io.*;
import java.nio.charset.Charset;

public class InputFilterUsage {

    public static void main(String[] args) throws Exception {
        use1();
    }

    private static void use1() throws Exception {
        File basicDataFile = new File("./src/main/resources/temp/basicData.txt");
//        write(basicDataFile);
        read(basicDataFile);
    }

    private static void read(File basicDataFile) throws Exception {
        var fileIn = new FileInputStream(basicDataFile);
        var bufIn = new BufferedInputStream(fileIn);
        try (var dataIn = new DataInputStream(bufIn)) {
            System.out.println(dataIn.readUTF());
        }
    }

    private static void write(File basicDataFile) throws Exception {
        var fileOut = new FileOutputStream(basicDataFile);
        var bufOut = new BufferedOutputStream(fileOut);
        try (var dataOut = new DataOutputStream(bufOut)) {
            dataOut.writeUTF("Hello world ！");
            dataOut.flush();
        }
    }
}
