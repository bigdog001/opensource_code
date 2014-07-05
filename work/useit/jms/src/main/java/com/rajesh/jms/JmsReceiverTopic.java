/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajesh.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author bigdog
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/rajeshtopic")
})
public class JmsReceiverTopic implements MessageListener {

    public void onMessage(Message msg) {
        try {
            TextMessage tm = (TextMessage) msg;
            System.out.println(tm.getText() + " from topic type");
        } catch (JMSException ex) {
            Logger.getLogger(JmsReceiverQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
