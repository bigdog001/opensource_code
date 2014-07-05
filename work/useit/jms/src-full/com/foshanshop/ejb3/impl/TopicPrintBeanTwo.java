package com.foshanshop.ejb3.impl;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig =
{
  @ActivationConfigProperty(propertyName="destinationType",
    propertyValue="javax.jms.Topic"),
  @ActivationConfigProperty(propertyName="destination",
    propertyValue="topic/chatTopic")
})
public class TopicPrintBeanTwo implements MessageListener{
	
    public void onMessage(Message msg) {
        try {
            TextMessage tmsg = (TextMessage) msg;
            String content = tmsg.getText();
            System.out.println(this.getClass().getName()+"=="+ content);            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
