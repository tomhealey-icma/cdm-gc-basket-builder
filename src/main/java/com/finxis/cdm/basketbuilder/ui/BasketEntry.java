package com.finxis.cdm.basketbuilder.ui;


import com.finxis.cdm.basketbuilder.BasketEntryModel;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;


import javax.swing.*;
import java.awt.event.*;

public class BasketEntry extends JPanel implements MouseListener {
    private final transient CdmBasketBuilderApplication application;

    public BasketEntry(BasketEntryModel basketEntryModel, CdmBasketBuilderApplication application) {
        //super(tradeEntryModel);
        //add(tradeEntryModel);
        this.application = application;
        addMouseListener(this);
    }



    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() != 2)
            return;
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
}
