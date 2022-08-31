package com.aison.test.design.proxy.jdkProxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 *
 * @author hyb
 * @date 2022/8/31 10:32
 */
public class ProxySport {
    private Object target;

    public ProxySport(Object target) {
        super();
        this.target = target;
    }
    public Object getInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //调用代理方法时 传入目标对象和参数
                Object invoke = method.invoke(target, args);
                return invoke;
            }
        });
    }
}
