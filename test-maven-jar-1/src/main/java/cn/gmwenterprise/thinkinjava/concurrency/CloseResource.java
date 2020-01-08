package cn.gmwenterprise.thinkinjava.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CloseResource {
    public static void main(String[] args) throws IOException, InterruptedException {
        var exec = Executors.newCachedThreadPool();
        var server = new ServerSocket(8080);
        var socketInput = new Socket("localhost", 8080).getInputStream();

        exec.execute(new IOBlocked(socketInput));
        exec.execute(new IOBlocked(System.in));
        TimeUnit.MILLISECONDS.sleep(100);

        System.out.println("Shutting down all threads");
        exec.shutdownNow();
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Closing " + socketInput.getClass().getName());
        socketInput.close(); // Release blocked thread
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Closing " + System.in.getClass().getName());
        System.in.close();
    }
}
