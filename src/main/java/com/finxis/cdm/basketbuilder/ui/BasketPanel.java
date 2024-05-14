package com.finxis.cdm.basketbuilder.ui;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.finxis.cdm.basketbuilder.ActionPanelModel;
import com.finxis.cdm.basketbuilder.BasketEntryModel;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;


public class  BasketPanel extends JPanel {

    private JPanel basketEntry = null;
    private JPanel actionPanel = null;
    public BasketPanel(BasketEntryModel basketEntryModel, ActionPanelModel actionPanelModel, CdmBasketBuilderApplication application) {


        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 2;
        setVisible(true);
    }


    public JPanel basketEntry() {
        return basketEntry;
    }

    public JPanel actionPanel() {
        return actionPanel;
    }
}
