package com.finxis.cdm.basketbuilder.ui;


import com.finxis.cdm.basketbuilder.BasketEntryModel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.*;

import java.sql.*;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

import cdm.event.common.*;

//import com.google.gson.JsonObject;
import com.finxis.cdm.basketbuilder.CdmBasketBuilder;
import com.finxis.cdm.basketbuilder.CdmBasketBuilderApplication;
import com.finxis.cdm.basketbuilder.CollateralDescription;
import com.finxis.cdm.basketbuilder.util.*;

@SuppressWarnings("unchecked")
public class BasketEntryPanel extends JPanel {

    public static CdmBasketBuilder basketBuilderApp;

    public final JComboBox symbolComboBox = new JComboBox(CollateralDescription.toArray());
    public final ValidateIntegerTextField quantityTextField = new ValidateIntegerTextField();


    public final ValidateDoubleTextField priceTextField = new ValidateDoubleTextField();

    public final JComboBox sessionComboBox = new JComboBox();

    public final JLabel messageLabel = new JLabel(" ");
    public final JButton submitButton = new JButton("Submit");


    public BasketEntryModel basketEntryModel = null;
    public transient CdmBasketBuilderApplication application = null;

    public final GridBagConstraints constraints = new GridBagConstraints();

    public final GridBagConstraints constraints2 = new GridBagConstraints();

    public JPanel basketBuildPanel;

    public JPanel basketPanelCol1;
    public JPanel basketPanelCol2;


    //Defaults
    public String defaultLocalTimeZone = "UTC";

    public String tradeStateStr = null;
    public String beforeTradeStateStr = null;
    public String afterTradeStateStr = null;


    public List<? extends TradeState> afterTradeStateList = null;
    public List<String> businessEventList;

    public ZonedDateTime TDzonedDateTime;

    public ZonedDateTime LDzonedDateTime;
    public String TDformattedDateTimeString;
    public ZonedDateTime PDzonedDateTime;
    public String PDformattedDateTimeString;
    public ZonedDateTime RDzonedDateTime;
    public String RDformattedDateTimeString;

    public String ETformattedDateTimeString;
    public DateTimeFormatter formatter;

    public ZonedDateTime ETzonedDateTime;
    public DateTimeFormatter ETformatter;

    public DateTimeFormatter eventFileformatter;
    public JComboBox transactionTypeField;

    public JComboBox tradeDirectionField;
    public JTextField tradeDateField;
    public JTextField purchaseDateField;
    public JTextField repurchaseDateField;
    public JTextField tradeUTIField;

    public JComboBox firmLEIField;
    public JComboBox buyerLEIField;
    public JComboBox sellerLEIField;
    public JComboBox collateralTypeField;
    public JTextField collateralDescriptionField;
    public JTextField collateralISINField;
    public JTextField collateralQuantityField;
    public JTextField collateralCleanPriceField;
    public JTextField collateralDirtyPriceField;
    public JTextField collateralAdjustedValueField;
    public JTextField collateralCurrencyField;
    public JTextField repoRateField;
    public JTextField cashCurrencyField;
    public JTextField cashQuantityField;
    public JTextField haircutField;
    public JComboBox termTypeField;
    public JComboBox terminationOptionField;
    public JTextField noticePeriodField;
    public JComboBox deliveryMethodField;
    public JComboBox substitutionAllowedField;
    public JComboBox rateTypeField;
    public JTextField dayCountFractionField;
    public JTextField termDaysField;
    public JTextField purchasePriceField;
    public JTextField repurchasePriceField;
    public JPanel actionPanel;

    public JTextField floatingRateReferenceField;
    public JComboBox floatingRateReferencePeriodField;
    public JTextField floatingRateReferenceMultiplierField;
    public JComboBox floatingRatePaymentFreqField;
    public JTextField floatingRatePaymentMultiplierField;
    public JComboBox floatingRateResetFreqField;
    public JTextField floatingRateResetMultiplierField;
    public JTextField floatingRateField;
    public JTextField floatingRateSpreadField;

