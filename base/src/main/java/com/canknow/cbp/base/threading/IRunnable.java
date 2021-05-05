package com.canknow.cbp.base.threading;

public interface IRunnable {
    void start();

    void stop();

    void waitToStop();
}
