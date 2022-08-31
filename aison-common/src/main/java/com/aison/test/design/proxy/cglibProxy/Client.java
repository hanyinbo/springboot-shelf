package com.aison.test.design.proxy.cglibProxy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

public class Client implements MethodInterceptor {
    public static void main(String[] args) {
        Enhancer en = new Enhancer();
        en.setSuperclass(Phone.class);
        en.setCallback(new Client());
        Phone p = (Phone)en.create();
        String reset = p.reset();

    }

    @Override
    public Object intercept(Object o, Method method,
                            Object[] objects,
                            MethodProxy methodProxy) throws Throwable {
        System.out.println("调用前。。。");
        Object obj = methodProxy.invokeSuper(o,objects);
        System.out.println("调用后。。。");
        return obj;
    }
}
