package com.example.web.blockingqueue;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 36560
 * @Date: 2019/9/8 :6:
 * @Description:
 */
class Toast {
    public enum Status {DRY, BUTTERED, JAMMED}

    private Status status = Status.DRY;
    private final int id;

    public Toast(int id) {
        this.id = id;
    }
    public void butter(){
        this.status = Status.BUTTERED;
    }
    public void jam(){
        this.status = Status.JAMMED;
    }
    public Status getStatus(){
        return status;
    }
    public int getId(){
        return id;
    }
    public String toString(){
        return "Toast "+id+"; "+status;
    }
}
class ToastQueue extends LinkedBlockingQueue<Toast>{}

class Toaster implements Runnable{
    private ToastQueue toastQueue;
    private int count = 0;
    private Random random = new Random(47);
    public Toaster(ToastQueue toastQueue){
        this.toastQueue = toastQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(500+random.nextInt(1000));
                Toast toast = new Toast(count++);
                System.out.println(toast);
                toastQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("Toast interrupted");
        }

    }
}
class Butterer implements Runnable{
    private ToastQueue dryQueue,butteredQueue;
    public Butterer(ToastQueue dryQueue,ToastQueue butteredQueue){
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = dryQueue.take();
                toast.butter();
                System.out.println(toast);
                butteredQueue.put(toast);
            }
        } catch (Exception e) {
            System.out.println("Butterer interrupted");
        }
        System.out.println("butterer off");
    }
}
class Jammer implements Runnable{
    private ToastQueue butteredQueue,jammedQueue;
    public Jammer(ToastQueue butteredQueue,ToastQueue jammedQueue){
        this.butteredQueue = butteredQueue;
        this.jammedQueue = jammedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = butteredQueue.take();
                toast.jam();
                System.out.println(toast);
                jammedQueue.put(toast);
            }
        } catch (Exception e) {
            System.out.println("Jammed interrupted");
        }
        System.out.println("Jammer off");
    }
}
class Eater implements Runnable{
    private ToastQueue productionQueue;
    private int count = 0;
    public Eater(ToastQueue toastQueue){
        this.productionQueue = toastQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast take = productionQueue.take();
                if (take.getId() != count++ || take.getStatus() != Toast.Status.JAMMED){
                    System.out.println(">>> Error :"+take);
                    System.exit(1);
                }else {
                    System.out.println("Chomp!"+take);
                }
            }
        } catch (Exception e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}
public class ToastOMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue(),
                butteredQueue = new ToastQueue(),
                jammedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue,butteredQueue));
        exec.execute(new Jammer(butteredQueue,jammedQueue));
        exec.execute(new Eater(jammedQueue));
        TimeUnit.SECONDS.sleep(20);
        exec.shutdownNow();
    }
}
