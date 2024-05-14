package com.finxis.cdm.basketbuilder.ui;

import cdm.base.staticdata.asset.common.AssetType;
import cdm.base.staticdata.asset.common.CurrencyCodeEnum;
import cdm.product.collateral.CheckEligibilityResult;
import cdm.product.collateral.EligibilityQuery;
import cdm.product.collateral.EligibleCollateralCriteria;
import cdm.product.collateral.EligibleCollateralSpecification;
import cdm.product.collateral.functions.CheckAssetType;
import cdm.product.collateral.functions.CheckDenominatedCurrency;
import cdm.product.collateral.functions.CheckEligibilityByDetails;
import cdm.product.collateral.functions.Create_EligibleCollateralSpecificationFromInstruction;
import cdm.product.template.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finxis.cdm.basketbuilder.ActionPanelModel;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;
import com.finxis.cdm.basketbuilder.CreateBasket;
import com.finxis.cdm.basketbuilder.util.IcmaRepoUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import org.apache.commons.io.FileUtils;
import org.finos.cdm.CdmRuntimeModule;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
public class ActionPanel extends JPanel implements Observer {

    public Integer btnWidth;

    public Integer btnHeight;


    public JTabbedPane tabbedPane;

    public JButton createBasketBtn;

    public JButton loadBasketBtn;

    public JButton loadSecurityBtn;
    public JButton validateCollateralBtn;

    public JPanel actionPanel;

    public BasketEntryPanel tep;
    private String defaultLocalTimeZone = "UTC";


    private JTextArea outputArea;

    public transient CdmBasketBuilderApplication application = null;

    public final GridBagConstraints constraints = new GridBagConstraints();
    public EligibleCollateralCriteria eligibleCollateralCriteria = null;

    public EligibilityQuery eligibilityQuery = null;

    public EligibleCollateralSpecification eligibleCollateralSpecification = null;

    public String publicSecurityQuery=null;
    public Product publicGcBasket = null;

    public ActionPanel(final ActionPanelModel actionPanelModel,
                       final BasketEntryPanel tep,
                       final JTextArea outputArea,
                       final CdmBasketBuilderApplication application) {


        setName("BasketEntryPanel");
        this.actionPanel = actionPanelModel;
        this.application = application;
        this.outputArea = outputArea;


        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setLayout(new GridBagLayout());
        createComponents(tep);
    }

    private void createComponents(BasketEntryPanel tep) {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        btnWidth = 150;
        btnHeight = 30;

        this.tep = tep;
        actionPanel.setLayout(new GridBagLayout());
        GridBagConstraints apgbc = new GridBagConstraints();
        apgbc.gridy = 0;
        apgbc.anchor = apgbc.PAGE_START;

        setSize(600, 50);
        setPreferredSize(new Dimension(600,50));

        Border blueline = BorderFactory.createLineBorder(Color.blue);
        setBorder(blueline);
        setVisible(true);

        JPanel actionBtnPanel = new JPanel(new GridLayout(1, 6));
        actionBtnPanel.setSize(600, 50);
        actionBtnPanel.setPreferredSize(new Dimension(600,50));

        createBasketBtn = new JButton("Create Basket");
        addActionListener(new ActionPanelListener() , createBasketBtn);
        createBasketBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(createBasketBtn);

        loadBasketBtn = new JButton("Load Basket");
        addActionListener(new ActionPanelListener() , loadBasketBtn);
        loadBasketBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(loadBasketBtn);


        loadSecurityBtn = new JButton("Load Security");
        addActionListener(new ActionPanelListener() , loadSecurityBtn);
        loadSecurityBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(loadSecurityBtn);

        validateCollateralBtn = new JButton("Validate");
        addActionListener(new ActionPanelListener() , validateCollateralBtn);
        validateCollateralBtn.setPreferredSize(new Dimension(btnWidth, btnHeight));
        actionBtnPanel.add(validateCollateralBtn);

        actionBtnPanel.setVisible(true);

        add(actionBtnPanel, constraints);

        setVisible(true);


    }

