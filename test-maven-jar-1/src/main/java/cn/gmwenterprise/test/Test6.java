package cn.gmwenterprise.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Test6 {
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        Runnable r = () -> {
            try (var fin = new FileInputStream(new File(
                "E:\\Videos\\视频教程\\慕课网 - 基于TypeScript从零重构axios(14章完整版) - 2019年" +
                "\\第7章 ts-axios 接口扩展【工厂模式的合理运用，设计思想的转变】\\7.1-7.4.mp4"))) {
                byte[] buf = new byte[(int) Math.pow(2, 30)];
                int p;
                while ((p = fin.read(buf)) != -1) {

                }
                flag = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(r);
        thread.start();
        List<Thread.State> states = new LinkedList<>();
        while (flag) {
            states.add(thread.getState());
            Thread.yield();
        }
        System.out.println(states.stream().distinct().collect(Collectors.toList()));
    }
}
