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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Controller implements CommandLineRunner {
    @Autowired
    private ConnectionProperties connectionProperties;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    private String actionKey = null;
    private String actionVal = null;

    @Override
    public void run(String... args) throws Exception {
        // Check the action key and value.
        for (int i=0; i<args.length; i++) {
            if (args[i].indexOf("--") != 0) {
                if (args[i].equals("-c") || args[i].equals("-b") || args[i].equals("-g") || args[i].equals("-e")) {
                    if (actionKey == null) {
                        actionKey = args[i];
                    } else {
                        System.out.println();
                        System.out.println(String.format("Too many options: %s ", args[i]));
                        showHelpAndExit();
                    }
                } else if (args[i].equals("-p") || args[i].equals("-f")) {
                    if (actionKey == null) {
                        actionKey = args[i];
                        i++;
                        if (i < args.length) {
                            if (actionKey.equals("-f")) {
                                actionVal = FileUtils.readFileToString(new File(args[i]));
                            } else {
                                actionVal = args[i];
                            }
                        } else {
                            System.out.println();
                            System.out.println(String.format("Missing value for option: %s ", actionKey));
                            showHelpAndExit();
                        }
                    } else {
                        System.out.println();
                        System.out.println(String.format("Too many options: %s ", args[i]));
                        showHelpAndExit();
                    }
                } else {
                    System.out.println();
                    System.out.println(String.format("Invalid option: %s ", args[i]));
                    showHelpAndExit();
                }
            }
        }
        
        // Start the application or Show Help.
        if (null == actionKey) {
            showHelpAndExit();
        }
    }

    /**
     * Loads the application context and executes the action.
     * 
     * @param args Options and Arguments to execute the MQUtil.
     */
    public void execute() {
        System.out.println("===============================================================================");
        System.out.println();
        try {
            switch (actionKey) {
                case "-c":
                    MQUtil.count(jmsTemplate, connectionProperties);
                    break;
                case "-b":
                    MQUtil.browse(jmsTemplate, connectionProperties);
                    break;
                case "-g":
                    MQUtil.get(jmsTemplate, connectionProperties);
                    break;
                case "-e":
                    MQUtil.clean(jmsTemplate, connectionProperties);
                    break;
                case "-p":
                    MQUtil.put(jmsTemplate, connectionProperties, actionVal);
                    break;
                case "-f":
                    MQUtil.put(jmsTemplate, connectionProperties, actionVal);
                    break;
                default:
                    showHelpAndExit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("===============================================================================");
    }
    
    
    /**
     * Displays the help information and exits the program.
     * 
     * @throws IOException 
     */
    private void showHelpAndExit() throws IOException {
        System.out.println();
        InputStream is = Application.class.getResourceAsStream("/META-INF/help.txt");
        System.out.println(IOUtils.toString(is));
        IOUtils.closeQuietly(is);
        System.exit(-1);
    }
}
