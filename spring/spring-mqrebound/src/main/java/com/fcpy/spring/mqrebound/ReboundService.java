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
package com.fcpy.spring.mqrebound;

import java.util.Date;
import java.util.Map;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * This is a service class to perform reply a predefined data back to 
 * the caller.
 */
public class ReboundService {
    private String replyData = null;
    
    @ServiceActivator(requiresReply="true")
    public String execute(@Payload Object payload, @Headers Map<String, Object> headers) {
        System.out.println("===ReboundService::START==========================");
        System.out.println("> time:");
        System.out.println(new Date());
        System.out.println("> payload:");
        System.out.println(payload);
        System.out.println("> headers:");
        System.out.println(headers);
        System.out.println("===ReboundService::END============================");
        return replyData;
    }

    /**
     * Gets reply data.
     * 
     * @return Reply Data in String.
     */
    public String getReplyData() {
        return replyData;
    }

    /**
     * Sets reply data.
     * 
     * @param replyData Reply Data in String.
     */
    public void setReplyData(String replyData) {
        this.replyData = replyData;
    }
}
