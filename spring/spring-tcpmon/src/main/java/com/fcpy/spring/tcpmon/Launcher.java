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
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * A launcher to provide entry point to execute the TCPMon.
 */
public class Launcher {

    private static final Log logger = LogFactory.getLog(Launcher.class);

    /**
     * Main Entry Point.
     * 
     * @param args Options and Arguments to execute the TCPMon.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // Check the profile key.
        if (!System.getProperties().containsKey("spring.profiles.active") 
                && System.getProperties().containsKey("tcpmon")) {
            System.setProperty("spring.profiles.active", System.getProperty("tcpmon"));
        }
        
        for (String arg : args) {
            if ("-h".equals(arg)) {
                showHelpAndExit();
            }
        }
        
        ApplicationContext ctx = new SpringApplicationBuilder(AppConfig.class).headless(false).run(args);
        Launcher.printBeanNames(ctx);
    }

    /**
     * Prints the name of beans found in the context.
     * 
     * @param ctx Context.
     */
    private static void printBeanNames(ApplicationContext ctx) {
        if (logger.isDebugEnabled()) {
            logger.debug("The beans in the context:");     
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                logger.debug("  > " + beanName);
            }
        }
    }
    
    /**
     * Displays the help information.
     * 
     * @throws IOException 
     */
    private static void showHelpAndExit() throws IOException {
        System.out.println();
        InputStream is = Launcher.class.getResourceAsStream("/META-INF/help.txt");
        System.out.println(IOUtils.toString(is));
        IOUtils.closeQuietly(is);
        System.exit(-1);
    }
    
}
