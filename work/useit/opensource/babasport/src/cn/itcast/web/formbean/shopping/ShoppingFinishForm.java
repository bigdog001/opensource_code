package cn.itcast.web.formbean.shopping;

import cn.itcast.bean.book.PaymentWay;
import cn.itcast.web.formbean.BaseForm;

public class ShoppingFinishForm extends BaseForm {
	private PaymentWay paymentway;
	private String orderid;
	private Float payablefee;
	public PaymentWay getPaymentway() {
		return paymentway;
	}
	public void setPaymentway(PaymentWay paymentway) {
		this.paymentway = paymentway;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Float getPayablefee() {
		return payablefee;
	}
	public void setPayablefee(Float payablefee) {
		this.payablefee = payablefee;
	}
	
}
