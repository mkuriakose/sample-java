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
package com.fcpy.spring.webutil;

/**
 * ConnectionProperties is a class to bind the connection parameters from 
 * the external properties.
 */
public class ConnectionProperties {
    private String host = null;

    private int port = 0;

    /**
     * Gets the host name/ip.
     * 
     * @return Host Name/IP.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Sets the host name/ip.
     * 
     * @param host Host Name/IP.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the port.
     * 
     * @return Port Number.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Sets the port.
     * 
     * @param port Port Number.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Sets the port.
     * 
     * @param port Port Number.
     */
    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }
}
