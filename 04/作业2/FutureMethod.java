package com.andy.homework4;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-28 5:15 PM
 * @Description
 * @Since V1.0
 */
public class FutureMethod implements Callable<Integer> {

    public static void main(String[] args) throws Exception{

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new FutureMethod());

        int result = future.get(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
        executorService.shutdown();

    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }


    @Override
    public Integer call() throws Exception {
        return sum();
    }
}
