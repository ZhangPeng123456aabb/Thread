package com.exam;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCAlternate {
    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
               for(int i=1;i<=20;i++){
                   ad.loopA(i);
               }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=20;i++){
                    ad.loopB(i);
                }
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=20;i++){
                    ad.loopC(i);
                    System.out.println("========================================");
                }
            }
        },"C").start();
    }
}
class AlternateDemo{
    private int number=1;//当前正在执行线程的标记
    private Lock lock = new ReentrantLock();
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();
    private Condition condition3=lock.newCondition();
    public void loopA(int totalLoop){
        lock.lock();
        try {
            //判断
            if(number != 1){
                condition1.await();
            }
            //打印
            for(int i=1;i<=1;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }
            //唤醒
            number=2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public void loopB(int totalLoop){
        lock.lock();
        try {
            //判断
            if(number != 2){
                condition2.await();
            }
            //打印
            for(int i=1;i<=1;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }
            //唤醒
            number=3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public void loopC(int totalLoop){
        lock.lock();
        try {
            //判断
            if(number != 3){
                condition3.await();
            }
            //打印
            for(int i=1;i<=1;i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }
            //唤醒
            number=1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}