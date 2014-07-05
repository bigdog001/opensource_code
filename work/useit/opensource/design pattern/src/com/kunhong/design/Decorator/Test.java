package com.kunhong.design.Decorator;

/**
 * 装饰模式
 * 装饰模式又名包装(Wrapper)模式，装饰模式以对客户透明的方式动态的给一个对象附加上更多的责任。装饰模式在不必改变原类文件和使用继承的情况下，
 * 动态的扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。 3、装饰模式的角色和特点？ 角色构成：
 * -抽象构件角色：给出一个抽象接口，以规范准备接收附加责任的对象。 -具体构件角色：定义一个将要接收附加责任的类。
 * -装饰角色：持有一个构件对象的引用，并定义一个与抽象构件接口一致的接口。 -具体装饰角色：负责给构件对象“贴上”附加的责任。
 * 
 * 装饰模式的特点： 1.装饰对象和真实对象有相同的接口。这样客户端对象就可以以和真实对象相同的方式和装饰 对象交互。
 * 2.装饰对象包含一个真实对象的引用(reference)。 3.装饰对象接收所有来自客户端的请求。它把这些请求转发给真实对象。
 * 4.装饰对象可以在转发这些请求以前或者以后增加一些附加功能
 * 。这样就确保了在运行时，不用修改给定对象的结构就可以在外部增加附加的功能。而在面向对象设计中，通常是通过继承来实现对给定类的功能扩展。
 * 
 * @author lyq
 * 
 */
public class Test {
	public static void main(String[] args) {
		Component component = new ConcreteDecorator2(new ConcreteDecorator1(
				new ConcreteComponent()));

		component.doSomething();
		
		//输出结果： 
		//功能A 
		//功能B 
		//功能C 
	}

}
