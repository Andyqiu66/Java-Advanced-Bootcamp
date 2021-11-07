package com.javacourse.jvm;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-07 10:39 AM
 * @Description
 * @Since V1.0
 */
public class XlassLoader extends ClassLoader{

    public static void main(String[] args) throws Exception{
        final String className = "Hello";
        final String methodName = "hello";
        ClassLoader classLoader = new XlassLoader();
        Class<?> clazz = classLoader.loadClass(className);
        //创建对象
        Object instance = clazz.getDeclaredConstructor().newInstance();
        //调用实例方法
        Method method = clazz.getMethod(methodName);
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String resourcePath = name.replace(".","/");
        final String suffix=".xlass";
        String resource = resourcePath+suffix;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        try{
            //读出的字节长度，如果不是本地读取的，而是网络读取的，需要进行判断，因为网络读取是分批的
            int length = inputStream.available();
            byte[] bytes = new byte[length];
            //进行读取
            inputStream.read(bytes);
            //进行转换
            byte[] classBytes = decode(bytes);

            return defineClass(name,classBytes,0,classBytes.length);
        }catch (IOException e){
            throw new ClassNotFoundException(name,e);
        }finally {
            close(inputStream);
        }
    }

    //还原处理（255-x）
    private static byte[] decode(byte[] bytes){
        if (null == bytes || bytes.length==0){
            return null;
        }

        byte[] targetBytes = new byte[bytes.length];
        for(int i=0;i<bytes.length;i++){
            targetBytes[i] = (byte) (255-bytes[i]);
        }

        return targetBytes;
    }

    //关闭输入流，避免泄漏
    private static void close(Closeable res){
        if (null!=res){
            try {
                res.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
