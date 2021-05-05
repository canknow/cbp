package com.canknow.cbp.base.threading;

public abstract class RunnableBase implements IRunnable {
    private volatile Boolean _isRunning;

    public Boolean getIsRunning() {
        return _isRunning;
    }

    @Override
    public void start() {
        _isRunning = true;
    }

    @Override
    public void stop() {
        _isRunning = false;
    }

    @Override
    public void waitToStop() {

    }
}
