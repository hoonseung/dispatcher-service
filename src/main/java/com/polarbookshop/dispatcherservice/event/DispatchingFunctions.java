package com.polarbookshop.dispatcherservice.event;

import com.polarbookshop.dispatcherservice.model.label.OrderDispatchedMessage;
import com.polarbookshop.dispatcherservice.model.pack.OrderAcceptedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

// 스프링 클라우드 함수는 함수를 빈으로 등록하면 인식할 수 있다.
// 스프링 클라우드 함수는 표준 자바 함수형 인터페이스인 Function, Supplier, Consumer 를 준수한 함수를 관리한다.
@Configuration
@Slf4j
public class DispatchingFunctions {


    @Bean
    public Function<OrderAcceptedMessage, Long> pack(){
        return orderAcceptedMessage -> {
            log.info("id {}order has packed.", orderAcceptedMessage.orderId());
            return orderAcceptedMessage.orderId();
        };
    }


    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label(){
        return orderFlux -> orderFlux.map(orderId -> {
            log.info("id {}order has labeled.", orderId);
            return new OrderDispatchedMessage(orderId);
        });
    }
}
