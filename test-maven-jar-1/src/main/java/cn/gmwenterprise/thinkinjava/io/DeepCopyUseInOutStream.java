package cn.gmwenterprise.thinkinjava.io;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class DeepCopyUseInOutStream {

    public static Serializable executeWithByteArrayStream(Serializable serializableObj) throws Exception {
        // 使用字节流来实现对象的深拷贝
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serializableObj);

        var byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        var objectInputStream = new ObjectInputStream(byteArrayInputStream);
        var result = (Serializable) objectInputStream.readObject();

        // close
        objectInputStream.close();
        byteArrayInputStream.close();
        objectOutputStream.close();
        byteArrayOutputStream.close();

        return result;
    }

    public static Serializable executeWithFileStream(Serializable serializableObj) throws Exception {
        // 使用一个文件流来实现深拷贝，且执行结束后可以查看这个文件
        var tempFile = new File("./src/main/resources/temp/tempFile.txt");
        if (!tempFile.exists()) {
            System.out.println(tempFile.createNewFile());
        }

        var fileOutputStream = new FileOutputStream(tempFile);
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(serializableObj);

        var fileInputStream = new FileInputStream(tempFile);
        var objectInputStream = new ObjectInputStream(fileInputStream);
        return (Serializable) objectInputStream.readObject();
    }

    public static class TestObj implements Serializable {
        public String title;
        public List<TestObj> childElements;
    }

    public static void main(String[] args) throws Exception {
        TestObj testObj = new TestObj();
        testObj.title = "Root";
        testObj.childElements = Collections.singletonList(new TestObj() {{ title = "Child"; }});

        var executeResult = executeWithByteArrayStream(testObj);
        System.out.println(executeResult instanceof TestObj);

        var executeResult2 = executeWithFileStream(testObj);
        System.out.println(executeResult2 instanceof TestObj);
    }
}
