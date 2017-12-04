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

public class ConnectionRecord {
    Date time;
    byte[] rqstData;
    byte[] rspnData; 
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
        this.rqstData = rqstData;
    }
    public byte[] getRspnData() {
        return rspnData;
    }
    public void setRspnData(byte[] rspnData) {
        this.rspnData = rspnData;
    }
    public String toString() {
        return time.toString();
    }
}
