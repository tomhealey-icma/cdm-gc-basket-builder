package com.finxis.cdm.basketbuilder.ui;

import com.finxis.cdm.basketbuilder.ActionPanelModel;
import com.finxis.cdm.basketbuilder.BasketEntryModel;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;


import javax.swing.*;
import java.awt.*;

public class BasketBuilderFrame extends JFrame {

    public BasketBuilderFrame(BasketEntryModel basketEntryModel, ActionPanelModel actionPanelModel, CdmBasketBuilderApplication application){
        super();
        setTitle("CDM Basket Builder Demo");
        setSize(800,800);

        getContentPane().add(new BasketBuilderPanel(basketEntryModel,actionPanelModel, application), BorderLayout.CENTER);
        setVisible(true);
    }

}
