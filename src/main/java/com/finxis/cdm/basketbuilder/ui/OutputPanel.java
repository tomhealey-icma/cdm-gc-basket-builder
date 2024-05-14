package com.finxis.cdm.basketbuilder.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finxis.cdm.basketbuilder.ActionPanelModel;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;
import com.finxis.cdm.basketbuilder.CreateBasket;
import com.finxis.cdm.basketbuilder.util.IcmaRepoUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
public class OutputPanel extends JPanel implements Observer {


    public BasketEntryPanel tep;

    private JTextArea outputArea;

    private JPanel outputPanel=null;
    JScrollPane scrollPane = null;

    public transient CdmBasketBuilderApplication application = null;

    public final GridBagConstraints constraints = new GridBagConstraints();

    public OutputPanel(
            final CdmBasketBuilderApplication application) {


        setName("Output Panel");
        this.application = application;
        this.outputPanel = new JPanel();
        outputPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        outputPanel.setLayout(new GridBagLayout());
        createComponents(tep);
    }

    private void createComponents(BasketEntryPanel tep) {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        //gc.weightx = 1;

        this.tep = tep;
        outputPanel.setLayout(new GridBagLayout());
        GridBagConstraints apgbc = new GridBagConstraints();
        apgbc.gridy = 0;
        apgbc.anchor = apgbc.PAGE_START;

        JPanel sysOutPanel = new JPanel(new BorderLayout());
        sysOutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        sysOutPanel.setSize( 800, 600 );

        outputArea = new JTextArea(40, 80);
        outputArea.setEditable(false);
        scrollPane = new JScrollPane(outputArea);
        scrollPane.setViewportView(outputArea);
        scrollPane.getPreferredSize();
        sysOutPanel.add(scrollPane, BorderLayout.CENTER);
        add(sysOutPanel);
        setVisible(true);


    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public JTextArea getOutputArea(){

        return this.outputArea;
    }


}