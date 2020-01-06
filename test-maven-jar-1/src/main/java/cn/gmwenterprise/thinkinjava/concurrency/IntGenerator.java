package cn.gmwenterprise.thinkinjava.concurrency;

public abstract class IntGenerator {
    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        // Allow this to be canceled:
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
