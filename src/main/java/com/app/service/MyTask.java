package com.app.service;

import javafx.concurrent.Task;

public class MyTask extends Task<Void> {
    private Runnable runnable;

    public MyTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    protected Void call() {
        runnable.run();
        return null;
    }

    public void execute() {
        Thread thread = new Thread(new MyTask(runnable));
        thread.setDaemon(true);
        thread.start();
    }
}
