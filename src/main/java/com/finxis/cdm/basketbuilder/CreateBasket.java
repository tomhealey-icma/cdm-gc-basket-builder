package com.finxis.cdm.basketbuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import cdm.base.datetime.Period;
import cdm.base.datetime.PeriodBound;
import cdm.base.datetime.PeriodEnum;
import cdm.base.datetime.PeriodRange;
import cdm.base.math.QuantifierEnum;
import cdm.base.staticdata.asset.common.*;
import cdm.base.staticdata.party.LegalEntity;
import cdm.event.common.Instruction;
import cdm.observable.asset.CreditNotation;
import cdm.observable.asset.CreditRatingAgencyEnum;
import cdm.product.collateral.*;
import cdm.product.collateral.functions.Create_EligibleCollateralSpecificationFromInstruction;
import cdm.product.template.Basket;
import cdm.product.template.BasketConstituent;
import cdm.product.template.EconomicTerms;
import cdm.product.template.Product;
import com.finxis.cdm.basketbuilder.ui.BasketEntryPanel;
import com.finxis.cdm.basketbuilder.util.*;
import com.finxis.cdm.basketbuilder.util.CdmEnumMap;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.metafields.FieldWithMetaString;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.lib.records.Date;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;
import org.finos.cdm.CdmRuntimeModule;

import javax.swing.*;

public class CreateBasket {

    public String createBasket(BasketEntryPanel bep) throws IOException, InterruptedException, ParseException {

        String basket = null;

        //Enum Mapping
        CdmEnumMap map = new CdmEnumMap();
        map.buildEnumMap(bep.cdmMap);

        String currency = bep.collateralCriteriaCurrencyField.getSelectedItem().toString();

        Product gcBasket = Product.builder()
                .setBasket(Basket.builder()
                        .setBasketConstituent(List.of(BasketConstituent.builder()
                                        .setSecurity(Security.builder()
                                                .setEconomicTerms(EconomicTerms.builder()
                                                    .setCollateral(Collateral.builder()
                                                        .setCollateralProvisions(CollateralProvisions.builder()
                                                                .setEligibleCollateral(createEligibleCollateralCriteria(bep)))
                                                                                .build()))))));


        String gcBasketJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(gcBasket);

        return gcBasketJson;

    }