    public JComboBox tradingBookOptionField;
    public JComboBox businessCenterOptionField;
    public JComboBox timeZoneOptionField;
    public JTextField executionTimeField;

    public JComboBox venueCodeOptionField;

    public JTextField traderNameField;
    public JTextField traderLocationField;
    public JTextField traderLocalDateField;
    public JTextField traderLocalTimeField;
    public JComboBox brokerField;
    public JComboBox tripartyField;
    public JComboBox beneficiaryField;

    public JComboBox settlementAgentOptionField;

    public JComboBox clearingMemberField;

    public JComboBox agentLenderField;
    public JComboBox centralClearingCounterpartyOptionField;
    public JComboBox csdOptionField;
    public JTextField firmNameField;
    public JTextField firmLeiField;
    public JTextField firmCapacityField;

    public JComboBox agreementNameField;

    public JTextField agreementVersionField;

    public JTextField otherAgreementNameField;

    public JComboBox reportingEntityOptionField;

    public JComboBox reportingSideField;

    public JTextField tradeIdField;
    public JTextField statusField;

    public JComboBox collateralMaturityTypeField;

    public JComboBox issuerTypeField;

    public JComboBox issuerCountryField;


    public JComboBox ownIssuePermittedField;


    public JComboBox issuerCountryOfOriginField;

    public JComboBox issuerNameField;

    public JComboBox issuerAgencyRatingField;

    public JComboBox sovereignAgencyRatingField;

    public JComboBox counterpartyOwnIssuePermittedField;

    public JComboBox collateralAssetTypeField;

    public JComboBox debtTypeField;

    public JComboBox debtInterestTypeField;

    public JComboBox debtSeniorityField;

    public JComboBox debtPrincipalField;

    public JComboBox collateralCriteriaCurrencyField;

    public JComboBox collateralCriteriaAgencyRatingField;

    public JComboBox collateralMinMaturityRangeField;

    public JComboBox collateralMaxMaturityRangeField;

    public JComboBox assetCountryOfOriginField;

    public JComboBox denominatedCurrencyField;

    public JComboBox maturityRangeField;

    public JComboBox productIdentifierField;

    public JComboBox collateralTaxonomyField;

    public JComboBox domesticCurrencyIssuedField;

    public JComboBox listingTypeField;

    public Map<String, String> cdmMap = new HashMap<>();

    public Integer labelWidth;
    public Integer labelHeight;

    public Integer columnWidth;

    public Integer fieldWidth;
    public Integer fieldHeight;



    public BasketEntryPanel(final BasketEntryModel basketEntryModel,
                           final CdmBasketBuilderApplication application) {
        setName("TradeEntryPanel");
        this.basketEntryModel = basketEntryModel;
        this.application = application;


        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setLayout(new GridBagLayout());
        createComponents();
    }


