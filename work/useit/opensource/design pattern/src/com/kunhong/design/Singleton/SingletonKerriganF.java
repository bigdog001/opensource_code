package com.kunhong.design.Singleton;

/**
 * 再来看看下面这种我觉得能应对较多场景的单例写法：
 * 实现单例访问Kerrigan的第六次尝试
 * 这种写法仍然使用JVM本身机制保证了线程安全问题；由于SingletonHolder是私有的，除了getInstance()之外没有办法访问它，
 * 因此它是懒汉式的；同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖JDK版本。
 */
public class SingletonKerriganF {
 
    private static class SingletonHolder {
        /**
         * 单例对象实例
         */
        static final SingletonKerriganF INSTANCE = new SingletonKerriganF();
    }
 
    public static SingletonKerriganF getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