    public List<EligibleCollateralCriteria> createEligibleCollateralCriteria(BasketEntryPanel bep){

        //Enum Mapping
        CdmEnumMap map = new CdmEnumMap();
        map.buildEnumMap(bep.cdmMap);

        String currency = bep.collateralCriteriaCurrencyField.getSelectedItem().toString();

        EligibleCollateralSpecificationInstruction eligibleCollateralSpecificationInstruction = EligibleCollateralSpecificationInstruction.builder()
                .setCommon(EligibleCollateralCriteria.builder()
                        .setAsset(List.of(AssetCriteria.builder()
                                .setDenominatedCurrency(List.of(CurrencyCodeEnum.valueOf(currency)))
                                .setCollateralAssetType(List.of(AssetType.builder()
                                        .setAssetType(AssetTypeEnum.SECURITY)
                                        .setDebtType(DebtType.builder()
                                                .setDebtClass(DebtClassEnum.VANILLA))))
                                .setMaturityRange(PeriodRange.builder()
                                        .setLowerBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.M)
                                                        .setPeriodMultiplier(3)))
                                        .setUpperBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.Y)
                                                        .setPeriodMultiplier(10))))
                                .addAgencyRating(AgencyRatingCriteria.builder()
                                        .setReferenceAgency(CreditRatingAgencyEnum.FITCH)
                                        .setQualifier(QuantifierEnum.ANY)
                                        .setCreditNotation(List.of(CreditNotation.builder()
                                                .setNotation(FieldWithMetaString.builder()
                                                        .setValue("AAA")))))
                        )))
                .setVariable(List.of(EligibleCollateralCriteria.builder()
                        .setAsset(List.of(AssetCriteria.builder()
                                .setDenominatedCurrency(List.of(CurrencyCodeEnum.valueOf(currency)))
                                .setCollateralAssetType(List.of(AssetType.builder()
                                        .setAssetType(AssetTypeEnum.SECURITY)
                                        .setDebtType(DebtType.builder()
                                                .setDebtClass(DebtClassEnum.VANILLA))))
                                .setMaturityRange(PeriodRange.builder()
                                        .setLowerBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.M)
                                                        .setPeriodMultiplier(3)))
                                        .setUpperBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.Y)
                                                        .setPeriodMultiplier(10))))
                                .addAgencyRating(AgencyRatingCriteria.builder()
                                        .setReferenceAgency(CreditRatingAgencyEnum.FITCH)
                                        .setQualifier(QuantifierEnum.ANY)
                                        .setCreditNotation(List.of(CreditNotation.builder()
                                                .setNotation(FieldWithMetaString.builder()
                                                        .setValue("AAA")))))
                        ))))
                .build();

        Injector injector = Guice.createInjector(new CdmRuntimeModule());

        EligibleCollateralCriteria criteriaTest = eligibleCollateralSpecificationInstruction.getCommon();

        Create_EligibleCollateralSpecificationFromInstruction.Create_EligibleCollateralSpecificationFromInstructionDefault createEligibleCollateralSpecification = new Create_EligibleCollateralSpecificationFromInstruction.Create_EligibleCollateralSpecificationFromInstructionDefault();
        injector.injectMembers(createEligibleCollateralSpecification);
        EligibleCollateralSpecification eligibleCollateralSpecification = createEligibleCollateralSpecification.evaluate(eligibleCollateralSpecificationInstruction);

        List<EligibleCollateralCriteria> elcList = List.of(eligibleCollateralSpecification.getCriteria().get(0));
        return elcList;

    }

    public EligibilityQuery createEligibilityQuery(BasketEntryPanel bep) throws IOException {

        EligibilityQuery eligibilityQuery = EligibilityQuery.builder()
                .setMaturity(BigDecimal.valueOf(10.0))
                .setCollateralAssetType(AssetType.builder()
                        .setAssetType(AssetTypeEnum.SECURITY)
                        .setDebtType(DebtType.builder()
                                .setDebtClass(DebtClassEnum.VANILLA)))
                .setAssetCountryOfOrigin(ISOCountryCodeEnum.GB)
                .setDenominatedCurrency(CurrencyCodeEnum.GBP)
                .setAgencyRating(AgencyRatingCriteria.builder()
                        .setCreditNotation(List.of(CreditNotation.builder()
                                .setNotation(FieldWithMetaString.builder()
                                        .setValue("AAA"))))
                        .setQualifier(QuantifierEnum.ANY))
                .setIssuerType(CollateralIssuerType.builder()
                        .setIssuerType(IssuerTypeEnum.SOVEREIGN_CENTRAL_BANK))
                .setIssuerName(LegalEntity.builder()
                        .setName(FieldWithMetaString.builder()
                                .setValue("HIS MAJESTY'S TREASURY")))

                .build();

        String eligibilityQueryJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(eligibilityQuery );

        IcmaRepoUtil ru = new IcmaRepoUtil();

        DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String eventDateTime = localDateTime.format(eventDateFormat);

        ru.writeEventToFile("eligibility-query", eventDateTime, eligibilityQueryJson);

        return eligibilityQuery;
    }

    public EligibleCollateralSpecification createEligibleCollateralSpecification(BasketEntryPanel bep){

        //Enum Mapping
        CdmEnumMap map = new CdmEnumMap();
        map.buildEnumMap(bep.cdmMap);

        String currency = bep.collateralCriteriaCurrencyField.getSelectedItem().toString();

        EligibleCollateralSpecificationInstruction eligibleCollateralSpecificationInstruction = EligibleCollateralSpecificationInstruction.builder()
                .setCommon(EligibleCollateralCriteria.builder()
                        .setAsset(List.of(AssetCriteria.builder()
                                .setDenominatedCurrency(List.of(CurrencyCodeEnum.valueOf(currency)))
                                .setCollateralAssetType(List.of(AssetType.builder()
                                        .setAssetType(AssetTypeEnum.SECURITY)
                                        .setDebtType(DebtType.builder()
                                                .setDebtClass(DebtClassEnum.VANILLA))))
                                .setMaturityRange(PeriodRange.builder()
                                        .setLowerBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.M)
                                                        .setPeriodMultiplier(3)))
                                        .setUpperBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.Y)
                                                        .setPeriodMultiplier(10))))
                                .addAgencyRating(AgencyRatingCriteria.builder()
                                        .setReferenceAgency(CreditRatingAgencyEnum.FITCH)
                                        .setQualifier(QuantifierEnum.ANY)
                                        .setCreditNotation(List.of(CreditNotation.builder()
                                                .setNotation(FieldWithMetaString.builder()
                                                        .setValue("AAA")))))
                        )))
                .setVariable(List.of(EligibleCollateralCriteria.builder()
                        .setAsset(List.of(AssetCriteria.builder()
                                .setDenominatedCurrency(List.of(CurrencyCodeEnum.valueOf(currency)))
                                .setCollateralAssetType(List.of(AssetType.builder()
                                        .setAssetType(AssetTypeEnum.SECURITY)
                                        .setDebtType(DebtType.builder()
                                                .setDebtClass(DebtClassEnum.VANILLA))))
                                .setMaturityRange(PeriodRange.builder()
                                        .setLowerBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.M)
                                                        .setPeriodMultiplier(3)))
                                        .setUpperBound(PeriodBound.builder()
                                                .setPeriod(Period.builder()
                                                        .setPeriod(PeriodEnum.Y)
                                                        .setPeriodMultiplier(10))))
                                .addAgencyRating(AgencyRatingCriteria.builder()
                                        .setReferenceAgency(CreditRatingAgencyEnum.FITCH)
                                        .setQualifier(QuantifierEnum.ANY)
                                        .setCreditNotation(List.of(CreditNotation.builder()
                                                .setNotation(FieldWithMetaString.builder()
                                                        .setValue("AAA")))))
                        ))))
                .build();

        Injector injector = Guice.createInjector(new CdmRuntimeModule());

        EligibleCollateralCriteria criteriaTest = eligibleCollateralSpecificationInstruction.getCommon();

        Create_EligibleCollateralSpecificationFromInstruction.Create_EligibleCollateralSpecificationFromInstructionDefault createEligibleCollateralSpecification = new Create_EligibleCollateralSpecificationFromInstruction.Create_EligibleCollateralSpecificationFromInstructionDefault();
        injector.injectMembers(createEligibleCollateralSpecification);
        EligibleCollateralSpecification eligibleCollateralSpecification = createEligibleCollateralSpecification.evaluate(eligibleCollateralSpecificationInstruction);

        return eligibleCollateralSpecification;

    }

    public void resetBasket(BasketEntryPanel bep) throws SQLException, IOException {


    }
}
