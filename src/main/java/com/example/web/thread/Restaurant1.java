package com.example.web.thread;

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
public class Restaurant1 {
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();
    boolean isCleanTime= false;
    BusyBoy1 busyBoy = new BusyBoy1(this);
    Meal meal;
    Chef1 chef = new Chef1(this);
    WaitPerson1 waitPerson = new WaitPerson1(this);
    ExecutorService exec = Executors.newCachedThreadPool();
    public Restaurant1(){
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(busyBoy);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}

class Meal1{
    private final int orderNum;
    public Meal1(int orderNum){
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal "+ orderNum;
    }
}

class WaitPerson1 implements Runnable{
    private Restaurant1 restaurant;
    public WaitPerson1(Restaurant1 restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                restaurant.lock.lock();
                try {
                    while (restaurant.meal == null){
                        restaurant.condition.await();
                    }
                    System.out.println("waitPerson get "+restaurant.meal);
                    restaurant.meal =null;
                    restaurant.isCleanTime =true;
                    restaurant.condition.signalAll();
                } finally {
                    restaurant.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class BusyBoy1 implements  Runnable{
    private Restaurant1 restaurant;
    public BusyBoy1(Restaurant1 restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                restaurant.lock.lock();
                try {
                    while (!restaurant.isCleanTime){
                        restaurant.condition.await();
                    }
                    System.out.println("开始清理桌面");
                    restaurant.isCleanTime = false;
                } finally {
                    restaurant.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("busyboy 退场");
        }
    }
}

class Chef1 implements Runnable{
    private int count = 0;
    private Restaurant1 restaurant;
    public Chef1(Restaurant1 restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                restaurant.lock.lock();
                try {
                    while (restaurant.meal != null){
                        restaurant.condition.await();
                    }
                    if (++count == 10){
                        System.out.println("out of food ,closing");
                        restaurant.exec.shutdownNow();
                        return;
                    }
                    System.out.println("order up");
                    restaurant.meal= new Meal(count);
                    restaurant.condition.signalAll();
                    TimeUnit.SECONDS.sleep(1);
                } finally {
                    restaurant.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}
