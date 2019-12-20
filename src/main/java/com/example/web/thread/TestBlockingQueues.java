package com.example.web.thread;

import javafx.scene.effect.Light;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @Auther: 36560
 * @Date: 2019/9/6 :7:05
 * @Description:
 */
class LightOffRunner implements Runnable{
    private BlockingQueue<LightOff> rockets;
    public LightOffRunner(BlockingQueue<LightOff> queue){
        this.rockets = queue;
    }
    public void add(LightOff lightOff){
        try {
            rockets.put(lightOff);
        } catch (InterruptedException e) {
            System.out.println("interrupt during put()");
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                LightOff take = rockets.take();
                take.run();
            }
        } catch (InterruptedException e) {
            System.out.println("waking from take()");
        }
        System.out.println("exiting lightoffrunner");
    }
}


public class TestBlockingQueues {
    static void getKey() throws IOException {
//        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
    static void getKey(String message) throws IOException {
        System.out.println(message);
        getKey();
    }
    static void test(String message , BlockingQueue<LightOff> queue) throws IOException {
        System.out.println(message);
        LightOffRunner lightOffRunner = new LightOffRunner(queue);
        Thread thread = new Thread(lightOffRunner);
        thread.start();
        for (int i=0;i<5;i++){
            lightOffRunner.add(new LightOff(5));
        }
        getKey("press 'enter'("+message+")");
        thread.interrupt();
        System.out.println("finish"+message+"test");
    }

    public static void main(String[] args) throws IOException {
        test("LinkedBlockingQueue",new LinkedBlockingQueue<LightOff>());
        test("ArrayBlokingQueue",new ArrayBlockingQueue<LightOff>(3));
        test("SynchronousQueue",new SynchronousQueue<LightOff>());
    }
}
