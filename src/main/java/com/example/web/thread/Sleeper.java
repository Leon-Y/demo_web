package com.example.web.thread;


import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/8/16 :7:03
 * @Description:
 */
public class Sleeper {
    static class Sleepers implements Runnable{
        private String name;
        private int duration;
        public Sleepers(int duration, String name){
            this.duration = duration;
        }
        @Override
        public void run() {
            try {
                Thread.currentThread().setName(name);
                TimeUnit.MILLISECONDS.sleep(duration);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"was interrupted."+
                "isinterrupted():"+Thread.currentThread().isInterrupted());
            }
            System.out.println(Thread.currentThread().getName()+"has awakened");
        }
    }
}
class Joiner implements Runnable{
    private Thread sleepers;
    private String name;
    public Joiner(String name,Thread runnable){
        this.name=name;
        this.sleepers=runnable;
    }
    @Override
    public void run() {
        try {
            Thread.currentThread().setName(name);
            sleepers.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
        System.out.println(Thread.currentThread().getName()+" join complete");
    }

    public static void main(String[] args) {
        Thread sleepy =new Thread(new Sleeper.Sleepers(1500,"sleepy")),
                grumpy = new Thread(new Sleeper.Sleepers(1500,"grumpy"));
        Thread dopey = new Thread(new Joiner("dopey",sleepy)),
                doc = new Thread(new Joiner("doc",grumpy));
        sleepy.start();
        grumpy.start();
        dopey.start();
        doc.start();
    }
}
