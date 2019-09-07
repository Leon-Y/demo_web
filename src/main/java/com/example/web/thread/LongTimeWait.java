package com.example.web.thread;

import javax.validation.constraints.Pattern;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/9/3 :7:00
 * @Description:
 */
public class LongTimeWait {
    private volatile boolean flag = false;
    public synchronized void setFlag(boolean flag){
        this.flag = flag;
    }
    public boolean getFlag(){
        return flag;
    }
    public synchronized void waitFlag() throws InterruptedException {
        while (!flag){
            wait();
        }
        System.out.println("脱离阻塞");
    }
    public synchronized void notifyAllFlag(){
        flag = true;
        notifyAll();
    }
}

class Wait1 implements Runnable{
    private LongTimeWait longTimeWait;
    public Wait1(LongTimeWait longTimeWait){
        this.longTimeWait = longTimeWait;
    }
    @Override
    public void run() {
        while (true){
            if (longTimeWait.getFlag()){
                System.out.println("线程1执行");
                longTimeWait.setFlag(false);
            }
        }
    }
}
class Wait2 implements Runnable{
    private LongTimeWait longTimeWait;
    public Wait2(LongTimeWait longTimeWait){
        this.longTimeWait = longTimeWait;
    }
    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.SECONDS.sleep(2);
                System.out.println("设置标志");
                longTimeWait.setFlag(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LongTimeWait longTimeWait = new LongTimeWait();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Wait1(longTimeWait));
        exec.execute(new Wait2(longTimeWait));
        exec.shutdown();
    }
}
class Wait3 implements Runnable{
    private LongTimeWait longTimeWait;
    public Wait3(LongTimeWait longTimeWait){
        this.longTimeWait = longTimeWait;
    }
    @Override
    public void run() {
        while (true){
            try {
                longTimeWait.waitFlag();
                System.out.println("线程3执行");
                longTimeWait.setFlag(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Wait4 implements Runnable{
    private LongTimeWait longTimeWait;
    public Wait4(LongTimeWait longTimeWait){
        this.longTimeWait = longTimeWait;
    }
    @Override
    public void run() {
        try {
            while (true){
                TimeUnit.SECONDS.sleep(2);
                System.out.println("线程4设置标志");
                longTimeWait.notifyAllFlag();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LongTimeWait longTimeWait = new LongTimeWait();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Wait3(longTimeWait));
        exec.execute(new Wait4(longTimeWait));
        exec.shutdown();
    }
}