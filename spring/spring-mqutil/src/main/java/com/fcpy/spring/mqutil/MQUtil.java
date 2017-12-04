/*
 * Copyright (c) 2015, FCPY Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fcpy.spring.mqutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.commons.codec.binary.Hex;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;

import com.ibm.mq.jms.MQQueue;

/**
 * MQUtil provides static methods to access the Message Queue.
 */
public class MQUtil {
    /**
     * Counts the number of message pending in the Message Queue.
     *    
     * @param jmsTemplate JMS Template.
     * @param queue Queue Name.
     * @throws JMSException 
     */
    public static void count(final JmsTemplate jmsTemplate, final ConnectionProperties connectionProperties) throws JMSException {
        Queue queue = new MQQueue(connectionProperties.getTargetQueue());
        int count = jmsTemplate.browse(queue, new BrowserCallback<Integer>() {
            @Override
            public Integer doInJms(final Session session, final QueueBrowser browser) throws JMSException {
                Enumeration<?> enumeration = browser.getEnumeration();
                int counter = 0;
                while (enumeration.hasMoreElements()) {
                    enumeration.nextElement();
                    counter += 1;
                }
                return counter;
            }
        });
        System.out.println(String.format("%s message(s) found.", count));
    }
    
    /**
     * Browses and prints all messages in the Message Queue.
     *    
     * @param jmsTemplate JMS Template.
     * @param queue Queue Name.
     * @throws JMSException 
     */
    public static void browse(final JmsTemplate jmsTemplate, final ConnectionProperties connectionProperties) throws JMSException {
        Queue queue = new MQQueue(connectionProperties.getTargetQueue());
        int count = jmsTemplate.browse(queue, new BrowserCallback<Integer>() {
            @Override
            public Integer doInJms(final Session session, final QueueBrowser browser) throws JMSException {
                Enumeration<?> enumeration = browser.getEnumeration();
                int counter = 0;
                while (enumeration.hasMoreElements()) {
                    Message msg = (Message) enumeration.nextElement();
                    counter += 1;
                    System.out.println(String.format("Found #%s", counter));
                    System.out.println(MQUtil.format(msg));
                    System.out.println("--------------------------------------------------");
                }
                return counter;
            }
        });
        System.out.println(String.format("%s message(s) found.", count));
    }
    
    /**
     * Gets and prints all messages in the Message Queue. After get, the queue is cleaned.
     *    
     * @param jmsTemplate JMS Template.
     * @param queue Queue Name.
     * @throws JMSException 
     */    
    public static void get(final JmsTemplate jmsTemplate, final ConnectionProperties connectionProperties) throws JMSException {
        Queue queue = new MQQueue(connectionProperties.getTargetQueue());
        int counter = 0;
        Message msg = jmsTemplate.receive(queue);
        while (msg != null) {
            counter += 1;
            System.out.println(String.format("Found #%s", counter));
            System.out.println(MQUtil.format(msg));
            System.out.println("--------------------------------------------------");
            msg = jmsTemplate.receive(queue);
        }
        System.out.println(String.format("%s message(s) found.", counter));
    }

    
    /**
     * Cleans the Message Queue.
     *    
     * @param jmsTemplate JMS Template.
     * @param queue Queue Name.
     * @throws JMSException 
     */    
    public static void clean(final JmsTemplate jmsTemplate, final ConnectionProperties connectionProperties) throws JMSException {
        Queue queue = new MQQueue(connectionProperties.getTargetQueue());
        int counter = 0;
        Message msg = jmsTemplate.receive(queue);
        while (msg != null) {
            counter += 1;
            msg = jmsTemplate.receive(queue);
        }
        System.out.println(String.format("%s message(s) removed.", counter));
    }
    
    /**
     * Puts a message to the Message Queue.
     *    
     * @param jmsTemplate JMS Template.
     * @param queue Queue Name.
     * @param data Data to be put to the Queue.
     * @throws JMSException 
     */
    public static void put(final JmsTemplate jmsTemplate, final ConnectionProperties connectionProperties, final String data) throws JMSException {
        Queue queue = new MQQueue(connectionProperties.getTargetQueue());
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(data);
                String replyToQueue = connectionProperties.getReplyToQueue();
                if (StringUtils.hasText(replyToQueue)) {
                    message.setJMSReplyTo(new MQQueue(replyToQueue));
                }
                return message;
            }
        });
        System.out.println("Message is sent.");
    }
    
    /**
     * Formats the Message to String.
     * 
     * @param msg Message.
     * @return Formatted String.
     */
    private static String format(Message msg) {
        try {
            StringBuilder builder = new StringBuilder();
            String msgType;
            String data;
            if (msg instanceof TextMessage) {
                msgType = "jms_text";
                data = ((TextMessage) msg).getText();
            } else if (msg instanceof StreamMessage) {
                msgType = "jms_stream";
                data = msg.getBody(Object.class).toString();
            } else if (msg instanceof MapMessage) {
                msgType = "jms_map";
                data = msg.getBody(Object.class).toString();
            } else if (msg instanceof BytesMessage) {
                msgType = "jms_bytes";
                BytesMessage bytesMsg = ((BytesMessage) msg);
                byte bData[]=new byte[(int) bytesMsg.getBodyLength()];
                bytesMsg.readBytes(bData);
                data = Hex.encodeHexString(bData);
            } else if (msg instanceof ObjectMessage) {
                msgType = "jms_object";
                data = ((ObjectMessage) msg).getObject().toString();
            } else {
                msgType = "jms_none";
                data = msg.getBody(Object.class).toString();
            }
            builder.append("> JMSMessage class      : ").append(msgType).append(System.lineSeparator());
            builder.append("> JMSType               : ").append(msg.getJMSType()).append(System.lineSeparator());
            builder.append("> JMSDeliveryMode       : ").append(msg.getJMSDeliveryMode()).append(System.lineSeparator());
            builder.append("> JMSExpiration         : ").append(msg.getJMSExpiration()).append(System.lineSeparator());
            builder.append("> JMSPriority           : ").append(msg.getJMSPriority()).append(System.lineSeparator());
            builder.append("> JMSMessageID          : ").append(msg.getJMSMessageID()).append(System.lineSeparator());
            builder.append("> JMSTimestamp          : ").append(msg.getJMSTimestamp()).append(System.lineSeparator());
            builder.append("> JMSCorrelationID      : ").append(msg.getJMSCorrelationID()).append(System.lineSeparator());
            builder.append("> JMSDestination        : ").append(msg.getJMSDestination()).append(System.lineSeparator());
            builder.append("> JMSReplyTo            : ").append(msg.getJMSReplyTo()).append(System.lineSeparator());
            builder.append("> JMSRedelivered        : ").append(msg.getJMSRedelivered()).append(System.lineSeparator());
            Enumeration<?> enumPropertyNames = msg.getPropertyNames();
            ArrayList<?> aList = Collections.list(enumPropertyNames);
            Object[] oNames= aList.toArray();
            Arrays.sort(oNames);
            for (Object name: oNames) {
                builder.append(String.format("> %1$-21s : ", name))
                .append(msg.getObjectProperty(name.toString()))
                .append(System.lineSeparator());
            }
            builder.append("> Body                  : ").append(System.lineSeparator());
            builder.append(data).append(System.lineSeparator());
            return builder.toString();
        } catch (JMSException e) {
            e.printStackTrace();
            return "Exception in extracting message properties and body.";
        }
    }
}
