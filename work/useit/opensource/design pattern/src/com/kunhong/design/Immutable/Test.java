package com.kunhong.design.Immutable;

/**
 * ����ģʽ ����ģʽ��������ʽ��һ����������ģʽ����һ����ǿ����ģʽ��
 * 
 * ������ģʽ��
 * һ�����ʵ����״̬�ǲ��ɱ仯�ģ��������������õ�ʵ�����п��ܻ�仯��״̬�������������������ģʽ�Ķ��塣Ҫʵ��������ģʽ��һ���������������������
 * 
 * ��һ������û���κη������޸Ķ����״̬��������Ĺ��캯���Զ����״̬��ʼ��֮�󣬶����״̬�㲻�ٸı䡣
 * 
 * �ڶ������е����Զ�Ӧ����˽�еģ��Է��ͻ��˶���ֱ���޸��κε��ڲ�״̬��
 * 
 * ������������������õĶ�������ǿɱ����Ļ��������跨���������������ķ��ʣ��Է�ֹ����Щ������޸ġ��������Ӧ�þ����ڲ��������ڲ�����ʼ����
 * 
 * ������ģʽ��ȱ���ǣ�
 * һ��������������õ�ʵ�����������ǿɱ���󣬿��ܻ�ͨ������޸ĸ������״̬������һ��������ȱ�㡣�����ڳ�ʼ���ɱ����ʱ���Ƚ���clone��
 * 
 * ����ģʽ ǿ����ģʽ�� һ�����ʵ����״̬����ı䣬ͬʱ���������ʵ��Ҳ���в��ɱ仯��״̬�������������ǿ����ģʽ��Ҫʵ��ǿ����ģʽ��
 * һ���������������������ģʽ��Ҫ����������� �����һ�Ҫ������������֮һ��
 * ��һ�������ǵ������еķ�����Ӧ����final���������������಻�ܹ��û�������ķ�����
 * �ڶ�������౾������final�ģ���ô�����Ͳ����ܻ������࣬�Ӷ�Ҳ�Ͳ������б������޸ĵ����⡣
 * 
 * @author lyq
 * 
 */
public class Test {
	public static void main(String[] args) {
		int state = 0;
		User u = new User();
		Integer age = 100;
		u.setName("yes");
		WeakImmutable weak = new WeakImmutable(state, u, age);
		System.out.println("ԭʼֵ��" + weak.getState() + ","
				+ weak.getUser().getName() + "," + weak.getAge());
		// �޸����ú�
		state = 5;
		// User�����ǿɱ�������ã�������Ӱ��
		u.setName("no");
		age = 200;
		System.out.println("�޸����ú�" + weak.getState() + ","
				+ weak.getUser().getName() + "," + weak.getAge());
	}
}