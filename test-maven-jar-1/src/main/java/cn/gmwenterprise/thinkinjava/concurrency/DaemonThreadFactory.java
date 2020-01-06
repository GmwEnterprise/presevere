package cn.gmwenterprise.thinkinjava.concurrency;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        var daemon = new Thread(r);
        daemon.setDaemon(true);
        return daemon;
    }
}
