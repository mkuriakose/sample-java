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
package com.fcpy.spring.tcpmon.model;

import java.util.Date;
import java.util.Vector;

/**
 * This is a model to hold the view data and fire the event.
 */
public class DataModel extends AbstractModel {
    public static final String PROP_NEW_DATA_MODEL = "DataModel.new";
    public static final String PROP_SET_LAST_STATUS = "DataModel.setLastStatus";
    public static final String PROP_SET_RQST_DATA = "DataModel.setRqstData";
    public static final String PROP_SET_RSPN_DATA = "DataModel.setRspnData";
    public static final String PROP_ADD_DATA_TO_LIST = "DataModel.addDataToList";
    private Date time = null;
    private byte[] rqstData = null;
    private byte[] rspnData = null;
    private String lastStatus;
    private int listenPort = 0;
    private String targetHost = "localhost";
    private int targetPort = 0;
    private Vector<ConnectionRecord> recordList = new Vector<ConnectionRecord>();

    public DataModel() {
        firePropertyChange(PROP_NEW_DATA_MODEL, null, this);
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public byte[] getRqstData() {
        return rqstData;
    }
    public void setRqstData(byte[] rqstData) {
        byte[] oldValue = this.rqstData;
        this.rqstData = rqstData;
        firePropertyChange(PROP_SET_RQST_DATA, oldValue, this.rqstData);
    }
    public byte[] getRspnData() {
        return rspnData;
    }
    public void setRspnData(byte[] rspnData) {
        byte[] oldValue = this.rspnData;
        this.rspnData = rspnData;
        firePropertyChange(PROP_SET_RSPN_DATA, oldValue, this.rspnData);
    }
    public String getLastStatus() {
        return lastStatus;
    }
    public void setLastStatus(String lastStatus) {
        String oldValue = this.lastStatus;
        this.lastStatus = lastStatus;
        firePropertyChange(PROP_SET_LAST_STATUS, oldValue, this.lastStatus);
    }
    public int getListenPort() {
        return listenPort;
    }
    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }
    public String getTargetHost() {
        return targetHost;
    }
    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }
    public int getTargetPort() {
        return targetPort;
    }
    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }
    public void addDataToList(Date time, byte[] rqstData, byte[] rspnData) {
        ConnectionRecord connrec = new ConnectionRecord();
        connrec.setTime(time);
        connrec.setRqstData(rqstData);
        connrec.setRspnData(rspnData);
        recordList.add(connrec);
        firePropertyChange(PROP_ADD_DATA_TO_LIST, null, this.recordList.toArray());
    }
    public void cleanList() {
        recordList.clear();
        firePropertyChange(PROP_ADD_DATA_TO_LIST, null, this.recordList.toArray());
    }
}
