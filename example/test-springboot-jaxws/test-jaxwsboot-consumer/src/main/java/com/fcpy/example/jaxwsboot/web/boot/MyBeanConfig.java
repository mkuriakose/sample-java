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
package com.fcpy.example.jaxwsboot.web.boot;

import java.net.URL;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.xml.ws.Service;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

import com.fcpy.example.jaxwsboot.client.calculator.CalculatorDelegate;
import com.fcpy.example.jaxwsboot.client.converter.ConverterDelegate;

@Configuration
public class MyBeanConfig {
	private static final Logger LOGGER = Logger.getLogger("com.fcpy.example.jaxwsboot.web.boot.MyBeanConfig");

	public MyBeanConfig() {
		LOGGER.info("-----MyBeanConfig is constructed.");
	}

	private Service lookup(String jndiName) {
		JndiTemplate template = new JndiTemplate();
		try {
			return (Service) template.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isStandaloneMode = (System.getProperty("MyWebApp.standalone") != null && System.getProperty("MyWebApp.standalone").equalsIgnoreCase("true") ? true : false);

	@Bean(name = "calculatorServiceFactory")
	public JaxWsPortProxyFactoryBean calculatorServiceFactoryBean() throws Exception {
		LOGGER.info("-----MyBeanConfig.calculatorService initialization is triggered.");
	    JaxWsPortProxyFactoryBean jwppfb = new JaxWsPortProxyFactoryBean();
	    jwppfb.setServiceInterface(CalculatorDelegate.class);
	    jwppfb.setPortName("CalculatorPort");
	    if (isStandaloneMode) {
	    	jwppfb.setServiceName("CalculatorService");
		    jwppfb.setWsdlDocumentUrl(new URL("http://localhost:9080/test-jaxwsboot-provider/CalculatorService?wsdl"));
		    jwppfb.setEndpointAddress("http://localhost:9080/test-jaxwsboot-provider/CalculatorService");
	    } else {
	    	jwppfb.setJaxWsService(lookup("java:comp/env/service/CalculatorClient")) ;
	    }
	    jwppfb.afterPropertiesSet();
		LOGGER.info("-----MyBeanConfig.calculatorService initialization is completed.");
	    return jwppfb;
	}

	@Bean(name = "converterServiceFactory")
	public JaxWsPortProxyFactoryBean converterServiceFactoryBean() throws Exception {
		LOGGER.info("-----MyBeanConfig.converterService initialization is triggered.");
	    JaxWsPortProxyFactoryBean jwppfb = new JaxWsPortProxyFactoryBean();
	    jwppfb.setServiceInterface(ConverterDelegate.class);
	    jwppfb.setPortName("ConverterPort");
	    if (isStandaloneMode) {
		    jwppfb.setServiceName("ConverterService");
		    jwppfb.setWsdlDocumentUrl(new URL("http://localhost:9080/test-jaxwsboot-provider/ConverterService?wsdl"));
		    jwppfb.setEndpointAddress("http://localhost:9080/test-jaxwsboot-provider/ConverterService");
	    } else {
		    jwppfb.setJaxWsService(lookup("java:comp/env/service/ConverterClient")) ;
	    }
	    jwppfb.afterPropertiesSet();
		LOGGER.info("-----MyBeanConfig.converterService initialization is completed.");
	    return jwppfb;
	}

	@Bean
	public SOAPHandler<SOAPMessageContext> wsClientHandler() {
		LOGGER.info("-----MyBeanConfig.wsClientHandler initialization is triggered & completed.");
		return new MyWsClientHandler();
	}

    public Object calculatorServiceFactory() {
        try {
            return calculatorServiceFactoryBean().getObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	@Bean(name = "calculatorServiceBindingFactory")
	public MethodInvokingFactoryBean calculatorServiceBindingFactoryBean() throws Exception {
		LOGGER.info("-----MyBeanConfig.calculatorServiceBinding initialization is triggered.");
		MethodInvokingFactoryBean mifBean = new MethodInvokingFactoryBean();
		mifBean.setTargetObject(calculatorServiceFactory());
		mifBean.setTargetMethod("getBinding");
		LOGGER.info("-----MyBeanConfig.calculatorServiceBinding initialization is completed.");
		return mifBean;
	}

    public Object calculatorServiceBindingFactory() {
        try {
            return calculatorServiceBindingFactoryBean().getObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

	@Bean
	public MethodInvokingFactoryBean calculatorServiceHandler() throws Exception {
		LOGGER.info("-----MyBeanConfig.calculatorServiceHandler initialization is triggered.");
		MethodInvokingFactoryBean mifBean = new MethodInvokingFactoryBean();
		mifBean.setTargetObject(calculatorServiceBindingFactory());
		mifBean.setTargetMethod("setHandlerChain");
		mifBean.setArguments(new Object[]{wsClientHandler()});
		LOGGER.info("-----MyBeanConfig.calculatorServiceHandler initialization is completed.");
		return mifBean;
	}
}
