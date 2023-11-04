package com.example.webclienttest;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Component
public class WebClientConfig {

    @Bean
    public WebClient webClient(){

        HttpClient httpClient =HttpClient.create()
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10)) // 읽기 최대 시간 초 단위
                        .addHandlerLast(new WriteTimeoutHandler(10)) // 쓰기 최대 시간 초 단위

                )
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000); // 커넥션을 생성하는 시간

        // httpClient 객체의 wiretap 메소드를 사용하여 로그를 남길 수 있다.
        // logging.level.reactor.netty.http.client=DEBUG 로 설정하면 로그를 남길 수 있다.
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .clientConnector(connector)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("Content-Type", "application/json");
                    httpHeaders.add("Accept", "application/json");
                })
                .build();
    }
}