    public void addActionListener(ActionListener listener, JButton jButton) {
        jButton.addActionListener(listener);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private class ActionPanelListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            CreateBasket bt = new CreateBasket();

            if (ae.getSource() == createBasketBtn) {
                JOptionPane.showMessageDialog(tep, "Create Basket", "Alert", JOptionPane.INFORMATION_MESSAGE);
                try {
                    String gcBasketJson = bt.createBasket(tep);
                    IcmaRepoUtil ru = new IcmaRepoUtil();

                    DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String eventDateTime = localDateTime.format(eventDateFormat);

                    ru.writeEventToFile("create-basket-event", eventDateTime, gcBasketJson);
                    outputArea.setText(gcBasketJson);

                    ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
                    Product gcBasket = new Product.ProductBuilderImpl();
                    publicGcBasket = rosettaObjectMapper.readValue(gcBasketJson, gcBasket.getClass());



                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            } else if (ae.getSource() == validateCollateralBtn) {
                JOptionPane.showMessageDialog(tep, "Validate", "Alert", JOptionPane.INFORMATION_MESSAGE);
                try {
                    String businessEvent = bt.createBasket(tep);
                    System.out.println(businessEvent);

                    IcmaRepoUtil ru = new IcmaRepoUtil();

                    DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                    LocalDateTime localDateTime = LocalDateTime.now();
                    String eventDateTime = localDateTime.format(eventDateFormat);

                    //Boolean result = validateCollateral(publicSecurityQuery);
                    Boolean result = validateAssetType(publicSecurityQuery);
                    result = validateCurrency(publicSecurityQuery);
                    if(result){
                        JOptionPane.showMessageDialog(tep, "Valid", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(tep, "Failed", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    }

                    ru.writeEventToFile("validate-basket-event", eventDateTime, businessEvent);


                } catch (IOException ignored) {
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } else if (ae.getSource() == loadBasketBtn) {
                JOptionPane.showMessageDialog(tep, "Select File", "Alert", JOptionPane.INFORMATION_MESSAGE);

                JFileChooser fc = new JFileChooser();
                File cd = new File("../gcBaskets");
                fc.setCurrentDirectory(cd);
                int returnVal = fc.showDialog(ActionPanel.this, "Attach");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    System.out.println("Opening: " + selectedFile.getName());
                    //This is where a real application would open the file.
                    try {
                        String gcBasketJson = FileUtils.readFileToString(selectedFile, "UTF-8");
                        outputArea.setText(gcBasketJson);
                        System.out.println("GC Basket: " + gcBasketJson);
                        loadGCBasket(gcBasketJson);

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            } else if (ae.getSource() == loadSecurityBtn) {
            JOptionPane.showMessageDialog(tep, "Select File", "Alert", JOptionPane.INFORMATION_MESSAGE);

            JFileChooser fc = new JFileChooser();
            File cd = new File("../securities");
            fc.setCurrentDirectory(cd);
            int returnVal = fc.showDialog(ActionPanel.this, "Attach");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                System.out.println("Opening: " + selectedFile.getName());
                //This is where a real application would open the file.
                try {
                    String securityQueryJson = FileUtils.readFileToString(selectedFile, "UTF-8");
                    publicSecurityQuery = securityQueryJson;
                    outputArea.setText(securityQueryJson);
                    System.out.println("Security: " + securityQueryJson);

                    //Boolean result = validateCollateral(securityQueryJson);
                    Boolean result = validateAssetType(publicSecurityQuery);
                    result = validateCurrency(publicSecurityQuery);
                    if(result){
                        JOptionPane.showMessageDialog(tep, "Valid", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(tep, "Failed", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        }
    }

    public Boolean validateCollateral(String securityQueryJson) throws IOException {


        CreateBasket bt = new CreateBasket();
        bt.createEligibilityQuery(tep);

        Injector injector = Guice.createInjector(new CdmRuntimeModule());
        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        EligibilityQuery eligibilityQueryObject = new EligibilityQuery.EligibilityQueryBuilderImpl();
        EligibilityQuery eligibilityQuery = rosettaObjectMapper.readValue(securityQueryJson, eligibilityQueryObject.getClass());

        //String icmarepoexecutionfuncinputJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(betest);

        //EligibleCollateralCriteria criteriaTest = eligibleCollateralSpecificationInstruction.getCommon();


        this.eligibleCollateralSpecification = bt.createEligibleCollateralSpecification(tep);

        CheckEligibilityByDetails checkEligibilityByDetails = new CheckEligibilityByDetails.CheckEligibilityByDetailsDefault();
        injector.injectMembers(checkEligibilityByDetails);
        CheckEligibilityResult checkEligibilityResult = checkEligibilityByDetails.evaluate(this.eligibleCollateralSpecification, eligibilityQuery);


        if (checkEligibilityResult.getIsEligible().booleanValue())
            return true;
        else
            return false;

    }

    public Boolean validateAssetType(String securityQueryJson) throws IOException {


        CreateBasket bt = new CreateBasket();
        bt.createEligibilityQuery(tep);

        Injector injector = Guice.createInjector(new CdmRuntimeModule());
        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        EligibilityQuery eligibilityQueryObject = new EligibilityQuery.EligibilityQueryBuilderImpl();
        EligibilityQuery eligibilityQuery = rosettaObjectMapper.readValue(securityQueryJson, eligibilityQueryObject.getClass());

        //String icmarepoexecutionfuncinputJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(betest);

        //EligibleCollateralCriteria criteriaTest = eligibleCollateralSpecificationInstruction.getCommon();


        this.eligibleCollateralSpecification = bt.createEligibleCollateralSpecification(tep);

        java.util.List<AssetType> assetTypes = (java.util.List<AssetType>) this.eligibleCollateralSpecification.getCriteria().get(0).getAsset().get(0).getCollateralAssetType();

        CheckAssetType checkAssetType = new CheckAssetType.CheckAssetTypeDefault();
        injector.injectMembers(checkAssetType);
        Boolean result = checkAssetType.evaluate(assetTypes, eligibilityQuery);


        if (result)
            return true;
        else
            return false;

    }

    public Boolean validateCurrency(String securityQueryJson) throws IOException {


        CreateBasket bt = new CreateBasket();
        bt.createEligibilityQuery(tep);

        Injector injector = Guice.createInjector(new CdmRuntimeModule());
        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        EligibilityQuery eligibilityQueryObject = new EligibilityQuery.EligibilityQueryBuilderImpl();
        EligibilityQuery eligibilityQuery = rosettaObjectMapper.readValue(securityQueryJson, eligibilityQueryObject.getClass());

        //String icmarepoexecutionfuncinputJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(betest);

        //EligibleCollateralCriteria criteriaTest = eligibleCollateralSpecificationInstruction.getCommon();


        this.eligibleCollateralSpecification = bt.createEligibleCollateralSpecification(tep);

        java.util.List<CurrencyCodeEnum> currencyCodes =  this.eligibleCollateralSpecification.getCriteria().get(0).getAsset().get(0).getDenominatedCurrency();

        CheckDenominatedCurrency checkDenominatedCurrency = new CheckDenominatedCurrency.CheckDenominatedCurrencyDefault();
        injector.injectMembers(checkDenominatedCurrency );
        Boolean result = checkDenominatedCurrency.evaluate(currencyCodes, eligibilityQuery);


        if (result)
            return true;
        else
            return false;

    }

    public void loadGCBasket(String gcBasketJson) throws JsonProcessingException {

        Injector injector = Guice.createInjector(new CdmRuntimeModule());
        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        Product basketProductObj = new Product.ProductBuilderImpl();
        Product  basketProduct = rosettaObjectMapper.readValue(gcBasketJson, basketProductObj.getClass());

        tep.gcBasketIdField.setText(basketProduct.getBasket().getProductIdentifier().get(0).getValue().getIdentifier().getValue().trim().toString());
        tep.gcBasketNameField.setText(basketProduct.getBasket().getProductIdentifier().get(1).getValue().getIdentifier().getValue().trim().toString());

        String issuertype = basketProduct.getBasket().getBasketConstituent().get(0).getSecurity().getEconomicTerms().getCollateral().getCollateralProvisions().getEligibleCollateral().get(0).getIssuer().get(0).getIssuerType().get(0).getIssuerType().toString();
        selectItemByString(tep.issuerTypeField, issuertype );
        //tep.issuerTypeField.setSelectedIndex(1);

        String issuerCountry = basketProduct.getBasket().getBasketConstituent().get(0).getSecurity().getEconomicTerms().getCollateral().getCollateralProvisions().getEligibleCollateral().get(0).getIssuer().get(0).getIssuerCountryOfOrigin().get(0).toDisplayString();
        selectItemByString(tep.issuerCountryField, issuerCountry );
        //tep.issuerCountryField.setSelectedIndex(2);

        String issuerRating = basketProduct.getBasket().getBasketConstituent().get(0).getSecurity().getEconomicTerms().getCollateral().getCollateralProvisions().getEligibleCollateral().get(0).getIssuer().get(0).getIssuerAgencyRating().get(0).getCreditNotation().get(0).getNotation().getValue().trim().toString();
        selectItemByString(tep.issuerAgencyRatingField, issuerRating);
        //tep.issuerAgencyRatingField.setSelectedIndex(1);

        String  collateralAssetType= basketProduct.getBasket().getBasketConstituent().get(0).getSecurity().getEconomicTerms().getCollateral().getCollateralProvisions().getEligibleCollateral().get(0).getAsset().get(0).getCollateralAssetType().get(0).getAssetType().toDisplayString();
        selectItemByString(tep.collateralAssetTypeField, collateralAssetType);

        String  collateralCurrency= basketProduct.getBasket().getBasketConstituent().get(0).getSecurity().getEconomicTerms().getCollateral().getCollateralProvisions().getEligibleCollateral().get(0).getAsset().get(0).getDenominatedCurrency().get(0).toDisplayString();
        selectItemByString(tep.collateralCriteriaCurrencyField, collateralCurrency);

    }

    private static void selectItemByString(JComboBox cb, String s) {
        for (int i=0; i<cb.getItemCount(); i++) {
            if (cb.getItemAt(i).toString().equals(s)) {
                cb.setSelectedIndex(i);
                break;
            }
        }
        return;
    }

    private class ActionActivator implements KeyListener, ItemListener {


        @Override
        public void itemStateChanged(ItemEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}