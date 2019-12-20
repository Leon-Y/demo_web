package com.example.web.thread;

/**
 * @Auther: 36560
 * @Date: 2019/8/26 :6:58
 * @Description:
 */
public class TestSync implements Runnable{
    int b=100;
    public synchronized void m1() throws InterruptedException {
        b= 1000;
        Thread.sleep(500);
        System.out.println("m1:"+b);
    }
    public synchronized void m2() throws InterruptedException {
        b= 2000;
        Thread.sleep(250);
        System.out.println("m2:"+b);
    }

    @Override
    public void run() {
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestSync testSync = new TestSync();
        Thread thread = new Thread(testSync);
        thread.start();
        testSync.m2();
        System.out.println("sout:"+testSync.b);
    }
}
