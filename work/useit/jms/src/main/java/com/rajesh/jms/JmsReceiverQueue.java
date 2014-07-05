package com.rajesh.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDrivenBean;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Hello world!
 *
 */
@MessageDriven(activationConfig={
    @ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue"),
    @ActivationConfigProperty(propertyName="destination", propertyValue="queue/rajesh"),
    @ActivationConfigProperty(propertyName="acknowledgeMode",propertyValue="Auto-acknowledge")
})                                                                                                 
public class JmsReceiverQueue implements MessageListener{
    public void onMessage(Message msg) {
        try {
            TextMessage tm = (TextMessage)msg;
            System.out.println(tm.getText()+" from queue type");
        } catch (JMSException ex) {
            Logger.getLogger(JmsReceiverQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
}
