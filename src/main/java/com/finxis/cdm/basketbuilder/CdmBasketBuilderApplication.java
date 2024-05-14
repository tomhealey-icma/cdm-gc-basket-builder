package com.finxis.cdm.basketbuilder;


import cdm.event.common.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;

import com.rosetta.model.lib.process.PostProcessStep;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;

public class CdmBasketBuilderApplication {

    private final PostProcessStep keyProcessor;
    public CdmBasketBuilderApplication () {keyProcessor = new GlobalKeyProcessStep(NonNullHashCollector::new);
    }


    private <T extends RosettaModelObject> T addGlobalKey(Class<T> type, T modelObject) {
        RosettaModelObjectBuilder builder = modelObject.toBuilder();
        keyProcessor.runProcessStep(type, builder);
        return type.cast(builder.build());
    }
    private String getGlobalReference(GlobalKey globalKey) {
        return globalKey.getMeta().getGlobalKey();
    }


    public static String getTradeState(String businessEventJson) throws JsonProcessingException {

        ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
        BusinessEvent businessEventObj = new BusinessEvent.BusinessEventBuilderImpl();
        BusinessEvent businessEvent  = rosettaObjectMapper.readValue(businessEventJson, businessEventObj.getClass());


        TradeState tradeStateObj = new TradeState.TradeStateBuilderImpl();
        TradeState tradeState  = businessEvent.getAfter().get(0);

        String tradeStateJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(tradeState);

        return tradeStateJson;

    }


}

