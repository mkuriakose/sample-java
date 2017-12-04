/*
 * Copyright (c) 2015, FCPY Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fcpy.example.jaxwsboot.web.boot;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class MyWsClientHandler implements SOAPHandler<SOAPMessageContext> {
	private static final Logger LOGGER = Logger.getLogger("com.fcpy.example.jaxwsboot.web.boot.MyClientWsHandler");
	private static final String NAMESPACE = "http://service.jaxwsboot.example.fcpy.com/";

	@Override
	public void close(MessageContext context) {
		LOGGER.info("-----MyClientWsHandler.close is triggered and completed.");
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		LOGGER.info("-----MyClientWsHandler.handleFault is triggered and completed.");
		return false;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		LOGGER.info("-----MyClientWsHandler.handleMessage is triggered.");
		Boolean isOutbound = ((Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
		if (isOutbound) {
			try {
				SOAPMessage soapMsg = context.getMessage();
				SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnv.getHeader();
				if (soapHeader == null){
	            	soapHeader = soapEnv.addHeader();
	            }
				QName sysInfoQName = new QName(NAMESPACE, "sysInfo");
	            SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(sysInfoQName);
	            addChildElement(soapHeaderElement, NAMESPACE, "ipAddress", getIPAddress());
	            addChildElement(soapHeaderElement, NAMESPACE, "javaVendor", System.getProperty("java.vendor"));
	            addChildElement(soapHeaderElement, NAMESPACE, "javaVersion", System.getProperty("java.version"));
	            addChildElement(soapHeaderElement, NAMESPACE, "osName", System.getProperty("os.name"));
	            addChildElement(soapHeaderElement, NAMESPACE, "osVersion", System.getProperty("os.version"));
	            soapMsg.saveChanges();
			} catch (SOAPException e) {
				e.printStackTrace();
			}						
		}
		LOGGER.info("-----MyClientWsHandler.handleMessage is executed.");
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	private void addChildElement(SOAPHeaderElement soapHeaderElement, String ns, String key, String value) throws SOAPException {
		QName qName = new QName(ns, key);
        SOAPElement childElement = soapHeaderElement.addChildElement(qName);
        childElement.addTextNode(value);
	}
	
	private String getIPAddress(){
		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
		} catch (UnknownHostException e) {
			return e.getMessage();
		}
	}
}
