package com.polarbookshop.dispatcherservice;

import com.polarbookshop.dispatcherservice.model.label.OrderDispatchedMessage;
import com.polarbookshop.dispatcherservice.model.pack.OrderAcceptedMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@Disabled("These tests are only necessary when using the functions alone (no bindings)")
@FunctionalSpringBootTest
 class DispatchingFunctionsIntegrationTests {


    @Autowired
    private FunctionCatalog catalog;  // 함수 레지스트리 역할

   // 실행 시 에러 발생됨 원인 못찾음
    @Test
    void packAndLabelOrder(){
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel
                = catalog.lookup(Function.class, "pack|label");

        long orderId = 121L;
        var orderAcceptedMessage = new OrderAcceptedMessage(orderId);


        StepVerifier.create(packAndLabel.apply(orderAcceptedMessage))
                .expectNextMatches(orderDispatchedMessage
                        -> orderDispatchedMessage.equals(new OrderDispatchedMessage(orderId)))
                .verifyComplete();

    }


    @Test
    void packed(){
        Function<OrderAcceptedMessage, Long>  pack  = catalog.lookup(Function.class, "pack");
        Long orderId = 121L;
        Long apply = pack.apply(new OrderAcceptedMessage(orderId));
        Assertions.assertThat(apply).isEqualTo(orderId);
    }

    @Test
    void labeled(){
        Function<Flux<Long>, Flux<OrderDispatchedMessage>>label =
                catalog.lookup(Function.class, "label");
        long orderId = 121L;
        Flux<Long> orderAcceptedMessage = Flux.just(orderId);

        StepVerifier.create(label.apply(orderAcceptedMessage))
                .expectNextMatches(orderDispatchedMessage
                        -> orderDispatchedMessage.equals(new OrderDispatchedMessage(orderId)))
                .verifyComplete();
    }
}
