package com.finxis.cdm.basketbuilder;

import com.finxis.cdm.basketbuilder.ui.BasketBuilderFrame;
import com.rosetta.model.lib.process.PostProcessStep;

import javax.swing.*;
import java.awt.*;

public class CdmBasketBuilder{

    private PostProcessStep keyProcessor = null;
    private JFrame frame = null;
    private static CdmBasketBuilder basketbuilderapp;

    public CdmBasketBuilder(String[] args) throws Exception {

        BasketEntryModel basketEntryModel = basketEntryModel();
        ActionPanelModel actionPanelModel = actionPanelModel();
        CdmBasketBuilderApplication application = application();

        frame = new BasketBuilderFrame(basketEntryModel, actionPanelModel, application);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }


    protected BasketEntryModel basketEntryModel() {
        return new BasketEntryModel();
    }

    protected ActionPanelModel actionPanelModel() {
        return new ActionPanelModel();
    }
    protected CdmBasketBuilderApplication application() {
        return new CdmBasketBuilderApplication();
    }

    public JFrame getFrame() {
        return frame;
    }

    public static CdmBasketBuilder get() {return basketbuilderapp;}

    public static void main(String[] args) throws Exception {
        System.out.println("CDM Basket Builder");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        basketbuilderapp = new CdmBasketBuilder(args);


    }

}
