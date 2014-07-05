package com.rajesh.jms;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * 
     */
    public void testApp()
    {
//        queueSender();
      topicSender();  

    }
    
    //here just jms queue message sender
    private void queueSender(){
        try {            
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.provider.url", "localhost:1099");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
        
            InitialContext ctx = new InitialContext(props);
            QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
            QueueConnection conn = factory.createQueueConnection();
            QueueSession session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination ) ctx.lookup("queue/rajesh");
            MessageProducer producer = session.createProducer(destination);
            TextMessage msg = session.createTextMessage("hello ,i am rajesh ,i am the first jms queue message.");            
            producer.send(msg);
            session.close();
            conn.close();
        } catch (NamingException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     //here just jms topic message sender
    private void topicSender(){
         try {            
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.provider.url", "localhost:1099");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
        
            InitialContext ctx = new InitialContext(props);
            TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory");
            TopicConnection conn = factory.createTopicConnection();
            TopicSession session = conn.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination ) ctx.lookup("topic/rajeshtopic");
            MessageProducer producer = session.createProducer(destination);
            TextMessage msg = session.createTextMessage("hello ,i am rajesh ,i am the first jms topic message.");            
            producer.send(msg);
            session.close();
            conn.close();
        } catch (NamingException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
