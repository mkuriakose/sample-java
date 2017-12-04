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
import java.awt.GridLayout;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MainFrame class is the main container to hold all the GUI components.
 */
public class MainView {

    private static final Log logger = LogFactory.getLog(MainView.class);

    private JFrame mainFrame = null;
    private JButton cleanButton = null;
    private JButton recordButton = null;
    private JLabel sourceLabel = null;
    private JLabel targetLabel = null;
    private JLabel backupLabel = null;
    private JLabel statusLabel = null;
    private JScrollPane sourceScrollPane = null;
    private JScrollPane targetScrollPane = null;
    private JScrollPane backupScrollPane = null;
    private JTextArea sourceTextArea = null;
    private JTextArea targetTextArea = null;
    private JPanel backupPane = null;
    private JList<Object> connectionRecordList = null;
    
    /**
     * Constructs the MainFrame class.
     */
    public MainView() {
        if (logger.isInfoEnabled()) {
            logger.info(SwingUtilities.isEventDispatchThread() ? "Created On EDT" : "NOT Created On EDT");
        }
    }

    /**
     * Initializes the Main Frame.
     */
    @PostConstruct
    public void init() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(0, 2));
        buttonPane.add(this.cleanButton);
        buttonPane.add(this.recordButton);
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout(new BorderLayout(0, 0));
        bottomPane.add(buttonPane, BorderLayout.EAST);
        bottomPane.add(this.statusLabel, BorderLayout.CENTER);
        JPanel sourcePane = new JPanel();
        sourcePane.setLayout(new BorderLayout(0, 0));
        sourcePane.add(this.sourceLabel, BorderLayout.NORTH);
        sourcePane.add(this.sourceScrollPane, BorderLayout.CENTER);
        JPanel targetPane = new JPanel();
        targetPane.setLayout(new BorderLayout(0, 0));
        targetPane.add(this.targetLabel, BorderLayout.NORTH);
        targetPane.add(this.targetScrollPane, BorderLayout.CENTER);
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new GridLayout(0, 2));
        centerPane.add(sourcePane);
        centerPane.add(targetPane);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(1, 1));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(centerPane, BorderLayout.CENTER);
        contentPane.add(bottomPane, BorderLayout.SOUTH);
        contentPane.add(this.backupPane, BorderLayout.EAST);
        this.mainFrame.setContentPane(contentPane);
        this.mainFrame.pack();
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setVisible(true);
    }

    public JButton getCleanButton() {
        return cleanButton;
    }

    public void setCleanButton(JButton cleanButton) {
        this.cleanButton = cleanButton;
    }

    public JButton getRecordButton() {
        return recordButton;
    }

    public void setRecordButton(JButton recordButton) {
        this.recordButton = recordButton;
    }

    public JLabel getSourceLabel() {
        return sourceLabel;
    }

    public void setSourceLabel(JLabel sourceLabel) {
        this.sourceLabel = sourceLabel;
    }

    public JLabel getTargetLabel() {
        return targetLabel;
    }

    public void setTargetLabel(JLabel targetLabel) {
        this.targetLabel = targetLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public JScrollPane getSourceScrollPane() {
        return sourceScrollPane;
    }

    public void setSourceScrollPane(JScrollPane sourceScrollPane) {
        this.sourceScrollPane = sourceScrollPane;
    }

    public JScrollPane getTargetScrollPane() {
        return targetScrollPane;
    }

    public void setTargetScrollPane(JScrollPane targetScrollPane) {
        this.targetScrollPane = targetScrollPane;
    }

    public JTextArea getSourceTextArea() {
        return sourceTextArea;
    }

    public void setSourceTextArea(JTextArea sourceTextArea) {
        this.sourceTextArea = sourceTextArea;
    }

    public JTextArea getTargetTextArea() {
        return targetTextArea;
    }

    public void setTargetTextArea(JTextArea targetTextArea) {
        this.targetTextArea = targetTextArea;
    }
    
    public JScrollPane getBackupScrollPane() {
        return backupScrollPane;
    }

    public void setBackupScrollPane(JScrollPane backupScrollPane) {
        this.backupScrollPane = backupScrollPane;
    }

    public JLabel getBackupLabel() {
        return backupLabel;
    }

    public void setBackupLabel(JLabel backupLabel) {
        this.backupLabel = backupLabel;
    }

    public JList<Object> getBackupList() {
        return connectionRecordList;
    }

    public void setBackupList(JList<Object> connectionRecordList) {
        this.connectionRecordList = connectionRecordList;
    }

    public JPanel getBackupPane() {
        return backupPane;
    }

    public void setBackupPane(JPanel backupPane) {
        this.backupPane = backupPane;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
