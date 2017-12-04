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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fcpy.spring.tcpmon.edt.OnDemandBean;
import com.fcpy.spring.tcpmon.model.ConnectionRecord;
import com.fcpy.spring.tcpmon.model.DataModel;
import com.fcpy.spring.tcpmon.ui.MainView;

/**
 * This class acts as a controller under MVC design which manages
 * the interaction between Model and View.
 */
public class AppController implements PropertyChangeListener, OnDemandBean {
    private static final Log logger = LogFactory.getLog(AppController.class);

    private DataModel model;
    private MainView view;
    
    /**
     * Constructs the Application Controller which manages the interaction
     * between model and view.
     * 
     * @param view MainView.
     * @param model DataModel.
     */
    public AppController(MainView view, DataModel model) {
        if (logger.isInfoEnabled()) {
            logger.info(SwingUtilities.isEventDispatchThread() ? "Created On EDT" : "NOT Created On EDT");
        }
        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.view = view;
        setupViewEvents();
        setupLabel();
    }
    
    /**
     * Sets the view events.
     */
    private void setupViewEvents() {
        this.view.getCleanButton().setAction(new AbstractAction("Clean"){
            private static final long serialVersionUID = 6506373108592768414L;
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.view.getSourceTextArea().setText("");
                AppController.this.view.getTargetTextArea().setText("");
                AppController.this.view.getStatusLabel().setText("Cleaned.");
                AppController.this.model.cleanList();
            }
        });
        this.view.getRecordButton().setAction(new AbstractAction("Record"){
            private static final long serialVersionUID = 6506373108592768414L;
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.view.getBackupPane().setVisible(!AppController.this.view.getBackupPane().isVisible());
            }
        });
        this.view.getBackupList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    ConnectionRecord selectedValue = (ConnectionRecord) AppController.this.view.getBackupList().getSelectedValue();
                    if (selectedValue != null) {
                        String rqstString = new String(selectedValue.getRqstData()) + System.lineSeparator() + "<<<END-OF-RQST-DATA>>>";
                        AppController.this.view.getSourceTextArea().setText(rqstString);
                        String rspnString = new String(selectedValue.getRspnData()) + System.lineSeparator() + "<<<END-OF-RSPN-DATA>>>";
                        AppController.this.view.getTargetTextArea().setText(rspnString);
                        AppController.this.view.getStatusLabel().setText(String.format("Showing record captured at %s.", selectedValue.getTime()));
                    }
                }
            }
        });
    }
    
    /**
     * Sets the labels.
     */
    private void setupLabel() {
        this.view.getSourceLabel().setText(
                String.format("localhost:%s", this.model.getListenPort()));
        this.view.getTargetLabel().setText(
                String.format("%s:%s", this.model.getTargetHost(), this.model.getTargetPort()));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(DataModel.PROP_NEW_DATA_MODEL)) {
            this.view.getSourceTextArea().setText("");
            this.view.getTargetTextArea().setText("");
        } else if (evt.getPropertyName().equals(DataModel.PROP_SET_LAST_STATUS)) {
            this.view.getStatusLabel().setText((String) evt.getNewValue()); 
        } else if (evt.getPropertyName().equals(DataModel.PROP_SET_RQST_DATA)) {
            byte[] rqstData = (byte[]) evt.getNewValue();
            if (null != rqstData && rqstData.length > 0) {
                String rqstString = new String(rqstData) + System.lineSeparator() + "<<<END-OF-RQST-DATA>>>";
                this.view.getSourceTextArea().setText(rqstString);
            }
        } else if (evt.getPropertyName().equals(DataModel.PROP_SET_RSPN_DATA)) {
            byte[] rspnData = (byte[]) evt.getNewValue();
            if (null != rspnData && rspnData.length > 0) {
                String rspnString = new String(rspnData) + System.lineSeparator() + "<<<END-OF-RSPN-DATA>>>";
                this.view.getTargetTextArea().setText(rspnString);
            }
        } else if (evt.getPropertyName().equals(DataModel.PROP_ADD_DATA_TO_LIST)) {
            this.view.getBackupList().setListData((Object[]) evt.getNewValue());
        } else {
            System.out.println("Unknown Event.");
        }
    }

}