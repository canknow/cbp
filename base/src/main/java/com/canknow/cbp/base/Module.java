package com.canknow.cbp.base;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public abstract class Module implements ApplicationRunner {
    protected abstract void initialize();

    @Override
    public void run(ApplicationArguments args) {
        this.initialize();
    }
}
