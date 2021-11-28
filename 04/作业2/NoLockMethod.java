package com.andy.homework4;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-28 8:22 PM
 * @Description
 * @Since V1.0
 */
public class NoLockMethod {

    private volatile Integer result = null;

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        NoLockMethod noLockMethod = new NoLockMethod();

        Thread thread = new Thread(()->{
            noLockMethod.sum(30);
        });
        thread.start();

        int result = noLockMethod.getResult(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程

    }

    public void sum(int number) {
        result = fibo(number);
    }

    public int getResult(){
        while (null == result){

        }
        return result;
    }


    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }




}
