package com.example.web.notify;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/9/3 :6:42
 * @Description:
 */
public class NotifyAllTest {
}
class RunnerTest1 implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("test1 开始等待");
            waitSelf();
            System.out.println("test1 执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void waitSelf() throws InterruptedException {
        wait();
    }
    public synchronized void notifySelf(){
        notifyAll();
    }
}
class RunnableTest2 implements Runnable{
    private RunnerTest1 runnable;
    public RunnableTest2(RunnerTest1 runnable){
        this.runnable = runnable;
    }
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println("执行唤醒");
            runnable.notifySelf();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        RunnerTest1 runnerTest1 = new RunnerTest1();
        exec.execute(runnerTest1);
        exec.execute(new RunnableTest2(runnerTest1));
        exec.shutdown();
    }
}
