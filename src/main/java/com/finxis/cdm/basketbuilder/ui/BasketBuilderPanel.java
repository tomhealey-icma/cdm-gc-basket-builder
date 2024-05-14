package com.finxis.cdm.basketbuilder.ui;

import com.finxis.cdm.basketbuilder.ActionPanelModel;
import com.finxis.cdm.basketbuilder.BasketEntryModel;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;

import java.awt.*;

import javax.swing.*;

public class BasketBuilderPanel extends JPanel{

    //private final BasketPanel basketPanel;
    private BasketEntryPanel basketEntryPanel;

    private ActionPanel actionPanel;

    private OutputPanel outputPanel;

    public BasketBuilderPanel(BasketEntryModel basketEntryModel,
                       ActionPanelModel actionPanelModel, CdmBasketBuilderApplication application) {

        setName("Basket Building App");

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        //constraints.fill = GridBagConstraints.BOTH;
        //constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        basketEntryPanel = new BasketEntryPanel(basketEntryModel, application);
        constraints.insets = new Insets(0, 0, 5, 0);
        add(basketEntryPanel, constraints);

        constraints.gridx++;
        constraints.weighty = 10;


        outputPanel = new OutputPanel(application);


        actionPanel = new ActionPanel(actionPanelModel, basketEntryPanel, outputPanel.getOutputArea(), application);
        actionPanel.setPreferredSize(new Dimension(650,50));
        add(actionPanel, constraints);
        setVisible(true);



        //outputPanel = new OutputPanel(application);
        outputPanel.setPreferredSize(new Dimension(650,400));

        //constraints.gridx++;
        constraints.weighty = 1;

        add(outputPanel,constraints);
        setVisible(true);

    }


}
