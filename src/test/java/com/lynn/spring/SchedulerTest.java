package com.lynn.spring;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulerTest {


    static ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(2);

    private static long initialDelayMs = 1000;
    private static long refreshIntervalMs = 3000;

    public static void main(String[] args) throws IOException {
        System.out.println("begin");
        AtomicInteger i = new AtomicInteger();
        enableAndInitLearnNewServersFeature(() -> {
            System.out.println(i.getAndIncrement());
        });
        System.in.read();
    }

    public interface ServerListUpdater {
        public interface UpdateAction {
            void doUpdate();
        }
    }

    private static void enableAndInitLearnNewServersFeature(ServerListUpdater.UpdateAction updateAction) {
        Runnable wrapperRunnable = () -> {
            updateAction.doUpdate();
        };
        scheduledExecutor.scheduleWithFixedDelay(wrapperRunnable, initialDelayMs,
                refreshIntervalMs, TimeUnit.MILLISECONDS);
    }

}
