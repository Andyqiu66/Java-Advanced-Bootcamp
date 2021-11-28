package com.andy.homework4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-28 8:13 PM
 * @Description
 * @Since V1.0
 */
public class LockConditionMethod {

    private volatile Integer result = null;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException{

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        LockConditionMethod lockConditionMethod = new LockConditionMethod();

        Thread thread = new Thread(()->{
            lockConditionMethod.sum(30);
        });
        thread.start();

        int result = lockConditionMethod.getResult(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程

    }

    public void sum(int number) {
        lock.lock();
        try{
            result = fibo(number);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public int getResult() throws InterruptedException{
        lock.lock();
        try {
            while (null == result){
                condition.await();
            }
        }finally {
            lock.unlock();
        }

        return result;

    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }




}
