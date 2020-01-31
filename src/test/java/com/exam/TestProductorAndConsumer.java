package com.exam;

public class TestProductorAndConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);

        new Thread(productor,"生产者").start();
        new Thread(consumer,"消费者").start();
    }
}
class Clerk{
    private int product = 0;
    //进货
    public synchronized void get(){
        if(product>=10){
            System.out.println("产品已满！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(Thread.currentThread().getName()+":"+ ++product);
            this.notifyAll();
        }
    }
    //卖货
    public synchronized void sale(){
        if(product <=0){
            System.out.println("缺货！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(Thread.currentThread().getName()+":"+ --product);
            this.notifyAll();
        }
    }
}
//生产者
class Productor implements Runnable{
    private Clerk clerk;
    public Productor(Clerk clerk){
        this.clerk=clerk;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.get();
        }
    }
}
//消费者
class Consumer implements Runnable{
    private Clerk clerk;
    public Consumer(Clerk clerk){
        this.clerk=clerk;
    }
    @Override
    public void run() {
        for(int i=0;i<20;i++){
            clerk.sale();
        }
    }
}