    private void createComponents() {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        labelWidth = 150;
        labelHeight = 15;
        fieldWidth = 170;
        fieldHeight = 20;
        columnWidth = 15;


        basketBuildPanel = new JPanel();
        basketBuildPanel.setAlignmentY(1.0f);
        basketPanelCol1 = new JPanel();
        basketPanelCol1.setLayout(new BoxLayout(basketPanelCol1, BoxLayout.Y_AXIS));
        basketPanelCol1.setAlignmentY(1.0f);
        basketPanelCol2 = new JPanel();
        basketPanelCol2.setLayout(new BoxLayout(basketPanelCol2, BoxLayout.Y_AXIS));
        basketPanelCol2.setAlignmentY(1.0f);

        //Border redline = BorderFactory.createLineBorder(Color.red);
        Border blueline = BorderFactory.createLineBorder(Color.blue);
        //repoTradePanelCol1.setBorder(blueline);
        //repoTradePanelCol2.setBorder(blueline);
        //repoTradePanelCol3.setBorder(blueline);
        basketBuildPanel.setBorder(blueline);
        basketBuildPanel.add(basketPanelCol1, constraints);
        basketBuildPanel.add(basketPanelCol2, constraints);
        //repoTradePanel.add(repoTradePanelCol3);


        IcmaRepoUtil ru = new IcmaRepoUtil();

        //Issuer Criteria
        JPanel collateralIssuerPanel = new JPanel(new GridBagLayout());
        JLabel collateralIssuerLabel = new JLabel("Issuer Criteria:", JLabel.LEFT);
        collateralIssuerLabel.setPreferredSize(new Dimension(320, labelHeight));
        collateralIssuerPanel.add(collateralIssuerLabel);
        basketPanelCol1.add(collateralIssuerPanel);

        //Issuer Type Criteria
        JPanel issuerTypePanel = new JPanel(new GridBagLayout());
        JLabel issuerTypeLabel = new JLabel("issuer Type", JLabel.LEFT);
        issuerTypeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        issuerTypeField = new JComboBox();
        issuerTypeField.addItem(new CItem("Any", "ANY"));
        issuerTypeField.addItem(new CItem("SupraNational", "SUPRA_NATIONAL"));
        issuerTypeField.addItem(new CItem("SovereignCentralBank", "SOVEREIGN_CENTRAL_BANK"));
        issuerTypeField.addItem(new CItem("QuasiGovernment", "QUASI_GOVERNMENT"));
        issuerTypeField.addItem(new CItem("RegionalGovernment", "REGIONAL_GOVERNMENT"));
        issuerTypeField.addItem(new CItem("Corporate", "CORPORATE"));
        issuerTypeField.addItem(new CItem("Fund", "FUND"));
        issuerTypeField.addItem(new CItem("SpecialPurposeVehicle", "SPECIAL_PURPOSE_VEHICLE"));

        issuerTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        issuerTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        issuerTypePanel.add(issuerTypeLabel);
        issuerTypePanel.add(issuerTypeField);
        basketPanelCol1.add(issuerTypePanel);

        //Issuer Type Criteria
        JPanel issuerCountryPanel = new JPanel(new GridBagLayout());
        JLabel issuerCountryLabel = new JLabel("issuer Country", JLabel.LEFT);
        issuerCountryLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        issuerCountryField = new JComboBox();
        issuerCountryField.addItem(new CItem("", ""));
        issuerCountryField.addItem(new CItem("UK", "UK"));
        issuerCountryField.addItem(new CItem("XS", "XS"));

        issuerCountryField.setAlignmentX(Component.LEFT_ALIGNMENT);
        issuerCountryField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        issuerCountryPanel.add(issuerCountryLabel);
        issuerCountryPanel.add(issuerCountryField);
        basketPanelCol1.add(issuerCountryPanel);


        //Rating
        JPanel issuerAgencyRatingPanel = new JPanel(new GridBagLayout());
        JLabel issuerAgencyRatingLabel = new JLabel("Min Rating", JLabel.LEFT);
        issuerAgencyRatingLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        issuerAgencyRatingField = new JComboBox();
        issuerAgencyRatingField.addItem(new CItem("Any", "ANY"));
        issuerAgencyRatingField.addItem(new CItem("AAA", "AAA"));


        issuerAgencyRatingField.setAlignmentX(Component.LEFT_ALIGNMENT);
        issuerAgencyRatingField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        issuerAgencyRatingPanel.add(issuerAgencyRatingLabel);
        issuerAgencyRatingPanel.add(issuerAgencyRatingField);
        basketPanelCol1.add(issuerAgencyRatingPanel);

        //Collateral Criteria
        JPanel assetCriteriaPanel = new JPanel(new GridBagLayout());
        JLabel assetCriteriaLabel = new JLabel("Asset Criteria:", JLabel.LEFT);
        assetCriteriaLabel.setPreferredSize(new Dimension(320, labelHeight));
        assetCriteriaPanel.add(assetCriteriaLabel);
        basketPanelCol1.add(assetCriteriaPanel);


        //Maturity Criteria
        String[] collateralMaturityTypeChoices = {"RemainingMaturity", "OriginalMaturity", "FromIssuance"};
        JPanel collateralMaturityTypePanel = new JPanel(new GridBagLayout());
        JLabel collateralMaturityTypeLabel = new JLabel("Maturity Type", JLabel.LEFT);
        collateralMaturityTypeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));

        collateralMaturityTypeField = new JComboBox<String>(collateralMaturityTypeChoices);
        collateralMaturityTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralMaturityTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        collateralMaturityTypePanel.add(collateralMaturityTypeLabel);
        collateralMaturityTypePanel.add(collateralMaturityTypeField);
        basketPanelCol1.add(collateralMaturityTypePanel);

        //AssetType
        JPanel assetTypeOptionPanel = new JPanel(new GridBagLayout());
        JLabel assetTypeOptionLabel = new JLabel("Asset Type", JLabel.LEFT);
        assetTypeOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        collateralAssetTypeField = new JComboBox();
        collateralAssetTypeField.addItem(new CItem("Security", "SECURITY"));
        collateralAssetTypeField.addItem(new CItem("Cash", "CASH"));
        collateralAssetTypeField.addItem(new CItem("Commodity", "COMMODITY"));

        collateralAssetTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralAssetTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        assetTypeOptionPanel.add(assetTypeOptionLabel);
        assetTypeOptionPanel.add(collateralAssetTypeField);
        basketPanelCol1.add(assetTypeOptionPanel);


        //Debt Type
        JPanel debtTypeOptionPanel = new JPanel(new GridBagLayout());
        JLabel debtTypeOptionLabel = new JLabel("Debt Type", JLabel.LEFT);
        debtTypeOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        debtTypeField = new JComboBox();
        debtTypeField.addItem(new CItem("Vanilla", "VANILLA"));
        debtTypeField.addItem(new CItem("AssetBacked", "ASSET_BACKED"));
        debtTypeField.addItem(new CItem("Convertible", "CONVERTIBLE"));
        debtTypeField.addItem(new CItem("RegCap", "REG_CAP"));
        debtTypeField.addItem(new CItem("Structured", "STRUCTURED"));
        debtTypeField.addItem(new CItem("Holder Convertible", "HOLDER_CONVERTIBLE"));
        debtTypeField.addItem(new CItem("Holder Exchangeable", "HOLDER_EXCHANGEABLE"));
        debtTypeField.addItem(new CItem("Issuer Convertible", "issuer_CONVERTIBLE"));
        debtTypeField.addItem(new CItem("Issuer Exchangeable", "ISSUER_EXCHANGEABLE"));


        debtTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        debtTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        debtTypeOptionPanel.add(debtTypeOptionLabel);
        debtTypeOptionPanel.add(debtTypeField);
        basketPanelCol1.add(debtTypeOptionPanel);


        //Interest Type
        JPanel debtInterestTypeOptionPanel = new JPanel(new GridBagLayout());
        JLabel debtInterestTypeOptionLabel = new JLabel("Interest Type", JLabel.LEFT);
        debtInterestTypeOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        debtInterestTypeField = new JComboBox();
        debtInterestTypeField.addItem(new CItem("Any", "ANY"));
        debtInterestTypeField.addItem(new CItem("Fixed", "FIXED"));
        debtInterestTypeField.addItem(new CItem("Float", "FLOAT"));
        debtInterestTypeField.addItem(new CItem("InflationLinked", "INFLATION_LINKED"));
        debtInterestTypeField.addItem(new CItem("IndexLinked", "INDEX_LINKED"));
        debtInterestTypeField.addItem(new CItem("InterestOnly", "INTEREST_ONLY"));
        debtInterestTypeField.addItem(new CItem("OtherStructured", "OTHER_STRUCTURE"));
        debtInterestTypeField.addItem(new CItem("InverseFloating", "INVERSE_FLOATING"));
        debtInterestTypeField.addItem(new CItem("ZeroCoupon", "ZERO_COUPON"));

        debtInterestTypeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        debtInterestTypeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        debtInterestTypeOptionPanel.add(debtInterestTypeOptionLabel);
        debtInterestTypeOptionPanel.add(debtInterestTypeField);
        basketPanelCol2.add(debtInterestTypeOptionPanel);

        //Debt Seniority
        JPanel debtSeniorityOptionPanel = new JPanel(new GridBagLayout());
        JLabel debtSeniorityOptionLabel = new JLabel("Debt Seniority", JLabel.LEFT);
        debtSeniorityOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        debtSeniorityField = new JComboBox();
        debtSeniorityField.addItem(new CItem("Any", "Any"));
        debtSeniorityField.addItem(new CItem("Secured", "SECURED"));
        debtSeniorityField.addItem(new CItem("Senior", "SENIOR"));
        debtSeniorityField.addItem(new CItem("Subordinated", "SUBORDINATE"));


        debtSeniorityField.setAlignmentX(Component.LEFT_ALIGNMENT);
        debtSeniorityField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        debtSeniorityOptionPanel.add(debtSeniorityOptionLabel);
        debtSeniorityOptionPanel.add(debtSeniorityField);
        basketPanelCol2.add(debtSeniorityOptionPanel);

        //Debt Principal
        JPanel debtPrincipalOptionPanel = new JPanel(new GridBagLayout());
        JLabel debtPrincipalOptionLabel = new JLabel("Principal Type", JLabel.LEFT);
        debtPrincipalOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        debtPrincipalField = new JComboBox();
        debtPrincipalField.addItem(new CItem("Any", "ANY"));
        debtPrincipalField.addItem(new CItem("Bullet", "BULLET"));
        debtPrincipalField.addItem(new CItem("Callable", "CALLABLE"));
        debtPrincipalField.addItem(new CItem("Puttable", "PUTTABLE"));
        debtPrincipalField.addItem(new CItem("Amortising", "AMORTISING"));
        debtPrincipalField.addItem(new CItem("InflationLinked", "INFLATION_LINKED"));
        debtPrincipalField.addItem(new CItem("IndexLinked", "INDEX_LINKED"));
        debtPrincipalField.addItem(new CItem("OtherStructured", "OTHER_STRUCTURED"));
        debtPrincipalField.addItem(new CItem("PrincipalOnly", "PRINCIPAL_ONLY"));


        debtPrincipalField.setAlignmentX(Component.LEFT_ALIGNMENT);
        debtPrincipalField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        debtPrincipalOptionPanel.add(debtPrincipalOptionLabel);
        debtPrincipalOptionPanel.add(debtPrincipalField);
        basketPanelCol2.add(debtPrincipalOptionPanel);

        //Currency
        JPanel collateralCriteriaCurrencyOptionPanel = new JPanel(new GridBagLayout());
        JLabel collateralCriteriaCurrencyOptionLabel = new JLabel("Currency", JLabel.LEFT);
        collateralCriteriaCurrencyOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        collateralCriteriaCurrencyField = new JComboBox();
        collateralCriteriaCurrencyField.addItem(new CItem("Any", "ANY"));
        collateralCriteriaCurrencyField.addItem(new CItem("GBP", "GBP"));
        collateralCriteriaCurrencyField.addItem(new CItem("EUR", "EUR"));


        collateralCriteriaCurrencyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralCriteriaCurrencyField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        collateralCriteriaCurrencyOptionPanel.add(collateralCriteriaCurrencyOptionLabel);
        collateralCriteriaCurrencyOptionPanel.add(collateralCriteriaCurrencyField);
        basketPanelCol2.add(collateralCriteriaCurrencyOptionPanel);

        //Rating
        JPanel collateralCriteriaAgencyRatingOptionPanel = new JPanel(new GridBagLayout());
        JLabel collateralCriteriaAgencyRatingOptionLabel = new JLabel("Min Rating", JLabel.LEFT);
        collateralCriteriaAgencyRatingOptionLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        collateralCriteriaAgencyRatingField = new JComboBox();
        collateralCriteriaAgencyRatingField.addItem(new CItem("Any", "ANY"));
        collateralCriteriaAgencyRatingField.addItem(new CItem("AAA", "AAA"));


        collateralCriteriaAgencyRatingField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralCriteriaAgencyRatingField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        collateralCriteriaAgencyRatingOptionPanel.add(collateralCriteriaAgencyRatingOptionLabel);
        collateralCriteriaAgencyRatingOptionPanel.add(collateralCriteriaAgencyRatingField);
        basketPanelCol2.add(collateralCriteriaAgencyRatingOptionPanel);


        //Min Maturity Range
        JPanel collateralMinMaturityRangePanel = new JPanel(new GridBagLayout());
        JLabel collateralMinMaturityRangeLabel = new JLabel("Min Maturity", JLabel.LEFT);
        collateralMinMaturityRangeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        collateralMinMaturityRangeField = new JComboBox();
        collateralMinMaturityRangeField.addItem(new CItem("Any", "ANY"));
        collateralMinMaturityRangeField.addItem(new CItem("3M", "3M"));
        collateralMinMaturityRangeField.addItem(new CItem("6M", "6M"));
        collateralMinMaturityRangeField.addItem(new CItem("1Y", "1Y"));


        collateralMinMaturityRangeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralMinMaturityRangeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        collateralMinMaturityRangePanel.add(collateralMinMaturityRangeLabel);
        collateralMinMaturityRangePanel.add(collateralMinMaturityRangeField);
        basketPanelCol2.add(collateralMinMaturityRangePanel);


        //Max Maturity Range
        JPanel collateralMaxMaturityRangePanel = new JPanel(new GridBagLayout());
        JLabel collateralMaxMaturityRangeLabel = new JLabel("Max Maturity", JLabel.LEFT);
        collateralMaxMaturityRangeLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));


        collateralMaxMaturityRangeField = new JComboBox();
        collateralMaxMaturityRangeField.addItem(new CItem("Any", "ANY"));
        collateralMaxMaturityRangeField.addItem(new CItem("2Y", "2Y"));
        collateralMaxMaturityRangeField.addItem(new CItem("5Y", "5Y"));
        collateralMaxMaturityRangeField.addItem(new CItem("10Y", "10Y"));


        collateralMaxMaturityRangeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        collateralMaxMaturityRangeField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        collateralMaxMaturityRangePanel.add(collateralMaxMaturityRangeLabel);
        collateralMaxMaturityRangePanel.add(collateralMaxMaturityRangeField);
        basketPanelCol2.add(collateralMaxMaturityRangePanel);



        basketEntryModel.add(basketBuildPanel, constraints);
        add(basketEntryModel, 0, 0);


    }

    private JComponent add(JComponent component, int x, int y) {
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
        return component;
    }

    private void activateSubmit() {
        //TradeType type = (TradeType) typeComboBox.getSelectedItem();

    }

    public void update(Observable o, Object arg) {

    }

    public void rateTypeFieldEvent(String selectedRateType) {

        if (selectedRateType.equals("FLOAT")) {

            this.floatingRateReferenceField.setText("SONIA");
            this.floatingRateReferenceField.setEnabled(true);
            this.floatingRateReferenceField.setBackground(Color.WHITE);

            this.floatingRateReferencePeriodField.setSelectedItem("DAYS");
            this.floatingRateReferencePeriodField.setEnabled(true);
            this.floatingRateReferencePeriodField.setBackground(Color.WHITE);

            this.floatingRateReferenceMultiplierField.setText("1");
            this.floatingRateReferenceMultiplierField.setEnabled(true);
            this.floatingRateReferenceMultiplierField.setBackground(Color.WHITE);

            this.floatingRatePaymentFreqField.setSelectedItem("MONTH");
            this.floatingRatePaymentFreqField.setEnabled(true);
            this.floatingRatePaymentFreqField.setBackground(Color.WHITE);

            this.floatingRatePaymentMultiplierField.setText("1");
            this.floatingRatePaymentMultiplierField.setEnabled(true);
            this.floatingRatePaymentMultiplierField.setBackground(Color.WHITE);

            this.floatingRateResetFreqField.setSelectedItem("DAYS");
            this.floatingRateResetFreqField.setEnabled(true);
            this.floatingRateResetFreqField.setBackground(Color.WHITE);

            this.floatingRateResetMultiplierField.setText("1");
            this.floatingRateResetMultiplierField.setEnabled(true);
            this.floatingRateResetMultiplierField.setBackground(Color.WHITE);

            this.floatingRateField.setText("4.43");
            this.floatingRateField.setEnabled(true);
            this.floatingRateField.setBackground(Color.WHITE);

            this.floatingRateSpreadField.setText("2");
            this.floatingRateSpreadField.setEnabled(true);
            this.floatingRateSpreadField.setBackground(Color.WHITE);

            Double refrate = Double.parseDouble(this.floatingRateField.getText());
            Double spread = Double.parseDouble(this.floatingRateSpreadField.getText());

            Double reporate = (double) Math.round((refrate + spread / 100.0) * 100) / 100;

            this.repoRateField.setText(reporate.toString());


        } else {
            this.floatingRateReferenceField.setText("");
            this.floatingRateReferenceField.setEnabled(false);
            this.floatingRateField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateReferencePeriodField.setSelectedItem("");
            this.floatingRateReferencePeriodField.setEnabled(false);
            this.floatingRateReferencePeriodField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateReferenceMultiplierField.setText("");
            this.floatingRateReferenceMultiplierField.setEnabled(false);
            this.floatingRateReferenceMultiplierField.setBackground(Color.LIGHT_GRAY);

            this.floatingRatePaymentFreqField.setSelectedItem("");
            this.floatingRatePaymentFreqField.setEnabled(false);
            this.floatingRatePaymentFreqField.setBackground(Color.LIGHT_GRAY);

            this.floatingRatePaymentMultiplierField.setText("");
            this.floatingRatePaymentMultiplierField.setEnabled(false);
            this.floatingRatePaymentMultiplierField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateResetFreqField.setSelectedItem("");
            this.floatingRateResetFreqField.setEnabled(false);
            this.floatingRateResetFreqField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateResetMultiplierField.setText("");
            this.floatingRateResetMultiplierField.setEnabled(false);
            this.floatingRateResetMultiplierField.setBackground(Color.LIGHT_GRAY);

            this.floatingRateField.setText("");
            this.floatingRateField.setEnabled(false);
            this.floatingRateField.setBackground(Color.WHITE);

            this.floatingRateSpreadField.setText("");
            this.floatingRateSpreadField.setEnabled(false);
            this.floatingRateSpreadField.setBackground(Color.WHITE);

            this.repoRateField.setText("4.65");

        }

    }

    public void collateralTypeFieldEvent(String selectedCollateralType) throws IOException, SQLException {

        if (selectedCollateralType.equals("Special")) {
            //Collateral
            collateralDescriptionField.setText("GILT .625 31/07/2035");

            //Collateral ISIN
            collateralISINField.setText("GB00BMGR2916");

            //Collateral Quantity
            collateralQuantityField.setText("1000");

            //Collateral Price
            collateralCleanPriceField.setText("91.06");

            //Collateral Dirty Price
            collateralDirtyPriceField.setText("91.8066");

            //Collateral Adjusted Value
            collateralAdjustedValueField.setText("918066.00");


            updateTotalsXPrice();

        } else {
            //Collateral
            collateralDescriptionField.setText("ICMA EU 10 Year Bond GC Basket");

            //Collateral ISIN
            collateralISINField.setText("IC000A3CT441");

            //Collateral Quantity
            collateralQuantityField.setText("50000");

            //Collateral Price
            collateralCleanPriceField.setText("100.00");

            //Collateral Dirty Price
            collateralDirtyPriceField.setText("100.00");

            //Collateral Adjusted Value
            collateralAdjustedValueField.setText("50000000.00");

            haircutField.setText("0");


        }

    }

    public void termTypeFieldEvent(String selectedTermType) {

        if (selectedTermType.equals("FIXED")) {
            this.repurchasePriceField.setText("");
            this.repurchasePriceField.setEnabled(true);
            this.repurchasePriceField.setBackground(Color.WHITE);

            //Repurchase Date
            this.RDzonedDateTime = PDzonedDateTime.plusDays(1);
            this.RDformattedDateTimeString = RDzonedDateTime.format(formatter);
            this.repurchaseDateField.setText(RDformattedDateTimeString);

            this.repurchaseDateField.setEnabled(true);
            this.repurchaseDateField.setBackground(Color.WHITE);


            updateTotalsXPrice();
        } else {

            this.repurchaseDateField.setText("");
            this.repurchaseDateField.setEnabled(false);
            this.repurchaseDateField.setBackground(Color.LIGHT_GRAY);

            this.repurchasePriceField.setEnabled(false);
            this.repurchasePriceField.setBackground(Color.LIGHT_GRAY);

        }
    }

    public void updateTotalsXPrice() {

        //System.out.println("Update Totals");

        DecimalFormat formatter = new DecimalFormat("#,###.00");

        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

        String pdt = purchaseDateField.getText();
        String purchaseDateStr = pdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";
        ZonedDateTime startDateTime = ZonedDateTime.parse(purchaseDateStr, dtFormat);

        String rpdt = repurchaseDateField.getText();
        String repurchaseDateStr = rpdt.replaceAll("\\s", "") + "T00:00:00.000+00:00";
        ZonedDateTime endDateTime = ZonedDateTime.parse(repurchaseDateStr, dtFormat);

        long daysBetween = Duration.between(startDateTime, endDateTime).toDays();
        DecimalFormat intFormat = new DecimalFormat("###");
        String termsDaysStr = intFormat.format(daysBetween);
        termDaysField.setText(termsDaysStr);

        Double dp = Double.valueOf(collateralDirtyPriceField.getText().replaceAll(",", "").trim());
        Double cc = Double.valueOf(collateralQuantityField.getText().replaceAll(",", "").trim());
        Double cap = cc * dp / 100.00 * 1000;
        String collateralAdjustedValueStr = formatter.format(cap);
        collateralAdjustedValueField.setText(collateralAdjustedValueStr);

        //s.replaceAll(",","").trim();

        Double cv = Double.valueOf(collateralAdjustedValueField.getText().replaceAll(",", "").trim());
        Double hc = Double.valueOf(haircutField.getText().replaceAll(",", "").trim());
        Double pp = cv * (1 - hc / 100.00);


        String purchasePriceStr = formatter.format(pp);
        purchasePriceField.setText(purchasePriceStr);

        if (this.rateTypeField.getSelectedItem().toString().equals("FLOAT")) {

            Double refrate = Double.parseDouble(this.floatingRateField.getText());
            Double spread = Double.parseDouble(this.floatingRateSpreadField.getText());
            Double reporate = (double) Math.round((refrate + spread / 100.0) * 100) / 100;
            this.repoRateField.setText(reporate.toString());

        }


        Double rr = Double.valueOf(repoRateField.getText().replaceAll(",", "").trim());
        Double rp = pp + (pp * (daysBetween / 360.00 * rr / 100.00));
        String repurchasePriceStr = formatter.format(rp);
        repurchasePriceField.setText(repurchasePriceStr);


    }

    private class SubmitActivator implements KeyListener, ItemListener {
        public void keyReleased(KeyEvent e) {
            Object obj = e.getSource();
            if (obj == symbolComboBox) {
                //symbolEntered = testField(obj);
            } else if (obj == quantityTextField) {
                //quantityEntered = testField(obj);
            }
            activateSubmit();
        }

        public void itemStateChanged(ItemEvent e) {
            //sessionEntered = sessionComboBox.getSelectedItem() != null;
            activateSubmit();
        }

        private boolean testField(Object o) {
            String value = ((JTextField) o).getText();
            value = value.trim();
            return value.length() > 0;
        }

        public void keyTyped(KeyEvent e) {}

        public void keyPressed(KeyEvent e) {}
    }
}


