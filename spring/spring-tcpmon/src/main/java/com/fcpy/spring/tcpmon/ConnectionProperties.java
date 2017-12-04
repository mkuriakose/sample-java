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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ConnectionProperties is a class to bind the connection parameters from 
 * the external properties.
 */
@Component
@ConfigurationProperties(prefix = "tcpmon")
public class ConnectionProperties {

    private int listenPort;

    private String targetHost;

    private int targetPort;

    /**
     * Gets the local listener port number.
     * 
     * @return Local Listener Port Number.
     */
    public int getListenPort() {
        return listenPort;
    }

    /**
     * Sets the local listener port number.
     * 
     * @param port Local Listener Port Number.
     */
    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    /**
     * Gets the target host.
     * 
     * @return Target Host.
     */
    public String getTargetHost() {
        return targetHost;
    }

    /**
     * Sets the target host.
     * 
     * @param targetHost Target Host.
     */
    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    /**
     * Gets the target port.
     * 
     * @return Target Port Number.
     */
    public int getTargetPort() {
        return targetPort;
    }

    /**
     * Sets the target port.
     * 
     * @param targetPort Target Port Number.
     */
    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

}
