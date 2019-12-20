package com.example.web.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 36560
 * @Date: 2019/8/21 :6:33
 * @Description:
 */
public class LockTest {
}
class AttemptLocking{
    private Lock lock=new ReentrantLock();
    public void untimed(){
        boolean capture = lock.tryLock();
        try {
            System.out.println("try lock:"+capture);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (capture){lock.unlock();}
        }
    }
    public void timed(){
        boolean capture = false;
        try {
            capture = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("try timed lock:"+capture);
        } finally {
            if (capture){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final AttemptLocking attemptLocking= new AttemptLocking();
        attemptLocking.untimed();
        attemptLocking.timed();

        for (int i=0;i<10;i++) {
            new Thread(){
                {
                    setDaemon(true);
                }

                @Override
                public void run() {
                    attemptLocking.lock.lock();
                    System.out.println("acquired");
                }
            }.start();
        }
        Thread.yield();
        attemptLocking.untimed();
        attemptLocking.timed();
    }
}
