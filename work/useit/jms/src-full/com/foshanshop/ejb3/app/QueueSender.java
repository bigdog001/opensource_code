package com.foshanshop.ejb3.app;

import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.foshanshop.ejb3.bean.Man;
/**
 * 发送Queue消息
 * @author lihuoming
 *
 */
public class QueueSender {
    public static void main(String[] args) {
        QueueConnection conn = null;
        QueueSession session = null;
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            props.setProperty(Context.PROVIDER_URL, "localhost:1099");
            props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            InitialContext ctx = new InitialContext(props);
            
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
            conn = factory.createQueueConnection();
            session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Destination destination = (Queue) ctx.lookup("queue/foshanshop");
            MessageProducer producer = session.createProducer(destination);
            
            //发送文本
            TextMessage msg = session.createTextMessage("佛山人您好，这是我的第一个消息驱动Bean");
            producer.send(msg);
            
            //发送Ojbect(对象必须实现序列化,否则等着出错吧)
            producer.send(session.createObjectMessage(new Man("大美女", "北京朝阳区和平里一号")));
            
            //发送MapMessage
            MapMessage mapmsg = session.createMapMessage();
            mapmsg.setObject("no1", "北京和平里一号");
            producer.send(mapmsg);
            
            //发送BytesMessage
            BytesMessage bmsg = session.createBytesMessage();
            bmsg.writeBytes("我是一个兵,来自老百姓".getBytes());
            producer.send(bmsg);            
            
            //发送StreamMessage
            StreamMessage smsg = session.createStreamMessage();
            smsg.writeString("巴巴运动网,http://www.babasport.com");
            producer.send(smsg);
            
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
            try {
                session.close ();
                conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
