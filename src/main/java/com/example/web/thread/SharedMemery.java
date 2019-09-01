package com.example.web.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: 36560
 * @Date: 2019/8/17 :6:20
 * @Description:
 */
public class SharedMemery {

    static class EvenChecker implements Runnable{
        private IntGenerator intGenerator;
        private final int ident;
        public EvenChecker(IntGenerator intGenerator,int ident){
            this.ident=ident;
            this.intGenerator = intGenerator;
        }
        @Override
        public void run() {
            while (!intGenerator.isCanceled()){
                int next = intGenerator.next();
                if (next%2 !=0){
                    System.out.println(next+"not even");
                    intGenerator.cancel();
                }
                System.out.println(next);
            }
        }
        public static void test(IntGenerator gp,int count){
            ExecutorService executorService = Executors.newCachedThreadPool(new ThreadException.HandlerThreadFactory());
            for (int i=0;i<count;i++){
                executorService.execute(new EvenChecker(gp,count));
            }
            executorService.shutdown();
        }
        public static void test(IntGenerator gp){
            test(gp,10);
        }
    }
    static class EvenGenerator extends IntGenerator{
        private int currentEvenValue=0;
        @Override
        public int next() {
            ++currentEvenValue;
            ++currentEvenValue;
            return currentEvenValue;
        }

        public static void main(String[] args) {
            EvenChecker.test(new EvenGenerator());
        }
    }
    static class SynchronizedEvengenerator extends IntGenerator {
        private int currentEvenValue = 0;
        @Override
        public synchronized int next() {
            ++currentEvenValue;
            Thread.yield();
            ++currentEvenValue;
            return currentEvenValue;
        }

        public static void main(String[] args) {
            EvenChecker.test(new SynchronizedEvengenerator());
        }
    }
    static class MutexEvengenerator extends IntGenerator{
        private int currentEvenvalue=0;
        private Lock lock =new ReentrantLock();
        @Override
        int next() {
            lock.lock();
            try {
                ++currentEvenvalue;
                TimeUnit.MILLISECONDS.sleep(1000);
                ++currentEvenvalue;
                return currentEvenvalue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return 0;
        }

        public static void main(String[] args) {
            EvenChecker.test(new MutexEvengenerator());
            EvenChecker.test(new MutexEvengenerator());
        }
    }
}
abstract class IntGenerator{
    private volatile boolean canceled = false;
    abstract int next();
    public void cancel(){
        canceled = true;
    }
    public boolean isCanceled(){
        return canceled;
    }
}
