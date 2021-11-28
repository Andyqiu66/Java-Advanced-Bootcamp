package com.andy.homework4;

import java.util.concurrent.CountDownLatch;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-28 4:06 PM
 * @Description
 * @Since V1.0
 */
public class CountDownLatchMethod {

    private volatile int result;
    private CountDownLatch countDownLatch;

    public static void main(String[] args) throws InterruptedException{

        long start=System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatchMethod method = new CountDownLatchMethod();

        method.setCountDownLatch(countDownLatch);
        Thread thread = new Thread(()->{
             method.sum(30);
        });
        thread.start();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        int result = method.getResult(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程

    }


    public void setCountDownLatch(CountDownLatch latch){
        this.countDownLatch = latch;
    }

    public void sum(int number) {
        result=fibo(number);
        //计算完成
        countDownLatch.countDown();
    }

    public int getResult() throws InterruptedException{
        countDownLatch.await();
        return result;
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
