package com.finxis.cdm.basketbuilder;

import javax.swing.*;
import java.awt.*;

public class BasketEntryModel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();
    public void BasketEntryModel(){
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setLayout(new GridBagLayout());
    }

}
