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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class Relay implements Runnable {
    private byte[] bytesResult = null;
    private InputStream is;
    private OutputStream os;
    
    Relay(java.io.InputStream inputstream, java.io.OutputStream outputstream) {
        is = inputstream;
        os = outputstream;
    }
    
    public byte[] getResult() {
        return bytesResult;
    }

    @Override
    public void run() {
        ByteArrayOutputStream buffer = null;
        try {
            buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[4096];
            boolean isAvailable = true;
            while (isAvailable && (nRead = is.read(data)) > 0) {
                os.write(data, 0, nRead);
                buffer.write(data, 0, nRead);
                os.flush();
                buffer.flush();
                bytesResult = buffer.toByteArray();
                if (is.available() <= 0) {
                    isAvailable = false;
                }
            }
            os.flush();
            buffer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(buffer);
        }
    }
}
