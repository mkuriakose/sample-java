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
package com.fcpy.spring.tcpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

/**
 * TCPUtil provides static methods to access the remote server using TCP/IP protocol.
 */
public class TCPUtil {
    /**
     * Sends and receives the data.
     * 
     * @param connectionProperties Connection Properties
     * @param rqstData Request Data
     * @return Response Data
     * @throws IOException 
     */
    public static String sendAndReceive(final ConnectionProperties connectionProperties, final String rqstData) throws IOException {
        Socket targetSocket = null; 
        OutputStream tsos = null;
        InputStream tsis = null;
        ByteArrayOutputStream buffer = null;
        byte[] bytesResult = null;
        try {
            targetSocket = new Socket (connectionProperties.getHost(), connectionProperties.getPort()); 
            tsos = targetSocket.getOutputStream();
            tsis = targetSocket.getInputStream();
            tsos.write(rqstData.getBytes());
            tsos.flush();
            int nRead;
            byte[] data = new byte[4096];
            boolean isAvailable = true;
            buffer = new ByteArrayOutputStream();
            while (isAvailable && (nRead = tsis.read(data)) > 0) {
                buffer.write(data, 0, nRead);
                buffer.flush();
                bytesResult = buffer.toByteArray();
                if (tsis.available() <= 0) {
                    isAvailable = false;
                }
            }
            buffer.flush();
        } finally {
            IOUtils.closeQuietly(buffer);
            IOUtils.closeQuietly(tsos);
            IOUtils.closeQuietly(tsis);
            IOUtils.closeQuietly(targetSocket);
        }
        return new String(bytesResult);
    }
}
