package com.example.web.thread;

import com.sun.xml.bind.v2.model.core.ID;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/8/26 :7:03
 * @Description:
 */
class Accessor implements Runnable{
    private final int id;
    public Accessor(int id){this.id=id;}

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            ThreadLocalHolder.increment();
            System.out.println(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public String toString(){
        return "#"+id+":"+ThreadLocalHolder.get();
    }
}
public class ThreadLocalHolder {
    private static ThreadLocal<Integer> threadLocal= new java.lang.ThreadLocal<Integer>(){
        private Random random = new Random(47);
        protected synchronized Integer initialValue(){
            return random.nextInt(10000);
        }
    };
    public static void increment(){
        threadLocal.set(threadLocal.get()+1);
    }
    public  static Integer get(){
        return threadLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool(new HolderThreadFactory());
        for (int i = 0 ;i<5;i++){
            executorService.execute(new Accessor(i));
        }
        TimeUnit.MILLISECONDS.sleep(500);
        executorService.shutdown();
    }
}
class HolderThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }
}
