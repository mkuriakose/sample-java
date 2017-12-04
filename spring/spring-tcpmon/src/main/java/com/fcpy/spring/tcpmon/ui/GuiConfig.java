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
package com.fcpy.spring.tcpmon.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * GuiConfig initializes the GUI related beans.
 */
@Configuration
@ComponentScan("com.fcpy.spring.tcpmon.ui")
public class GuiConfig {
    public static final String BEAN_MAIN_VIEW = "mainView";
    public static final String BEAN_FRM_MAIN = "mainFrame";
    public static final String BEAN_BTN_CLEAN = "cleanButton";
    public static final String BEAN_BTN_RECORD = "recordButton";
    public static final String BEAN_LB_SOURCE = "sourceLabel";
    public static final String BEAN_LB_TARGET = "targetLabel";
    public static final String BEAN_LB_BACKUP = "backupLabel";
    public static final String BEAN_LB_STATUS = "statusLabel";
    public static final String BEAN_SP_SOURCE = "sourceScrollPane";
    public static final String BEAN_SP_TARGET = "targetScrollPane";
    public static final String BEAN_SP_BACKUP = "backupScrollPane";
    public static final String BEAN_TA_SOURCE = "sourceTextArea";
    public static final String BEAN_TA_TARGET = "targetTextArea";
    public static final String BEAN_LT_BACKUP = "backupList";
    public static final String BEAN_PL_BACKUP = "backupPane";

    @Bean(name = BEAN_MAIN_VIEW)
    @Lazy
    public MainView mainView() {
        MainView view = new MainView();
        view.setMainFrame(mainFrame());
        view.setCleanButton(cleanButton());
        view.setRecordButton(recordButton());
        view.setSourceLabel(sourceLabel());
        view.setTargetLabel(targetLabel());
        view.setBackupLabel(backupLabel());
        view.setStatusLabel(statusLabel());
        view.setSourceScrollPane(sourceScrollPane());
        view.setTargetScrollPane(targetScrollPane());
        view.setBackupScrollPane(backupScrollPane());
        view.setSourceTextArea(sourceTextArea());
        view.setTargetTextArea(targetTextArea());
        view.setBackupList(backupList());
        view.setBackupPane(backupPane());
        return view;
    }

    @Bean(name = BEAN_FRM_MAIN)
    @Lazy
    public JFrame mainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("TCPMon");
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setSize(1024, 600);
        return mainFrame;
    }

    @Bean(name = BEAN_BTN_CLEAN)
    @Lazy
    public JButton cleanButton() {
        return new JButton("Clean");
    }

    @Bean(name = BEAN_BTN_RECORD)
    @Lazy
    public JButton recordButton() {
        return new JButton("Record");
    }

    @Bean(name = BEAN_LB_SOURCE)
    @Lazy
    public JLabel sourceLabel() {
        return new JLabel("source");
    }

    @Bean(name = BEAN_LB_TARGET)
    @Lazy
    public JLabel targetLabel() {
        return new JLabel("target");
    }

    @Bean(name = BEAN_LB_BACKUP)
    @Lazy
    public JLabel backupLabel() {
        return new JLabel("History");
    }

    @Bean(name = BEAN_LB_STATUS)
    @Lazy
    public JLabel statusLabel() {
        return new JLabel("Not Ready.");
    }

    @Bean(name = BEAN_SP_SOURCE)
    @Lazy
    public JScrollPane sourceScrollPane() {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportView(sourceTextArea());
        return jScrollPane;
    }

    @Bean(name = BEAN_SP_TARGET)
    @Lazy
    public JScrollPane targetScrollPane() {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportView(targetTextArea());
        return jScrollPane;
    }

    @Bean(name = BEAN_SP_BACKUP)
    @Lazy
    public JScrollPane backupScrollPane() {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setViewportView(backupList());
        return jScrollPane;
    }
    
    @Bean(name = BEAN_TA_SOURCE)
    @Lazy
    public JTextArea sourceTextArea() {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextArea.setEditable(false);
        return jTextArea;
    }

    @Bean(name = BEAN_TA_TARGET)
    @Lazy
    public JTextArea targetTextArea() {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jTextArea.setEditable(false);
        return jTextArea;
    }

    @Bean(name = BEAN_LT_BACKUP)
    @Lazy
    public JList<Object> backupList() {
        JList<Object> jList = new JList<Object>();
        return jList;
    }
    
    @Bean(name = BEAN_PL_BACKUP)
    @Lazy
    public JPanel backupPane() {
        JPanel backupPane = new JPanel();
        backupPane.setLayout(new BorderLayout(0, 0));
        backupPane.add(backupLabel(), BorderLayout.NORTH);
        backupPane.add(backupScrollPane(), BorderLayout.CENTER);
        backupPane.setVisible(false);
        return backupPane;
    }
}
