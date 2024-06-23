package com.polarbookshop.dispatcherservice;

import com.polarbookshop.dispatcherservice.model.label.OrderDispatchedMessage;
import com.polarbookshop.dispatcherservice.model.pack.OrderAcceptedMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@FunctionalSpringBootTest
 class DispatchingFunctionsIntegrationTests {


    @Autowired
    private FunctionCatalog catalog;  // 함수 레지스트리 역할


    @Test
    void packAndLabelOrder(){
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel
                = catalog.lookup(Function.class, "pack|label");

        long orderId = 121L;

        StepVerifier.create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
                .expectNextMatches(orderDispatcher ->
                        orderDispatcher.equals(new OrderDispatchedMessage(orderId)))
                .verifyComplete();

    }
}
