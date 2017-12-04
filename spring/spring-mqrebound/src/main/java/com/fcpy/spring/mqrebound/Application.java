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
package com.fcpy.spring.mqrebound;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

/**
 * AppConfig extracts the properties from externalized configuration and initializes the beans.
 */
@SpringBootApplication
public class Application {
    private final Log logger = LogFactory.getLog("spring-mqrebound");

    /**
     * ID for JMS Template Bean.
     */
    public static final String BEAN_JMS_TEMPLATE = "jmsTemplate";
    /**
     * ID for Connection Factory Bean.
     */
    public static final String BEAN_CONN_FACTORY = "connectionFactory";
    /**
     * ID for Target Queue Bean.
     */
    public static final String BEAN_TARGET_QUEUE = "targetQueue";
    /**
     * ID for Reply Data Bean.
     */
    public static final String BEAN_REPLY_DATA = "replyData";
    /**
     * ID for Rebound Flow Bean.
     */
    public static final String BEAN_REBOUND_FLOW = "reboundFlow";
    /**
     * ID for Rebound Service Bean.
     */    
    public static final String BEAN_REBOUND_SERVICE = "reboundService";
    
    @Autowired
    private Controller controller;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private ConnectionProperties connectionProperties;
    
    
    @Bean(name=BEAN_JMS_TEMPLATE)
    public JmsTemplate jmsTemplate() throws NumberFormatException, JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setReceiveTimeout(1000);
        return template;
    }
    
    @Bean(name=BEAN_CONN_FACTORY)
    public ConnectionFactory connectionFactory() throws NumberFormatException, JMSException {
        MQQueueConnectionFactory connFctry = new MQQueueConnectionFactory();
        connFctry.setHostName(connectionProperties.getHostName());
        connFctry.setPort(connectionProperties.getPort());
        connFctry.setChannel(connectionProperties.getChannel());
        connFctry.setQueueManager(connectionProperties.getQueueManager());
        connFctry.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return connFctry;
    }
    
    @Bean(name=BEAN_TARGET_QUEUE)
    public Queue targetQueue() throws JMSException {
         return new MQQueue(connectionProperties.getTargetQueue());
    }
    
    @Bean(name=BEAN_REPLY_DATA)
    public String replyData() throws IOException {
        return FileUtils.readFileToString(new File(connectionProperties.getFile()), "UTF-8");
    }
    
    @Bean(name=BEAN_REBOUND_SERVICE)
    public ReboundService reboundService() throws IOException {
        ReboundService rbService = new ReboundService();
        rbService.setReplyData(this.replyData());
        return rbService;
    }
    
    @Bean(name=BEAN_REBOUND_FLOW)
    public IntegrationFlow reboundFlow() throws NumberFormatException, JMSException {
         return IntegrationFlows.from(Jms.inboundGateway(this.connectionFactory())
                 .extractRequestPayload(true)
                 .destination(this.targetQueue())
                 ).handle(BEAN_REBOUND_SERVICE, "execute").get();
    }
    
    @PostConstruct
    public void postConstruct() {
        if (logger.isInfoEnabled()) {
            logger.info("AppConfig is constructed: " + env.toString());
            logger.info("mqrebound.hostName: " + connectionProperties.getHostName());
            logger.info("mqrebound.port: " + connectionProperties.getPort());
            logger.info("mqrebound.channel: " + connectionProperties.getChannel());
            logger.info("mqrebound.queueManager: " + connectionProperties.getQueueManager());
            logger.info("mqrebound.targetQueue: " + connectionProperties.getTargetQueue());
            logger.info("mqrebound.file: " + connectionProperties.getFile());
        }
    }
    
    /**
     * Entry Point.
     * 
     * @param args Options and Arguments to execute the MQUtil.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // Check the profile key.
        if (!System.getProperties().containsKey("spring.profiles.active") 
                && System.getProperties().containsKey("mqrebound")) {
            System.setProperty("spring.profiles.active", System.getProperty("mqrebound"));
        }
        
        new SpringApplicationBuilder().showBanner(false).sources(Application.class).run(args);
    }
}
