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
package com.fcpy.spring.tcpclient;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * AppConfig extracts the properties from externalized configuration and initializes the beans.
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(ConnectionProperties.class)
public class AppConfig {
    /**
     * ID for Connection Factory Bean.
     */
    public static final String BEAN_CONN_FACTORY = "connectionFactory";
    /**
     * ID for Reply Data Bean.
     */
    public static final String BEAN_REPLY_DATA = "replyData";
    
    private final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private Environment env;
    
    @Autowired
    private ConnectionProperties connectionProperties;

    @Bean(name=BEAN_REPLY_DATA)
    public String replyData() throws IOException {
        return FileUtils.readFileToString(new File(connectionProperties.getFile()), "UTF-8");
    }
    
    @PostConstruct
    public void postConstruct() {
        if (logger.isInfoEnabled()) {
            logger.info("AppConfig is constructed: " + env.toString());
            logger.info("tcpclient.hostName: " + connectionProperties.getHost());
            logger.info("tcpclient.port: " + connectionProperties.getPort());
            logger.info("tcpclient.file: " + connectionProperties.getFile());
        }
    }
}
