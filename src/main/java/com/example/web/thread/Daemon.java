package com.example.web.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/8/16 :6:08
 * @Description:
 */
public class Daemon {
    static class SimpleDaemon implements Runnable{
        @Override
        public void run() {
            try {
                int count =0;
                while (count++ <2){
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println(Thread.currentThread()+""+this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"终止");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            for (int i=0;i<10;i++){
                Thread thread = new Thread(new SimpleDaemon());
                thread.setDaemon(true);
                thread.start();
            }
            System.out.println("all daemon started ");
            TimeUnit.MILLISECONDS.sleep(101);
        }
    }

    /**
     * 后台线程工厂
     */
    static class DaemonTheadFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.setDaemon(true);
            return thread;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonTheadFactory());
        for (int i=0;i<10;i++) {
            executorService.execute(new SimpleDaemon());
        }
        System.out.println("all daemon started");
        TimeUnit.MILLISECONDS.sleep(120);
        Thread thread = new Thread(() -> {
            System.out.println("测试runnable");
        });
        thread.start();
    }
}
