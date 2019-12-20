package com.example.web.thread;

import sun.awt.windows.ThemeReader;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/9/1 :8:21
 * @Description:
 */
public class Interruption {
}
class NeedsCleanup{
    private final int id ;
    public NeedsCleanup(int id){
        this.id = id;
        System.out.println("NeedsCleanup"+id);
    }
    public void cleanup(){
        System.out.println("cleanup"+id);
    }
}
class Blocked3 implements Runnable{
    private double d = 0.0;
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                NeedsCleanup n1 = new NeedsCleanup(1);
                try {
                    System.out.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try {
                        System.out.println("Caculating");
                        for (int i=0;i<250000000;i++){
                            d=d+(Math.PI+ Math.E)/d;
                        }
                        System.out.println("Finished time_consuming operation");
                    } finally {
                        n2.cleanup();
                    }
                } finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        } catch (InterruptedException e) {
            System.out.println("Exiting via interruptedException");
        }
    }
}
class InterruptingIdiom{
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked3());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        thread.interrupt();
    }
}
