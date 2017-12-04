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

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

/**
 * AppConfig extracts the properties from externalized configuration and
 * initializes the beans.
 */
@SpringBootApplication
public class Application {
    private final Log logger = LogFactory.getLog("spring-mqutil");

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate() throws NumberFormatException, JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setReceiveTimeout(1000);
        return template;
    }

    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() throws NumberFormatException, JMSException {
        MQQueueConnectionFactory connFctry = new MQQueueConnectionFactory();
        connFctry.setHostName(connectionProperties.getHostName());
        connFctry.setPort(connectionProperties.getPort());
        connFctry.setChannel(connectionProperties.getChannel());
        connFctry.setQueueManager(connectionProperties.getQueueManager());
        connFctry.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return connFctry;
    }

    @Autowired
    private Environment env;

    @Autowired
    private ConnectionProperties connectionProperties;

    @Autowired
    private Controller controller;

    @PostConstruct
    public void postConstruct() {
        if (logger.isInfoEnabled()) {
            logger.info("AppConfig is constructed: " + env.toString());
            logger.info("mqutil.hostName: " + connectionProperties.getHostName());
            logger.info("mqutil.port: " + connectionProperties.getPort());
            logger.info("mqutil.channel: " + connectionProperties.getChannel());
            logger.info("mqutil.queueManager: " + connectionProperties.getQueueManager());
            logger.info("mqutil.targetQueue: " + connectionProperties.getTargetQueue());
            logger.info("mqutil.replyToQueue: " + connectionProperties.getReplyToQueue());
        }
    }

    /**
     * Main Entry Point.
     * 
     * @param args
     *            Options and Arguments to execute the MQUtil.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Check the profile key.
        if (!System.getProperties().containsKey("spring.profiles.active") && System.getProperties().containsKey("mqutil")) {
            System.setProperty("spring.profiles.active", System.getProperty("mqutil"));
        }
        // Launch the Spring Application.
        ApplicationContext ctx = new SpringApplicationBuilder().showBanner(false).sources(Application.class).run(args);
        Application app = ctx.getBean(Application.class);
        app.controller.execute();
        System.exit(0);
    }
}
