package com.example.web.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/9/17 :6:59
 * @Description:
 */
class TaskPortion implements Runnable{
    private static int count = 0;
    private final int id = count++ ;
    private final CountDownLatch countDownLatch;
    private static Random random = new Random(47);
    public TaskPortion(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        System.out.println(this+"completed");
    }
    public String toString(){
        return String.format("%1$-3d",id);
    }
    @Override
    public void run() {
        try {
            doWork();
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            System.out.println("interrupted taskPortion");
        }
    }
}
class WaitingTask implements Runnable{
    private static int count = 0;
    private final int id = count++;
    private final CountDownLatch countDownLatch;
    public WaitingTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    public String toString(){
        return String.format("WaitingTask %1$-3d",id);
    }
    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println("Latch Barrier Passed For "+this);
        } catch (InterruptedException e) {
            System.out.println(this+"interrupted");
        }
    }
}
public class CountDownLatchDemo {
    static final int size = 100;
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(size);
        for (int i = 0;i<10;i++){
            exec.execute(new WaitingTask(latch));
        }
        for (int i = 0;i<size;i++){
            exec.execute(new TaskPortion(latch));
        }
        System.out.println("Latch All Tasks");
        exec.shutdown();
    }
}
