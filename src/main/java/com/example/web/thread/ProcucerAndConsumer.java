package com.example.web.thread;

import com.sun.deploy.util.SyncAccess;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 36560
 * @Date: 2019/9/4 :6:29
 * @Description:
 */
public class ProcucerAndConsumer {
}
class Meal{
    private final int orderNum;
    public Meal(int orderNum){
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal "+ orderNum;
    }
}
class WaitPerson implements Runnable{
    private Restaurant restaurant;
    public WaitPerson(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (restaurant.meal == null){
                        wait();
                    }
                }
                System.out.println("waitPerson get "+restaurant.meal);
                synchronized (restaurant.chef){
                    restaurant.meal =null;
                    restaurant.chef.notifyAll();
                }
                synchronized (restaurant.busyBoy){
                    restaurant.isCleanTime =true;
                    restaurant.busyBoy.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}
class BusyBoy implements  Runnable{
    private Restaurant restaurant;
    public BusyBoy(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (!restaurant.isCleanTime){
                        wait();
                    }
                }
                System.out.println("开始清理桌面");
                synchronized (restaurant.busyBoy){
                    restaurant.isCleanTime = false;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("busyboy 退场");
        }
    }
}
class Chef implements Runnable{
    private int count = 0;
    private Restaurant restaurant;
    public Chef(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (restaurant.meal != null){
                        wait();
                    }
                }
                if (++count == 10){
                    System.out.println("out of food ,closing");
                    restaurant.exec.shutdownNow();
                    return;
                }
                System.out.println("order up");
                synchronized (restaurant.waitPerson){
                    restaurant.meal= new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}
class Restaurant{

    boolean isCleanTime= false;
    BusyBoy busyBoy = new BusyBoy(this);
    Meal meal;
    Chef chef = new Chef(this);
    WaitPerson waitPerson = new WaitPerson(this);
    ExecutorService exec = Executors.newCachedThreadPool();
    public Restaurant(){
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(busyBoy);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}