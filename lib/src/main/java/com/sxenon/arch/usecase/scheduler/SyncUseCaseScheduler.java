package com.sxenon.arch.usecase.scheduler;

public class SyncUseCaseScheduler extends BaseUseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
