package com.app.utility;

import javafx.concurrent.Task;

public class BackgroundTask extends Task<Void> {
    private Runnable runnable;

    public BackgroundTask(Runnable runnable) {
        this.runnable = runnable;
    }

    public void execute() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected Void call() throws Exception {
        runnable.run();
        return null;
    }
}
