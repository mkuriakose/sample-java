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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ConnectionProperties is a class to bind the connection parameters from 
 * the external properties.
 */
@Configuration
@ConfigurationProperties(prefix = "mqutil")
public class ConnectionProperties {
    private String hostName = null;

    private int port = 0;

    private String channel = null;

    private String queueManager = null;

    private String targetQueue = null;

    private String replyToQueue = null;

    /**
     * Gets the host name.
     * 
     * @return Host Name.
     */
    public String getHostName() {
        return this.hostName;
    }

    /**
     * Sets the host name.
     * 
     * @param hostName Host Name.
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
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
     * Gets the channel.
     * 
     * @return Channel Name.
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * Sets the channel.
     * 
     * @param channel Channel Name.
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Gets the queue manager.
     * 
     * @return Queue Manager Name.
     */
    public String getQueueManager() {
        return this.queueManager;
    }

    /**
     * Sets the queue manager.
     * 
     * @param queueManager Queue Manager Name.
     */
    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }

    /**
     * Gets the target queue.
     * 
     * @return Target Queue Name.
     */
    public String getTargetQueue() {
        return this.targetQueue;
    }

    /**
     * Sets the target queue.
     * 
     * @param targetQueue Target Queue Name.
     */
    public void setTargetQueue(String targetQueue) {
        this.targetQueue = targetQueue;
    }

    /**
     * Gets the replyTo queue.
     * 
     * @return replyTo Queue Name.
     */
    public String getReplyToQueue() {
        return this.replyToQueue;
    }

    /**
     * Sets the replyTo queue.
     * 
     * @param replyToQueue replyTo Queue Name.
     */
    public void setReplyToQueue(String replyToQueue) {
        this.replyToQueue = replyToQueue;
    }

}