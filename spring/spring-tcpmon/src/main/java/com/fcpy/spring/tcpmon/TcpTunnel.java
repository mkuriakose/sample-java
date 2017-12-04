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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fcpy.spring.tcpmon.model.DataModel;

/**
 *  TcpTunnel is a worker class to handle the incoming
 *  connection and relay process.
 */
public class TcpTunnel implements Runnable {
    private static final Log logger = LogFactory.getLog(TcpTunnel.class);

    private int listenPort;
    private String targetHost;
    private int targetPort;
    private boolean isActive;
    private DataModel dataModel;
    
    /**
     * Constructs the TcpTunnel.
     * 
     * @param listenPort Local Listener Port.
     * @param targetHost Target Host Name.
     * @param targetPort Target Listener Port.
     * @param dataModel Data Model.
     */
    public TcpTunnel(int listenPort, String targetHost, int targetPort, DataModel dataModel) {
        this.listenPort = listenPort;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.dataModel = dataModel;
    }

    /**
     * Initializes the TcpTunnel in a new Thread.
     */
    public void init() {
        this.dataModel.setListenPort(this.listenPort);
        this.dataModel.setTargetHost(this.targetHost);
        this.dataModel.setTargetPort(this.targetPort);
        new Thread(this).start();
    }
    
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

    /**
     * Returns true if active, otherwise false.
     * 
     * @return status of the thread.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the status of the thread.
     * 
     * @param isActive status.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void run() {
        ServerSocket serversocket = null;
        try {
            serversocket = new ServerSocket(listenPort);
            if (logger.isInfoEnabled()) {
                logger.info("Listen at: " + listenPort);
            }
            this.isActive = true;
            dataModel.setLastStatus("ready to rock and roll on port " + listenPort);
            do
            {
                Socket sourceSocket = null;
                Socket targetSocket = null;
                InputStream ssis = null;
                OutputStream tsos = null;
                InputStream tsis = null;
                OutputStream ssos = null;
                try {
                    sourceSocket = serversocket.accept();
                    if (logger.isInfoEnabled()) {
                        logger.info("New incoming connection.");
                    }
                    targetSocket = new Socket(targetHost, targetPort);
                    dataModel.setTime(new Date());
                    dataModel.setLastStatus(String.format("Last capture time (%s).", new Date()));
                    ssis = sourceSocket.getInputStream();
                    ssos = sourceSocket.getOutputStream();   
                    tsis = targetSocket.getInputStream();
                    tsos = targetSocket.getOutputStream();
                    Relay rqstRelay = new Relay(ssis, tsos);
                    Relay rspnRelay = new Relay(tsis, ssos);
                    Thread rqstThread = new Thread(rqstRelay);
                    Thread rspnThread = new Thread(rspnRelay);
                    rqstThread.start();
                    rspnThread.start();
                    do {
                        Thread.sleep(500);
                    } while (rqstThread.isAlive() && rspnThread.isAlive());
                    dataModel.setRqstData(rqstRelay.getResult());
                    dataModel.setRspnData(rspnRelay.getResult());
                    dataModel.addDataToList(dataModel.getTime(), rqstRelay.getResult(), rspnRelay.getResult());
                } catch (Exception e) {
                    dataModel.setLastStatus(e.getMessage());
                    e.printStackTrace();
                } finally {
                    IOUtils.closeQuietly(ssis);
                    IOUtils.closeQuietly(ssos);
                    IOUtils.closeQuietly(sourceSocket);
                    IOUtils.closeQuietly(tsis);
                    IOUtils.closeQuietly(tsos);
                    IOUtils.closeQuietly(targetSocket);                    
                }
            } while(this.isActive);
        } catch (IOException e) {
            this.isActive = false;
            dataModel.setLastStatus(e.getMessage());
        } finally {
            IOUtils.closeQuietly(serversocket);    
        }

    }

}