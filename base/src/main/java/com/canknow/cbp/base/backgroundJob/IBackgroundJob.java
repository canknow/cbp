package com.canknow.cbp.base.backgroundJob;

public interface IBackgroundJob<TArgs> {
    void execute(TArgs args) throws Exception;
}
