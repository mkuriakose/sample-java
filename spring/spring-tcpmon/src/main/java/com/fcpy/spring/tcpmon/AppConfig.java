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
package com.fcpy.spring.tcpmon;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import com.fcpy.spring.tcpmon.edt.EdtUtility;
import com.fcpy.spring.tcpmon.edt.OnDemandBean;
import com.fcpy.spring.tcpmon.edt.OnDemandBeanGetter;
import com.fcpy.spring.tcpmon.model.DataModel;
import com.fcpy.spring.tcpmon.ui.GuiConfig;

/**
 * AppConfig extracts the properties from externalized configuration and
 * initializes the beans.
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(ConnectionProperties.class)
@Import(GuiConfig.class)
public class AppConfig {
    private final Log logger = LogFactory.getLog(getClass());

    /** Bean ID for OnDemandBeanGetter */
    public static final String BEAN_ON_DEMAND_BEAN_GETTER = "onDemandBeanGetter";
    /** Bean ID for EdtUtility */
    public static final String BEAN_EDT_UTILITY = "edtUtility";
    /** Bean ID for TcpTunnel */
    public static final String BEAN_TCP_TUNNEL = "tcpTunnel";
    /** Bean ID for DataModel */
    public static final String BEAN_DATA_MODEL = "dataModel";
    /** Bean ID for AppController */
    public static final String BEAN_APP_CONTROLLER = "appController";
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private Environment env;

    @Autowired
    private ConnectionProperties connectionProperties;

    @Autowired 
    private GuiConfig guiConfig;

    @Bean(name = BEAN_ON_DEMAND_BEAN_GETTER)
    public OnDemandBeanGetter onDemandBeanGetter() {
        return new OnDemandBeanGetter() {
            @Override
            public OnDemandBean getBean() {
                return (OnDemandBean) context.getBean(BEAN_APP_CONTROLLER);
            }
        };
    }
    
    @Bean(name = BEAN_EDT_UTILITY)
    public EdtUtility edtUtility() {
        return new EdtUtility(onDemandBeanGetter());
    }

    @Bean(name = BEAN_DATA_MODEL)
    public DataModel dataModel() {
        return new DataModel();
    }
    
    @Bean(name = BEAN_TCP_TUNNEL, initMethod="init")
    public TcpTunnel tcpTunnel() {
        TcpTunnel tcpTunnel = new TcpTunnel(connectionProperties.getListenPort(), 
                connectionProperties.getTargetHost(), 
                connectionProperties.getTargetPort(),
                dataModel());
        return tcpTunnel;
    }
    
    @Bean(name = BEAN_APP_CONTROLLER)
    @Lazy
    public AppController appController() {
        return new AppController(guiConfig.mainView(), dataModel());
    }
    
    @PostConstruct
    public void postConstruct() {
        if (logger.isInfoEnabled()) {
            logger.info("AppConfig is constructed: " + env.toString());
            logger.info("tcpmon.listenPort: " + connectionProperties.getListenPort());
            logger.info("tcpmon.targetHost: " + connectionProperties.getTargetHost());
            logger.info("tcpmon.targetPort: " + connectionProperties.getTargetPort());
        }
    }
}
