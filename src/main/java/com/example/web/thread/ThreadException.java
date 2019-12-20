package com.example.web.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @Auther: 36560
 * @Date: 2019/8/17 :6:04
 * @Description:
 */
public class ThreadException {
    static class ExceptionTask implements Runnable{
        @Override
        public void run() {
            throw new RuntimeException("抛出异常");
        }
    }
    static class ExceptionHandler implements Thread.UncaughtExceptionHandler{
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(e.getMessage());
        }
    }
    static class HandlerThreadFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new ExceptionHandler());
            return thread;
        }
    }
    static class main{
        public static void main(String[] args) {
            ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
            executorService.execute(new ExceptionTask());
        }
    }
}
