package com.example.web.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 36560
 * @Date: 2019/8/23 :6:16
 * @Description:
 */
class Pair{
    private int x,y;
    public Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Pair(){
        this(0,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void incrementX(){x++;}
    public void incrementY(){y++;}
    public class PairValuesNotEqualException extends RuntimeException{
        public PairValuesNotEqualException(){
            super("pair not equal:"+Pair.this);
        }
    }
    public void checkState(){
        if (x!=y){
            throw  new PairValuesNotEqualException();
        }
    }
    public String toString(){
        return "x: "+x+",y: "+y;
    }
}
abstract class PairManager{
    AtomicInteger atomicInteger = new AtomicInteger();
    protected Pair pair = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
    public synchronized Pair getPair(){
        return new Pair(pair.getX(),pair.getY());
    }
    protected  void storage(Pair pair){
        storage.add(pair);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("意外中断");
        }
    }
    public abstract void increment();
}
class PairManager1 extends PairManager{

    @Override
    public synchronized void increment() {
        pair.incrementX();
        pair.incrementY();
        storage(pair);
    }
}
class PairManager2 extends PairManager{
    @Override
    public void increment() {
        Pair temp;
        synchronized (this){
            pair.incrementY();
            pair.incrementX();
            temp=getPair();
        }
        storage(temp);
    }
}
class PairMainpulator implements Runnable{
    private PairManager pm;
    public PairMainpulator(PairManager pairManager){
        this.pm=pairManager;
    }

    @Override
    public void run() {
        while (true){
            pm.increment();
        }
    }
    public String toString(){
        return " pair: "+pm.getPair()+" checkCounter: "+pm.atomicInteger.get();
    }
}
class PairChecker implements Runnable{
    private PairManager pm;
    public PairChecker(PairManager pairManager){
        this.pm=pairManager;
    }
    @Override
    public void run() {
        while (true){
            pm.atomicInteger.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}
public class CriticalSection {
    static void testApproaches(PairManager pairManager1,PairManager pairManager2){
        ExecutorService executorService = Executors.newCachedThreadPool();
        PairMainpulator
                pm1 = new PairMainpulator(pairManager1),
                pm2 = new PairMainpulator(pairManager2);
        PairChecker
                pCheck1 = new PairChecker(pairManager1),
                pCheck2 = new PairChecker(pairManager2);
        executorService.execute(pm1);
        executorService.execute(pm2);
        executorService.execute(pCheck1);
        executorService.execute(pCheck2);
        try {
            TimeUnit.MILLISECONDS.sleep(600 );
        } catch (InterruptedException e) {
            System.out.println("sleep interupt");
        }
        System.out.println("pm1:"+pm1+"\npm2:"+pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager
                pm1 = new PairManager1(),
                pm2 = new PairManager2();
        testApproaches(pm1,pm2);
    }
}
class syncTest{
    int count=0;
    public void test1(){
        synchronized (this){
            for (int i=0;i<5;i++) {
                System.out.println("test1["+i+"]:"+count++);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void test2(){
        synchronized (this){
            for (int i=0;i<5;i++) {
                System.out.println("test2["+i+"]:"+count++);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void test3(){
        synchronized (this){
            for (int i=0;i<5;i++) {
                System.out.println("test3["+i+"]:"+count++);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        syncTest syncTest1 = new syncTest();
        syncTest syncTest2 = new syncTest();
        syncTest syncTest3 = new syncTest();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    syncTest1.test1();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    syncTest2.test2();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    syncTest3.test3();
                }
            }
        });
    }
}
class lockTest{
    private Lock lock = new ReentrantLock();
    int count = 0;
    public void test1(){
        lock.lock();
        try {
            fun("test1");
        } finally {
            lock.unlock();
        }
    }
    public void test2(){
        lock.lock();
        try {
            fun("test2");
        } finally {
            lock.unlock();
        }
    }
    public void test3(){
        lock.lock();
        try {
            fun("test3");
        } finally {
            lock.unlock();
        }
    }
    public void fun(String name){
        for(int i=0;i<5;i++){
            System.out.println(name+"["+i+"]"+count++);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        lockTest lockTest = new lockTest();
        lockTest lockTest1 = new lockTest();
        lockTest lockTest2 = new lockTest();
        lockTest lockTest3 = new lockTest();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lockTest.test1();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lockTest.test2();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lockTest.test3();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lockTest1.test1();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lockTest2.test2();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    lockTest3.test3();
                }
            }
        });
    }
}
