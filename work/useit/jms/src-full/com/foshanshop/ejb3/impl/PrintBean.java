package com.foshanshop.ejb3.impl;

import java.io.ByteArrayOutputStream;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.foshanshop.ejb3.bean.Man;

@MessageDriven(activationConfig =
{
  @ActivationConfigProperty(propertyName="destinationType",
    propertyValue="javax.jms.Queue"),
  @ActivationConfigProperty(propertyName="destination",
    propertyValue="queue/foshanshop"),
  @ActivationConfigProperty(propertyName="acknowledgeMode", 
    propertyValue="Auto-acknowledge")
})
public class PrintBean implements MessageListener {

    public void onMessage(Message msg) {
        try {            
            if (msg instanceof TextMessage) {
                TextMessage tmsg = (TextMessage) msg;
                String content = tmsg.getText();
                System.out.println(content);
            }else if(msg instanceof ObjectMessage){
                ObjectMessage omsg = (ObjectMessage) msg;
                Man man = (Man) omsg.getObject();
                String content = man.getName()+ " ¼Ò×¡"+ man.getAddress();
                System.out.println(content);
            }else if(msg instanceof MapMessage){
                MapMessage map = (MapMessage) msg;
                String content = map.getString("no1");
                System.out.println(content);
            }else if(msg instanceof BytesMessage){
                BytesMessage bmsg = (BytesMessage) msg;
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[256]; 
                int length = 0; 
                while ((length = bmsg.readBytes(buffer)) != -1) { 
                      byteStream.write(buffer, 0, length); 
                }
                String content = new String(byteStream.toByteArray());
                byteStream.close(); 
                System.out.println(content);
            }else if(msg instanceof StreamMessage){
                StreamMessage smsg = (StreamMessage) msg;
                String content = smsg.readString();
                System.out.println(content);
            }
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
