package com.foshanshop.ejb3.impl;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import com.foshanshop.ejb3.TSender;
/**
 * ∑¢ÀÕTopicœ˚œ¢
 * @author lihuoming
 *
 */
@Stateless
@Remote (TSender.class)
public class TSenderBean implements TSender {
    @Resource(mappedName="TopicConnectionFactory") private TopicConnectionFactory factory;
    @Resource(mappedName="topic/chatTopic") private Topic destination;
    
	public void send(String msg) {
		TopicConnection conn = null;
		TopicSession session = null;
        try {            
            conn = factory.createTopicConnection();
            session = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            TextMessage text = session.createTextMessage(msg);
            producer.send(text);
        }catch (Exception e){
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
