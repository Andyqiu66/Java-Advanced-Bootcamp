package com.andy.homework4;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-28 5:57 PM
 * @Description
 * @Since V1.0
 */
public class SynchronizedMethod {

    private volatile Integer result = null;

    public static void main(String[] args) throws InterruptedException{
        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        SynchronizedMethod method = new SynchronizedMethod();
        Thread thread = new Thread(()->{
            method.sum(30);
        });
        thread.start();

        int result = method.getResult(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    synchronized public int getResult() throws InterruptedException{
        while (result == null){
            wait();
        }

        return result;
    }

   synchronized public void sum(int number) {
        result = fibo(number);
        //计算结束，通知所有等待的线程
        notifyAll();
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
