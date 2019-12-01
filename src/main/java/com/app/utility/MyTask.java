package com.app.utility;

public class MyTask {
    private Runnable runnable;

    public MyTask(Runnable runnable) {
        this.runnable = runnable;
    }

    public void execute() {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }
}
