package com.example.web.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: 36560
 * @Date: 2019/8/15 :21:13
 * @Description:
 */
public class Executor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){
            executorService.execute(new LightOff());
        }
        executorService.shutdown();
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);
        for (int i=0;i<20;i++){
            executorService1.execute(new LightOff());
        }
        executorService1.shutdown();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i=0;i<20;i++){
            singleThreadExecutor.execute(new LightOff());
        }
        singleThreadExecutor.shutdown();

    }
}
